package ml.market.cors.domain.market.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.market.enums.MarketStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class MarketDTO {

    private Long marketId;
    private String intro;
    private String image;
    private String name;
    private String email;
    private String location;
    private MarketStatus status;
//    private int wishCount;
    private List<ArticleDTO> articles;

    public MarketDTO(Long marketId, String intro, String image, String name, String email) {
        this.marketId = marketId;
        this.intro = intro;
        this.image = image;
        this.name = name;
        this.email = email;
    }
}
