package ml.market.cors.domain.article.entity.search;

import lombok.Data;
import ml.market.cors.domain.article.entity.enums.Division;

@Data
public class ArticleSearchCondition {

    private Division division;
    private String category;

}
