package ml.market.cors.controller.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.bookcategory.entity.dto.BookCategoryDTO;

@Data
@AllArgsConstructor
public class ArticleDivisionPageDTO {
    private long articleId;
    private String title;
    private String thumbnail;
    private Progress progress;
    private int tprice;
    private BookCategoryDTO  category;
}
