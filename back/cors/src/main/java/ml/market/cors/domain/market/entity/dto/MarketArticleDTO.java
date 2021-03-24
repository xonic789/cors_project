package ml.market.cors.domain.market.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.article.entity.dao.CountDAO;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MarketArticleDTO {

    private Long articleId;
    private CountDAO countDAO;
    private String title;
    private int tprice;
    private Progress progress;
    private Book_CategoryDAO category;
    private String nickname;
    private MarketDTO marketDTO;
    private LocalDateTime writeDate;
    private String image;
}
