package ml.market.cors.repository.bookcategory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ml.market.cors.domain.article.entity.search.ArticleSearchCondition;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ml.market.cors.domain.bookcategory.entity.QBook_CategoryDAO.book_CategoryDAO;

@Repository
public class BookCategoryRepositoryImpl implements BookCategoryRepositoryCustom{


}
