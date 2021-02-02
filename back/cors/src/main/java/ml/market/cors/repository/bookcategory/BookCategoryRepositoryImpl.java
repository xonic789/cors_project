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

    private final JPAQueryFactory query;

    public BookCategoryRepositoryImpl(EntityManager em){
        query=new JPAQueryFactory(em);
    }

    @Override
    public List<Book_CategoryDAO> findByCategory(ArticleSearchCondition cond) {
        Optional<List<String>> categoryList = getCategoryList(cond);
        if(categoryList.get()!=null){
            List<String> category = categoryList.get();
            return query
                    .selectFrom(book_CategoryDAO)
                    .where(
                            one_depthEq(category.get(0)),
                            two_depthEq(category.get(1)),
                            three_depthEq(category.get(2)),
                            four_depthEq(category.get(3)),
                            five_depthEq(category.get(4)))
                    .fetch();
        }
        return null;
    }

    private Optional<List<String>> getCategoryList(ArticleSearchCondition articleSearchCondition) {
        List<String> list = new ArrayList<>();
        if(articleSearchCondition.getCategory()!=null){
            if(!articleSearchCondition.getCategory().equals("")){
                String category = articleSearchCondition.getCategory();
                String[] split = category.split(">");
                for (String s : split) {
                    list.add(s);
                }
                return Optional.of(list);
            }
        }
        return Optional.empty();
    }
    private BooleanExpression one_depthEq(String one_depth){
       return one_depth != null ? book_CategoryDAO.one_depth.eq(one_depth) : null;
    }

    private BooleanExpression two_depthEq(String two_depth){
        return two_depth != null ? book_CategoryDAO.one_depth.eq(two_depth) : null;
    }
    private BooleanExpression three_depthEq(String three_depth){
        return three_depth != null ? book_CategoryDAO.one_depth.eq(three_depth) : null;
    }
    private BooleanExpression four_depthEq(String four_depth){
        return four_depth != null ? book_CategoryDAO.one_depth.eq(four_depth) : null;
    }
    private BooleanExpression five_depthEq(String five_depth){
        return five_depth != null ? book_CategoryDAO.one_depth.eq(five_depth) : null;
    }
}
