package ml.market.cors.domain.market.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.article.entity.dto.ArticleViewInMarketDTO;
import java.util.List;

@AllArgsConstructor
@Data
public class MarketViewDTO {
    private long marketId;
    private String marketName;
    private String marketIntro;
    private String marketImage;
    private List<ArticleViewInMarketDTO> articleList;
    private int totalPage;
}
