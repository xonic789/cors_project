package ml.market.cors.domain.market.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleViewInMarketDTO;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.market.entity.dto.MarketApproveStatusUpdateDTO;
import ml.market.cors.domain.market.entity.dto.MarketViewDTO;
import ml.market.cors.domain.market.enums.MarketStatus;
import ml.market.cors.domain.market.entity.vo.MarketApproveListVO;
import ml.market.cors.domain.market.enums.MarketKey;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.domain.util.kakao.KaKaoRestManagement;
import ml.market.cors.domain.util.kakao.dto.map.KakaoResMapDTO;
import ml.market.cors.domain.util.kakao.dto.map.MapDocumentsDTO;
import ml.market.cors.domain.util.token.LoginTokenManagement;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.market.MarketRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import ml.market.cors.upload.S3Uploader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    private final ArticleRepository articleRepository;

    private final S3Uploader s3Uploader;

    private final TokenInfoRepository tokenInfoRepository;

    private final MemberRepository memberRepository;

    private final KaKaoRestManagement kaKaoRestManagement;

    private final LoginTokenManagement loginTokenManagement;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public long save(Map<MarketKey, Object> marketInfo, String email, long memberId, @NonNull MultipartFile imageFile){
        Map<String, String> uploadResult;

        String fileName = System.currentTimeMillis() + imageFile.getOriginalFilename();
        MemberDAO memberDAO = new MemberDAO(memberId);
        boolean bResult = marketRepository.existsByMember(memberDAO);
        if(bResult){
            return 0;
        }

        List<MemberDAO> memberDAOList = memberRepository.findByMemberId(memberId);
        if(memberDAOList.size() == 0){
            return 0;
        }
        memberDAO = memberDAOList.get(0);
        String location = (String) marketInfo.get(MarketKey.location);
        if(location == null){
            return 0;
        }
        if(location.equals("")){
            return 0;
        }
        String name = (String) marketInfo.get(MarketKey.name);
        if(name == null){
            return 0;
        }
        if(name.equals("")){
            return 0;
        }
        String intro = (String) marketInfo.get(MarketKey.intro);
        if(intro == null){
            return 0;
        }

        KakaoResMapDTO kakaoResMapDTO = kaKaoRestManagement.transAddressToCoordinate(location);
        if(kakaoResMapDTO == null){
            return 0;
        }
        MapDocumentsDTO mapResMapDocumentsDTO = kakaoResMapDTO.getDocuments().get(0);
        double latitude = mapResMapDocumentsDTO.getY();
        double longitude = mapResMapDocumentsDTO.getX();
        MarketStatus status = MarketStatus.WAIT;

        try {
            uploadResult = s3Uploader.upload(imageFile, email, System.currentTimeMillis(), imageFile.getOriginalFilename(), fileName);
        }catch (Exception e){
            return 0;
        }
        String imageUrl = uploadResult.get("url");
        MarketDAO marketDAO = new MarketDAO(memberDAO, name, intro, location, latitude, longitude, imageUrl, status, null);
        marketDAO = marketRepository.save(marketDAO);
        return marketDAO.getMarket_id();
    }

    public List<MarketApproveListVO> list(int pageIndex){
        Pageable pageable = PageRequest.of(pageIndex, 10);
        List<MarketDAO> marketDAOList = marketRepository.findAllByStatus(pageable, MarketStatus.WAIT);
        List<MarketApproveListVO> marketApproveListVO = new ArrayList<>();
        for (MarketDAO item : marketDAOList) {
            marketApproveListVO.add(new MarketApproveListVO(item.getMarket_id(), item.getName(), item.getStatus()));
        }
        return marketApproveListVO;
    }


    public MarketViewDTO view(long marketId, int pageIndex) {
        if(marketId == 0 ){
            return null;
        }

        Optional<MarketDAO> optional = marketRepository.findById(marketId);
        MarketDAO marketDAO;
        try {
            marketDAO = optional.get();
        }catch (Exception e){
            return null;
        }

        Pageable pageable = PageRequest.of(pageIndex, 10);
        Page<ArticleDAO> articleListPage = articleRepository.findAllByMarket(pageable, marketDAO);
        List<ArticleDAO> articleList = articleListPage.getContent();
        List<ArticleViewInMarketDTO> articleViewInMarketDTOList = new ArrayList<>();
        ArticleViewInMarketDTO articleViewInMarketDTO;
        for (ArticleDAO item : articleList) {
            articleViewInMarketDTO = new ArticleViewInMarketDTO(item.getArticle_id(), item.getTitle(),item.getRprice(), item.getTprice(), item.getImageInfo().getImage1());
            articleViewInMarketDTOList.add(articleViewInMarketDTO);
        }
        int totalPage = articleListPage.getTotalPages();
        MarketViewDTO marketViewDTO = new MarketViewDTO(marketId, marketDAO.getName(), marketDAO.getIntro(), marketDAO.getImage(), articleViewInMarketDTOList, totalPage);
        return marketViewDTO;
    }

