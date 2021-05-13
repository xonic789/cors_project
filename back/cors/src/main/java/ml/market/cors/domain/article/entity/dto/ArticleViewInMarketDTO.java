package ml.market.cors.domain.article.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;

@AllArgsConstructor
@Data
public class ArticleViewInMarketDTO {
    private long id;
    private String title;
    private int rprice;
    private int tprice;
    private String image;
}
