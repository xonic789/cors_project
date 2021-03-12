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
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.member.query.MemberQuerys;
import ml.market.cors.domain.member.service.vo.MemberJoinVO;
import ml.market.cors.domain.member.service.vo.MemberProfileVO;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enums.eSocialType;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.domain.util.kakao.dto.map.MapDocumentsDTO;
import ml.market.cors.domain.util.kakao.KaKaoRestManagement;
import ml.market.cors.domain.util.kakao.dto.map.KakaoResMapDTO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.upload.S3Uploader;
import ml.market.cors.upload.vo.ImageInfoVO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

    private final MemberQuerys memberQuerys;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DivisionPageDTO getDivisionList(long memberId, Division division, int pageIndex){
        Pageable pageable = PageRequest.of(pageIndex,10, Sort.by("article_id").descending());
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

    public Map<String, Object> setMember(long memberId) throws UnsupportedEncodingException {
        Map<String, Object> member = new HashMap<>();
        MemberDAO memberDAO = memberRepository.findByMemberId(memberId).get(0);
        String nickname = Base64.encodeBase64String(memberDAO.getNickname().getBytes(StandardCharsets.UTF_8));
        member.put(MemberParam.LATITUDE, memberDAO.getLatitude());
        member.put(MemberParam.LONGITUDE, memberDAO.getLongitude());
        member.put(MemberParam.ROLE, memberDAO.getRole());
        member.put(MemberParam.NICKNAME, nickname);
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
    public boolean change(MemberProfileVO memberProfileVO, long memberId) {
        if (memberId == 0) {
            return false;
        }
        String passwd = memberProfileVO.getPasswd();

        List<MemberDAO> members = memberRepository.findByMemberId(memberId);
        if (members == null) {
            return false;
        }
        MemberDAO memberDAO = members.get(0);
        if (memberDAO.getESocialType() == eSocialType.NORMAL) {
            if (!bCryptPasswordEncoder.matches(passwd, memberDAO.getPassword())) {
                return false;
            }
        }
        String intro = memberProfileVO.getIntro();
        String nickname = memberProfileVO.getNickname();
        if (nickname != null) {
            if (nickname.equals("")) {
                return false;
            }
            if (!existNickname(nickname, memberId)) {
                if (existNickname(nickname)) {
                    return false;
                }
            }
        }

        String newPasswd = memberProfileVO.getNewPasswd();
        if(newPasswd != null) {
            if (!newPasswd.equals("")) {
                newPasswd = bCryptPasswordEncoder.encode(newPasswd);
            }
        }

        MultipartFile image = memberProfileVO.getProfile_img();
        String imageKey = null;
        String imageUrl = null;
        if (image != null) {
            if (!image.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + image.getOriginalFilename();
                    ImageInfoVO imageInfoVO = s3Uploader.upload(image, memberDAO.getEmail(), System.currentTimeMillis(), image.getOriginalFilename(), fileName, memberDAO.getProfileKey());
                    if (imageInfoVO == null) {
                        return false;
                    }
                    imageKey = imageInfoVO.getKey();
                    imageUrl = imageInfoVO.getUrl();
                } catch (Exception e) {
                    log.debug("파일 업로드 실패");
                    throw new RuntimeException();
                }
            }
        }
        memberDAO.updateProfile(nickname, intro, imageUrl, newPasswd, imageKey);
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

    public boolean existNickname(String nickname, long memberId){
        if(memberId == 0){
            return false;
        }
        if(nickname == null){
            return false;
        }
        return memberRepository.existsByMemberIdAndNickname(memberId, nickname);
    }

    public boolean existEmail(String email){
        return memberRepository.existsByEmail(email);
    }



    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean create(MemberJoinVO memberJoinVo) throws RuntimeException {
        String email = memberJoinVo.getEmail();
        String nickname = memberJoinVo.getNickname();
        String passwd = memberJoinVo.getPasswd();
        String address = memberJoinVo.getAddress();
        if(email.equals("") || nickname.equals("") || passwd.equals("") || address.equals("")){
            return false;
        }

        boolean bResult = existNickname(nickname);
        if(bResult){
            return false;
        }

        bResult = existEmail(email);
        if(bResult){
            return false;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptPasswd = bCryptPasswordEncoder.encode(passwd);

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

        KakaoResMapDTO kakaoResMapDTO = kaKaoRestManagement.transAddressToCoordinate(memberJoinVo.getAddress());
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
        MemberDAO memberDAO = new MemberDAO(MemberParam.DEFAULT_PROFILE_KEY, MemberParam.DEFAULT_PROFILE_IMG_DIR, email, MemberRole.USER, null, encryptPasswd
                ,address, latitude, longitude, nickname, eSocialType.NORMAL);
        memberRepository.save(memberDAO);
        emailStateRepository.deleteById(email);
        return true;
    }

    public Map<String, String> viewProfile(long memberId){
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
        result.put(MemberParam.LATITUDE, String.valueOf(memberDAO.getLatitude()));
        result.put(MemberParam.LONGITUDE, String.valueOf(memberDAO.getLongitude()));
        result.put(MemberParam.ROLE, memberDAO.getRole().getRole());
        result.put(MemberParam.SOCIALTYPE, memberDAO.getESocialType().toString());
        List<Object> wishIdList = memberQuerys.searchMemberWishArticleList(memberDAO.getMember_id());
        if (wishIdList.size() > 0) {
            result.put(MemberParam.WISHLIST, String.valueOf(wishIdList));
        }

        List<Object> marketList = memberQuerys.searchMemberMarketList(memberDAO.getMember_id());
        if (marketList.size() > 0) {
            result.put(MemberParam.MARKETLIST, String.valueOf(marketList));
        }

        List<Object> articleIdList = memberQuerys.searchMemberArticleIdList(memberDAO.getMember_id());
        if(articleIdList.size() > 0){
            result.put(MemberParam.ARTICLELIST, String.valueOf(articleIdList));
        }
        return result;
    }
}
