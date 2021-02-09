package ml.market.cors.domain.bookcategory.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;

@Data
@AllArgsConstructor
public class BookCategoryDTO {

    private Long cid;
    private String oneDepth;
    private String twoDepth;
    private String threeDepth;
    private String fourDepth;
    private String fiveDepth;

    public BookCategoryDTO(Book_CategoryDAO category) {
        this.cid = category.getCid();
        this.oneDepth = category.getOneDepth();
        this.twoDepth = category.getTwoDepth();
        this.threeDepth = category.getThreeDepth();
        this.fourDepth = category.getFourDepth();
        this.fiveDepth = category.getFiveDepth();
    }
}
