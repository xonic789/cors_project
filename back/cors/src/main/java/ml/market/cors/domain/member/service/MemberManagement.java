package ml.market.cors.domain.member.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.controller.api.member.dto.ArticleDivisionPageDTO;
import ml.market.cors.controller.api.member.dto.DivisionPageDTO;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.bookcategory.entity.dto.BookCategoryDTO;
import ml.market.cors.domain.mail.entity.EmailStateDAO;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enums.eSocialType;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.domain.util.kakao.dto.map.MapDocumentsDTO;
import ml.market.cors.domain.util.kakao.KaKaoRestManagement;
import ml.market.cors.domain.util.kakao.dto.map.KakaoResMapDTO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.article.Image_info_Repository;
import ml.market.cors.repository.bookcategory.BookCategoryRepository;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.upload.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberManagement {
    private final MemberRepository memberRepository;

    private final EmailStateRepository emailStateRepository;

    private final KaKaoRestManagement kaKaoRestManagement;

    private final S3Uploader s3Uploader;

    private final ArticleRepository articleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DivisionPageDTO getDivisionList(long memberId, Division division, int pageIndex){
        Pageable pageable = PageRequest.of(pageIndex,10);
        Page<ArticleDAO> page = articleRepository.findAllByMemberIdAndDivision(memberId, division,pageable);
        List<ArticleDAO> articleDAOList = page.getContent();
        List<ArticleDivisionPageDTO> articleDivisionPageDTOList = new ArrayList<>();
        Image_infoDAO image_infoDAO;
        Book_CategoryDAO book_categoryDAO;
        for (ArticleDAO item : articleDAOList) {
            book_categoryDAO = articleRepository.getCategoryByArticleId(item.getArticle_id());
            image_infoDAO = articleRepository.getImageByArticleId(item.getArticle_id());
            articleDivisionPageDTOList.add(new ArticleDivisionPageDTO(item.getArticle_id(), item.getTitle(), image_infoDAO.getImage1(), item.getProgress(), item.getTprice(), new BookCategoryDTO(book_categoryDAO.getCid(),book_categoryDAO.getOneDepth(),book_categoryDAO.getTwoDepth(),book_categoryDAO.getThreeDepth(),book_categoryDAO.getFourDepth(),book_categoryDAO.getFiveDepth())));
        }
        DivisionPageDTO divisionPageDTO = new DivisionPageDTO(page.getTotalPages(), articleDivisionPageDTOList);
        return divisionPageDTO;
    }

    public Map<String, Object> setMember(long memberId){
        Map<String, Object> member = new HashMap<>();
        MemberDAO memberDAO = memberRepository.findByMemberId(memberId).get(0);
        member.put(MemberParam.LATITUDE, memberDAO.getLatitude());
        member.put(MemberParam.LONGITUDE, memberDAO.getLongitude());
        member.put(MemberParam.ROLE, memberDAO.getRole());
        member.put(MemberParam.NICKNAME, memberDAO.getNickname());
        member.put(MemberParam.EMAIL, memberDAO.getEmail());
        member.put(MemberParam.PROFILE_IMG, memberDAO.getProfile_img());
        List<Wish_listDAO> wishList = memberDAO.getWish_listDAO();
        List<Long> wishIdList = new ArrayList();
        for (Wish_listDAO wishItem : wishList) {
            wishIdList.add(wishItem.getWish_id());
        }
        if (wishIdList.size() > 0) {
            member.put(MemberParam.WISHLIST, wishIdList);
        }
        List<ArticleDAO> articleList = memberDAO.getArticleDAO();
        List<Long> articleIdList = new ArrayList<>();
        for (ArticleDAO articleItem : articleList) {
            articleIdList.add(articleItem.getArticle_id());
        }
        if(articleIdList.size() > 0){
            member.put(MemberParam.ARTICLELIST, articleIdList);
        }
        return member;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean change(@NonNull Map<String, Object> member, Long id, MultipartFile multipartFile){
        if(id == 0){
            return false;
        }
        String passwd = (String)member.get(MemberParam.PASSWD);
        if(passwd == null){
            return false;
        }

        List<MemberDAO> members = memberRepository.findByMemberId(id);
        if(members == null){
            return false;
        }
        MemberDAO memberDAO = members.get(0);
        if (!bCryptPasswordEncoder.matches(passwd, memberDAO.getPassword())) {
            return false;
        }
        if(member.containsKey(MemberParam.INTRO)) {
            String intro = (String) member.get(MemberParam.INTRO);
            memberDAO = memberRepository.save(new MemberDAO(id, memberDAO.getProfileKey(), memberDAO.getProfile_img(), memberDAO.getEmail(), memberDAO.getRole(), memberDAO.getPassword(), memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), memberDAO.getNickname(), memberDAO.getESocialType(), intro));
        }
        if(member.containsKey(MemberParam.NICKNAME)) {
            String nickname = (String) member.get(MemberParam.NICKNAME);
            if (nickname.equals("")) {
                return false;
            }
            if (!existNickname(nickname, id)) {
                if (existNickname(nickname)) {
                    return false;
                }
                memberDAO = memberRepository.save(new MemberDAO(id, memberDAO.getProfileKey(), memberDAO.getProfile_img(), memberDAO.getEmail(), memberDAO.getRole(),  memberDAO.getPassword(), memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), nickname, memberDAO.getESocialType(), memberDAO.getIntro()));
            }
        }

        if(member.containsKey(MemberParam.NEWPASSWD)){
            String newPasswd = (String) member.get(MemberParam.NEWPASSWD);
            if(!newPasswd.equals("")){
                newPasswd = bCryptPasswordEncoder.encode(newPasswd);
                memberDAO = memberRepository.save(new MemberDAO(id, memberDAO.getProfileKey(), memberDAO.getProfile_img(), memberDAO.getEmail(), memberDAO.getRole(),  newPasswd, memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), memberDAO.getNickname(), memberDAO.getESocialType(), memberDAO.getIntro()));
            }
        }

        if(multipartFile != null) {
            if (!multipartFile.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
                    Map<String, String> result = s3Uploader.upload(multipartFile, memberDAO.getEmail(), System.currentTimeMillis(), multipartFile.getOriginalFilename(), fileName, memberDAO.getProfileKey());
                    if (result == null) {
                        return true;
                    }
                    s3Uploader.deleteObject(memberDAO.getProfileKey());
                    memberDAO = memberRepository.save(new MemberDAO(id, result.get("key"), result.get("url"), memberDAO.getEmail(), memberDAO.getRole(), memberDAO.getPassword(), memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), memberDAO.getNickname(), memberDAO.getESocialType(), memberDAO.getIntro()));
                } catch (Exception e) {
                    log.debug("파일 업로드 실패");
                    throw new RuntimeException();
                }
            }
        }
        return true;
    }


    public boolean conditionJoin(String email, String nickname){
        boolean bResult = existNickname(nickname);
        if(bResult){
            return false;
        }

        bResult = existEmail(email);
        if(bResult){
            return false;
        }
        return true;
    }

    public boolean existNickname(String nickname){
        if(nickname == null){
            return false;
        }
        if(nickname.equals("")){
            return false;
        }
        return memberRepository.existsByNickname(nickname);
    }

    public boolean existNickname(@NonNull String nickname, long id){
        if(id == 0){
            return false;
        }
        return memberRepository.existsByMemberIdAndNickname(id, nickname);
    }

    public boolean existEmail(String email){
        return memberRepository.existsByEmail(email);
    }



    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean create(MemberVO memberVo) throws RuntimeException {
        String email = memberVo.getEmail();
        String nickname = memberVo.getNickname();
        if(!conditionJoin(email, nickname)){
            return false;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwd = bCryptPasswordEncoder.encode(memberVo.getPasswd());

        Optional<EmailStateDAO> optional = emailStateRepository.findById(email);
        EmailStateDAO emailStateDAO;
        try{
            emailStateDAO = optional.get();
        }catch (Exception e){
            return false;
        }

        if(emailStateDAO.getAuthenticatedFlag() == eMailAuthenticatedFlag.N){
            return false;
        }
        Date date = new Date();
        if(date.after(new Date(emailStateDAO.getExpireTime()))){
            return false;
        }


        String address = memberVo.getAddress();
        KakaoResMapDTO kakaoResMapDTO = kaKaoRestManagement.transAddressToCoordinate(memberVo.getAddress());
        if(kakaoResMapDTO == null){
            return false;
        }
        MapDocumentsDTO mapResMapDocumentsDTO = kakaoResMapDTO.getDocuments().get(0);
        List documents = kakaoResMapDTO.getDocuments();
        if(documents.size() == 0){
            return false;
        }
        double latitude = mapResMapDocumentsDTO.getY();
        double longitude = mapResMapDocumentsDTO.getX();
        MemberDAO memberDAO = new MemberDAO(MemberParam.DEFAULT_PROFILE_KEY, MemberParam.DEFAULT_PROFILE_IMG_DIR, email, MemberRole.USER, null, passwd
                ,address, latitude, longitude, nickname, eSocialType.NORMAL);
        memberRepository.save(memberDAO);
        emailStateRepository.deleteById(email);
        return true;
    }

    public Map<String, String> viewProfile(@NonNull HttpServletResponse response, long memberId){
        if(memberId == 0){
            return null;
        }

        List<MemberDAO> memberList = memberRepository.findByMemberId(memberId);
        if(memberList == null){
            return null;
        }
        MemberDAO memberDAO = memberList.get(0);

        Map result = new HashMap();
        result.put(MemberParam.NICKNAME, memberDAO.getNickname());
        result.put(MemberParam.PROFILE_IMG, memberDAO.getProfile_img());
        result.put(MemberParam.INTRO, memberDAO.getIntro());
        return result;
    }
}
