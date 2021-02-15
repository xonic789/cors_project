package ml.market.cors.domain.article.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import ml.market.cors.domain.article.entity.dao.CountDAO;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.market.entity.MarketDAO;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {


    private Long articleId;
    private CountDAO countDAO;
    private String title;
    private LocalDateTime writeDate;
    private int tprice;
    private Progress progress;
    private Book_CategoryDAO category;
    private String image;
    private String nickname;
    private MarketDAO market;


    @QueryProjection
    public ArticleDTO(Long articleId, CountDAO countDAO, String title, int tprice, Progress progress, Book_CategoryDAO category, String nickname,LocalDateTime writeDate,String image) {
        this.articleId = articleId;
        this.countDAO = countDAO;
        this.title = title;
        this.tprice = tprice;
        this.progress = progress;
        this.category = category;
        this.nickname = nickname;
        this.writeDate=writeDate;
        this.image=image;
    }
    @QueryProjection
    public ArticleDTO(Long articleId, CountDAO countDAO, String title, int tprice, Progress progress, Book_CategoryDAO category, String nickname,MarketDAO market,LocalDateTime writeDate,String image) {
        this.articleId = articleId;
        this.countDAO = countDAO;
        this.title = title;
        this.tprice = tprice;
        this.progress = progress;
        this.category = category;
        this.nickname = nickname;
        this.market=market;
        this.writeDate=writeDate;
        this.image=image;
    }
}
