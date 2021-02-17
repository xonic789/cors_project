package ml.market.cors.domain.market.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.market.entity.dto.MarketArticleDTO;
import ml.market.cors.domain.market.entity.dto.MarketDTO;
import ml.market.cors.domain.market.entity.search.MarketSearchCondition;
import ml.market.cors.repository.market.MarketQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketMenuServiceImpl implements MarketMenuService{

    private final MarketQueryRepository marketQueryRepository;

    @Override
    public List<MarketDTO> findAll(MarketSearchCondition marketSearchCondition) {
        List<MarketDAO> markets = marketQueryRepository.findAll(marketSearchCondition);

        return markets.stream()
                .map(m -> new MarketDTO(
                        m.getMarket_id(),
                        m.getIntro(),
                        m.getImage(),
                        m.getName(),
                        m.getMember().getEmail(),
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

    @Override
    public List<MarketArticleDTO> findArticlesByMarketId(Long marketId) {
        List<ArticleDAO> articles = marketQueryRepository.findByMarketId(marketId);
        return articles.stream()
                .map(a -> new MarketArticleDTO(
                        a.getArticle_id(),
                        a.getCountDAO(),
                        a.getTitle(),
                        a.getTprice(),
                        a.getProgress(),
                        a.getCategory(),
                        a.getMember().getNickname(),
                        new MarketDTO(
                                a.getMarket().getMarket_id(),
                                a.getMarket().getIntro(),
                                a.getMarket().getImage(),
                                a.getMarket().getName(),
                                a.getMarket().getMember().getEmail()
                        ),
                        a.getWrite_date(),
                        a.getImageInfo().getImage1()
                )).collect(Collectors.toList());
    }
}
