package ml.market.cors.domain.market.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ml.market.cors.domain.article.entity.vo.ArticleViewInMarketVO;
import java.util.List;

@AllArgsConstructor
@Getter
public class MarketViewVO {
    private long id;
    private String name;
    private String intro;
    private String image;
    private List<ArticleViewInMarketVO> articleList;
}
