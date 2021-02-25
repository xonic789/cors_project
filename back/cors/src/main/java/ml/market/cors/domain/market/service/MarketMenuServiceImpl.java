package ml.market.cors.domain.market.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.market.entity.dto.MarketArticleDTO;
import ml.market.cors.domain.market.entity.dto.MarketDTO;
import ml.market.cors.domain.market.entity.search.MarketSearchCondition;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.market.MarketQueryRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketMenuServiceImpl implements MarketMenuService{

    private final MarketQueryRepository marketQueryRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<MarketDTO> findAll(MarketSearchCondition marketSearchCondition) {
        List<MarketDAO> markets = marketQueryRepository.findAll(marketSearchCondition);

        return getMarketDTOS(markets);
    }



    @Override
    public MarketDTO findArticlesByMarketId(Long marketId) {
        MarketDAO findMarket = marketQueryRepository.findById(marketId);
        if(findMarket==null){
            return null;
        }
        return new MarketDTO(
                findMarket.getMarket_id(),
                findMarket.getIntro(),
                findMarket.getImage(),
                findMarket.getName(),
                findMarket.getMember().getEmail(),
                findMarket.getLocation(),
                findMarket.getStatus(),
                marketQueryRepository.findByMarketId(findMarket.getMarket_id())
                .stream()
                .map(a -> new ArticleDTO(
                        a.getArticle_id(),
                        a.getTitle(),
                        a.getWrite_date(),
                        a.getTprice(),
                        a.getProgress(),
                        a.getCategory(),
                        a.getImageInfo().getImage1(),
                        a.getMember().getNickname()
                )).collect(Collectors.toList())
        );
    }

    @Override
    public List<MarketDTO> findAllByMemberLocation(JwtCertificationToken jwtCertificationToken, MarketSearchCondition marketSearchCondition) throws IllegalStateException{
        MemberDAO memberDAO = findMember(jwtCertificationToken).orElseThrow(() -> new IllegalStateException("멤버를 찾지 못했습니다."));
        List<MarketDAO> byUserLocation = marketQueryRepository.findByUserLocation(memberDAO, marketSearchCondition);
        return getMarketDTOS(byUserLocation);
    }



    private List<MarketDTO> getMarketDTOS(List<MarketDAO> byUserLocation) {
        return byUserLocation.stream()
                .map(m -> new MarketDTO(
                        m.getMarket_id(),
                        m.getIntro(),
                        m.getImage(),
                        m.getName(),
                        m.getMember().getEmail(),
                        m.getLocation(),
                        m.getStatus(),
                        marketQueryRepository.findByMarketIdLimit3(m.getMarket_id())
                                .stream()
                                .map(a -> new ArticleDTO(
                                        a.getArticle_id(),
                                        a.getTitle(),
                                        a.getTprice(),
                                        a.getImageInfo().getImage1()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    private Optional<MemberDAO> findMember(JwtCertificationToken jwtCertificationToken) {
        if(jwtCertificationToken!=null){
            String email = jwtCertificationToken.getName();
            return Optional.of(memberRepository.findByEmail(email));
        }
        return Optional.empty();
    }


}
