package ml.market.cors.domain.market.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.vo.ArticleViewInMarketVO;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.market.entity.dto.MarketApproveStatusUpdateDTO;
import ml.market.cors.domain.market.entity.vo.MarketViewVO;
import ml.market.cors.domain.market.enums.MarketStatus;
import ml.market.cors.domain.market.entity.vo.MarketApproveListVO;
import ml.market.cors.domain.market.enums.MarketKey;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.market.MarketRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.upload.S3Uploader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    private final ArticleRepository articleRepository;

    private final S3Uploader s3Uploader;

    private final MemberRepository memberRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean save(Map<MarketKey, Object> marketInfo, String email, long memberId, @NonNull MultipartFile imageFile){
        Map<String, String> uploadResult;
        String fileName = System.currentTimeMillis() + imageFile.getName();
        try {
            uploadResult = s3Uploader.upload(imageFile, email, System.currentTimeMillis(), imageFile.getOriginalFilename(), fileName);
        }catch (Exception e){
            return false;
        }
        List<MemberDAO> memberDAOList = memberRepository.findByMemberId(memberId);
        if(memberDAOList.size() == 0){
            return false;
        }
        MemberDAO memberDAO = memberDAOList.get(0);
        String name = (String) marketInfo.get(MarketKey.name);
        String intro = (String) marketInfo.get(MarketKey.intro);
        double latitude = memberDAO.getLatitude();
        double longitude = memberDAO.getLongitude();
        String location = memberDAO.getAddress();
        MarketStatus status = MarketStatus.WAIT;
        String imageUrl = uploadResult.get("url");
        MarketDAO marketDAO = new MarketDAO(memberDAO, name, intro, location, latitude, longitude, imageUrl, status, null);
        marketRepository.save(marketDAO);
        return true;
    }

    public List<MarketApproveListVO> list(Pageable pageable){
        List<MarketDAO> marketDAOList = marketRepository.findAllByStatus(pageable, MarketStatus.WAIT);
        List<MarketApproveListVO> marketApproveListVO = new ArrayList<>();
        for (MarketDAO item : marketDAOList) {
            marketApproveListVO.add(new MarketApproveListVO(item.getMarket_id(), item.getName(), item.getStatus()));
        }
        return marketApproveListVO;
    }

    public MarketViewVO view(long marketId, Pageable pageable) {
        if(marketId == 0 || pageable == null){
            return null;
        }

        Optional<MarketDAO> optional = marketRepository.findById(marketId);
        MarketDAO marketDAO;
        try {
            marketDAO = optional.get();
        }catch (Exception e){
            return null;
        }
        List<ArticleDAO> articleList = marketDAO.getArticles();
        List<ArticleViewInMarketVO> articleViewInMarketVO = new ArrayList<>();
        for (ArticleDAO item : articleList) {
            articleViewInMarketVO.add(new ArticleViewInMarketVO(item.getArticle_id(), item.getTitle(),item.getRprice(), item.getTprice(), item.getImage_info().getImage1()));
        }
        MarketViewVO marketViewVO = new MarketViewVO(marketDAO.getMarket_id(), marketDAO.getName(), marketDAO.getIntro(), marketDAO.getImage(), articleViewInMarketVO);
        return marketViewVO;
    }

    public boolean delete(long marketId, long memberId){
        if(marketId == 0){
            return false;
        }
        boolean bResult = marketRepository.existsByMarketIdAndMemberId(marketId, memberId);
        if(!bResult){
            return false;
        }
        marketRepository.deleteById(marketId);
        return true;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean updateStatus(MarketApproveStatusUpdateDTO marketApproveStatusUpdateDTO, long memberId) {
        if(marketApproveStatusUpdateDTO == null || memberId == 0){
            return false;
        }
        long marketId = marketApproveStatusUpdateDTO.getMarketId();
        boolean bResult = marketRepository.existsByMarketIdAndMemberId(marketId, memberId);
        if(!bResult){
            return false;
        }

        MarketStatus status = marketApproveStatusUpdateDTO.getStatus();
        String cancleCause = marketApproveStatusUpdateDTO.getCanclecause();
        Optional<MarketDAO> optional = marketRepository.findById(marketId);
        MarketDAO marketDAO;
        try{
            marketDAO = optional.get();
        }catch (Exception e){
            return false;
        }
        marketRepository.save(new MarketDAO(marketId, marketDAO.getMember(),marketDAO.getArticles(), marketDAO.getName(), marketDAO.getIntro(),marketDAO.getLocation(),marketDAO.getLatitude(),marketDAO.getLongitude(), marketDAO.getImage(), status, cancleCause));
        return true;
    }
}
