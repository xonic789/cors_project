package ml.market.cors.repository.bookcategory;


import ml.market.cors.domain.article.entity.search.ArticleSearchCondition;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;

import java.util.List;

public interface BookCategoryRepositoryCustom {

    List<Book_CategoryDAO> findByCategory(ArticleSearchCondition cond);
}
