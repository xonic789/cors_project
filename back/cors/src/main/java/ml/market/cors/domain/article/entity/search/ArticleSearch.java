package ml.market.cors.domain.article.entity.search;

import lombok.Data;
import ml.market.cors.domain.article.entity.enums.Division;

@Data
public class ArticleSearch {

    private Division division;
    private String oneDepth;
    private String twoDepth;
    private String threeDepth;
    private String fourDepth;
    private String fiveDepth;
}
