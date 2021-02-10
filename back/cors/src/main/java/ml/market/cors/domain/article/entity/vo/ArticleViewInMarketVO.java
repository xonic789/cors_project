package ml.market.cors.domain.article.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

@AllArgsConstructor
@Getter
public class ArticleViewInMarketVO {
    private long id;
    private String title;
    private int rprice;
    private int tprice;
    private String image;
}
