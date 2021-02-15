package ml.market.cors.domain.article.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MyWishListDTO {
    private List myArticleList;
    private int totalPage;
}