private Map<String, Object> setClaims(long member_id, List memberRoles, String email){
    Map<String, Object> claims = new HashMap();
    claims.put(LoginTokenManagement.ROLE, memberRoles);
    claims.put(LoginTokenManagement.ID, member_id);
    claims.put(LoginTokenManagement.EMAIL, email);
    claims.put(LoginTokenManagement.IAT, System.currentTimeMillis());
    return claims;
}

    private boolean setToken(String email, long memberId, HttpServletRequest request, HttpServletResponse response){
        loginTokenManagement.logout(request, response);
        List<MemberRole> memberRoles = new ArrayList<>();
        memberRoles.add(MemberRole.USER);
        Map<String, Object> claims = setClaims(memberId, memberRoles, email);
        Map<String, Object> tokenPair = loginTokenManagement.createTokenPair(claims);
        if(tokenPair == null){
            SecurityContextHolder.getContext().setAuthentication(null);
            return false;
        }

        try {
            response.setContentType("application/json");
            eCookie cookAttr = eCookie.ACCESS_TOKEN;
            String accessToken = (String)tokenPair.get(LoginTokenManagement.ACCESS_TOKEN);
            Cookie cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), accessToken);
            response.addCookie(cookie);
            cookAttr = eCookie.REFRESH_TOKEN;
            TokenInfoDAO tokenInfoDAO = tokenInfoRepository.save(new TokenInfoDAO((String)tokenPair.get(LoginTokenManagement.REFRESH_TOKEN), memberId, (long)tokenPair.get(LoginTokenManagement.REFRESH_TOKEN_EXPIRETIME)));
            Long index = tokenInfoDAO.getTokenindex();
            String refreshTokenIndexToken = loginTokenManagement.create(index);
            if(refreshTokenIndexToken == null){
                return false;
            }
            cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), refreshTokenIndexToken);
            response.addCookie(cookie);
            response.setHeader(MemberParam.ROLE, MemberRole.USER.getRole());
        } catch(Exception e) {
            response.reset();
            return false;
        }

        JwtCertificationToken authToken = new JwtCertificationToken(memberId ,email, memberRoles);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return true;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean delete(String email, long marketId, long memberId, HttpServletResponse response, HttpServletRequest request){
        if(marketId == 0 || response == null || request == null){
            return false;
        }
        boolean bResult = marketRepository.existsByMarketIdAndMemberId(marketId, memberId);
        if(!bResult){
            return false;
        }
        marketRepository.deleteById(marketId);
        List<MemberDAO> memberDAOList = memberRepository.findByMemberId(memberId);
        MemberDAO memberDAO = memberDAOList.get(0);
        memberRepository.save(new MemberDAO(memberDAO.getMember_id(),  memberDAO.getProfileKey(), memberDAO.getProfile_img(), memberDAO.getEmail(), MemberRole.USER,  memberDAO.getPassword(),  memberDAO.getAddress(),  memberDAO.getLatitude(), memberDAO.getLongitude(),  memberDAO.getNickname(),  memberDAO.getESocialType(), null));

        if(!setToken(email, memberId, request, response)){
            throw new RuntimeException();
        }

        return true;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean updateStatus(long marketId, MarketApproveStatusUpdateDTO marketApproveStatusUpdateDTO) {
        if(marketApproveStatusUpdateDTO == null){
            return false;
        }

        String cancleCause = null;
        Optional<MarketDAO> optional = marketRepository.findById(marketId);
        MarketDAO marketDAO;
        try{
            marketDAO = optional.get();
        }catch (Exception e){
            return false;
        }
        MemberDAO memberDAO = marketRepository.findByMember(marketId);
        if(memberDAO == null){
            return false;
        }
        MarketStatus status = marketApproveStatusUpdateDTO.getStatus();
        if(status == MarketStatus.REJECT){
            cancleCause = marketApproveStatusUpdateDTO.getCanclecause();
        } else if(status == MarketStatus.ACCEPT){
            memberRepository.save(new MemberDAO(memberDAO.getMember_id(),  memberDAO.getProfileKey(), memberDAO.getProfile_img(), memberDAO.getEmail(), MemberRole.CEO,  memberDAO.getPassword(),  memberDAO.getAddress(),  memberDAO.getLatitude(), memberDAO.getLongitude(),  memberDAO.getNickname(),  memberDAO.getESocialType(), null));
        }
        marketRepository.save(new MarketDAO(marketId, marketDAO.getMember(), marketDAO.getArticles(), marketDAO.getName(), marketDAO.getIntro(),marketDAO.getLocation(),marketDAO.getLatitude(),marketDAO.getLongitude(), marketDAO.getImage(), status, cancleCause));
        return true;
    }

    public MyMarketDTO getMyMarketList(long memberId, int pageIndex){
        if(memberId == 0){
            return null;
        }
        Pageable pageable = PageRequest.of(pageIndex, 10);
        Page<MarketDAO> marketDAOPage = marketRepository.findAllByMember(memberId, pageable);
        List<MyMarketInfo> myMarketInfoList = new ArrayList<>();
        for (MarketDAO item : marketDAOPage.getContent()) {
            myMarketInfoList.add(new MyMarketInfo(item.getMarket_id(), item.getImage()));
        }
        MyMarketDTO myMarketDTO = new MyMarketDTO(marketDAOPage.getTotalPages(), myMarketInfoList);
        return myMarketDTO;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean updateMyMarket(long marketId, Map<MarketKey, Object> marketInfo, String email, long memberId, MultipartFile imageFile) {
        if(memberId == 0){
            return false;
        }
        if(marketId == 0){
            return false;
        }

        boolean bResult = marketRepository.existsByMarketIdAndMemberId(marketId, memberId);
        if(!bResult){
            return false;
        }

        Optional<MarketDAO> optional = marketRepository.findById(marketId);
        MarketDAO marketDAO = null;
        try{
            marketDAO = optional.get();
        }catch (Exception e){
            return false;
        }

        bResult = marketInfo.containsKey(MarketKey.name);
        String name = marketDAO.getName();
        if(bResult){
            name = (String) marketInfo.get(MarketKey.name);
        }

        String intro = marketDAO.getIntro();
        bResult = marketInfo.containsKey(MarketKey.intro);
        if(bResult){
            intro = (String)marketInfo.get(MarketKey.intro);
        }

        String location = marketDAO.getLocation();
        double latitude = marketDAO.getLatitude();
        double longitude = marketDAO.getLongitude();
        bResult = marketInfo.containsKey(MarketKey.location);
        if(bResult){
            location = (String)marketInfo.get(MarketKey.location);
            KakaoResMapDTO kakaoResMapDTO = kaKaoRestManagement.transAddressToCoordinate(location);
            if(kakaoResMapDTO == null){
                return false;
            }
            MapDocumentsDTO mapResMapDocumentsDTO = kakaoResMapDTO.getDocuments().get(0);
            latitude = mapResMapDocumentsDTO.getY();
            longitude = mapResMapDocumentsDTO.getX();
        }

        String imageUrl = marketDAO.getImage();
        if(imageFile != null){
            bResult = imageFile.isEmpty();
            if(!bResult){
                Map<String, String> uploadResult;
                String fileName = System.currentTimeMillis() + imageFile.getOriginalFilename();
                try {
                    uploadResult = s3Uploader.upload(imageFile, email, System.currentTimeMillis(), imageFile.getOriginalFilename(), fileName);
                    imageUrl = uploadResult.get("url");
                }catch (Exception e){
                    return false;
                }
            }
        }
        MarketDAO tempMarketDAO = new MarketDAO(marketId, marketDAO.getMember(), marketDAO.getArticles(), name, intro, location, latitude, longitude, imageUrl, marketDAO.getStatus(), null);
        marketRepository.save(tempMarketDAO);
        return true;
    }

    public boolean exsistsMyMarket(long memberId) {
        if(memberId == 0){
            return false;
        }
        MarketDAO marketDAO = marketRepository.findByMemberId(memberId);
        if(marketDAO == null){
            return false;
        }
        MarketStatus status = marketDAO.getStatus();
        boolean bResult = false;
        if(status == MarketStatus.ACCEPT){
            bResult = true;
        }else{
            bResult = false;
        }
        return bResult;
    }
}

@AllArgsConstructor
@Data
class MyMarketDTO{
    private int totalPage;
    private List<MyMarketInfo> myMarketInfoList;
}

@Data
@AllArgsConstructor
class MyMarketInfo{
    private long marketId;
    private String thumbnail;
}