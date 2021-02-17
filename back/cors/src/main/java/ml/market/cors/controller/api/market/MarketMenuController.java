package ml.market.cors.controller.api.market;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.market.entity.dto.MarketArticleDTO;
import ml.market.cors.domain.market.entity.search.MarketSearchCondition;
import ml.market.cors.domain.market.service.MarketMenuService;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarketMenuController {

    private final MarketMenuService marketMenuService;
    private final ResponseEntityUtils responseEntityUtils;

    @GetMapping("/api/markets")
    public ResponseEntity<Message<Object>> getMarkets(
            @ModelAttribute MarketSearchCondition marketSearchCondition){
        return responseEntityUtils.getMessageResponseEntityOK(marketMenuService.findAll(marketSearchCondition));
    }

    @GetMapping("/api/market/{marketId}")
    public ResponseEntity<Message<Object>> marketDetail(@PathVariable Long marketId){
        List<MarketArticleDTO> findArticles = marketMenuService.findArticlesByMarketId(marketId);
        return responseEntityUtils.getMessageResponseEntityOK(findArticles);
    }
}
