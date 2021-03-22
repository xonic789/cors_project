package ml.market.cors.controller.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;

import java.util.List;

@Data
@AllArgsConstructor
public class DivisionPageDTO {
    private int pageTotal;
    private List<ArticleDivisionPageDTO> myAricleList;
}
