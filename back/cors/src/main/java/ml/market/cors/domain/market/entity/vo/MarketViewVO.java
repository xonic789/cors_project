package ml.market.cors.domain.market.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ml.market.cors.domain.article.entity.vo.ArticleViewInMarketVO;
import java.util.List;

@AllArgsConstructor
@Getter
public class MarketViewVO {
    private long marketId;
    private String marketName;
    private String marketIntro;
    private String marketImage;
    private List<ArticleViewInMarketVO> articleList;
}
