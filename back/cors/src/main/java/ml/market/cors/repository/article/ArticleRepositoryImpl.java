package ml.market.cors.repository.article;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.dto.QArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.search.ArticleSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ml.market.cors.domain.article.entity.dao.QArticleDAO.articleDAO;
import static ml.market.cors.domain.article.entity.dao.QCountDAO.countDAO;
import static ml.market.cors.domain.article.entity.dao.QImage_infoDAO.image_infoDAO;
import static ml.market.cors.domain.bookcategory.entity.QBook_CategoryDAO.book_CategoryDAO;
import static ml.market.cors.domain.market.entity.QMarketDAO.marketDAO;
import static ml.market.cors.domain.member.entity.QMemberDAO.memberDAO;

@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory query;

    public ArticleRepositoryImpl(EntityManager em) {
        this.query=new JPAQueryFactory(em);
    }

    @Override
    public List<ArticleDTO> findByDivision(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition){
        List<String> categoryList = getCategoryList(articleSearchCondition);
        return query
                .select(new QArticleDTO(
                        articleDAO.article_id,
                        articleDAO.countDAO,
                        articleDAO.title,
                        articleDAO.tprice,
                        articleDAO.progress,
                        articleDAO.category,
                        articleDAO.member.nickname,
                        articleDAO.write_date,
                        articleDAO.image_info.image1))
                .from(articleDAO)
                .join(articleDAO.member, memberDAO)
                .join(articleDAO.countDAO, countDAO)
                .join(articleDAO.category, book_CategoryDAO)
                .join(articleDAO.image_info, image_infoDAO)
                .where(divisionEq(division),articleDAO.market.isNull())
                .orderBy(articleDAO.article_id.desc())
                .fetch();
    }

//    private BooleanExpression one_depthEq(String one_depth){
//    }
//    private BooleanExpression two_depthEq(String one_depth){
//
//    }
//    private BooleanExpression three_depthEq(String one_depth){
//
//    }
//    private BooleanExpression four_depthEq(String one_depth){
//
//    }
//    private BooleanExpression five_depthEq(String one_depth){
//
//    }

    private List<String> getCategoryList(ArticleSearchCondition articleSearchCondition) {
        String category = articleSearchCondition.getCategory();
        String[] split = category.split(">");
        List<String> list = new ArrayList<>();

        for (String s : split) {
            list.add(s);
        }
        return list;
    }

    @Override
    public List<ArticleDTO> findByMarketDivision(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition){
        return query
                .select(new QArticleDTO(
                        articleDAO.article_id,
                        articleDAO.countDAO,
                        articleDAO.title,
                        articleDAO.tprice,
                        articleDAO.progress,
                        articleDAO.category,
                        articleDAO.member.nickname,
                        articleDAO.market,
                        articleDAO.write_date,
                        articleDAO.image_info.image1))
                .from(articleDAO)
                .join(articleDAO.member, memberDAO)
                .join(articleDAO.countDAO, countDAO)
                .join(articleDAO.category, book_CategoryDAO)
                .join(articleDAO.market, marketDAO)
                .join(articleDAO.image_info, image_infoDAO)
                .where(divisionEq(division),articleDAO.market.isNotNull())
                .orderBy(articleDAO.article_id.desc())
                .fetch();
    }

    private BooleanExpression divisionEq(Division divisionCond) {
        if (divisionCond == null){
            return null;
        }
        return articleDAO.division.eq(divisionCond);
    }

    @Override
    public ArticleDAO findByIdFetch(Long article_id){
        return query
                .selectFrom(articleDAO)
                .from(articleDAO)
                .join(articleDAO.countDAO).fetchJoin()
                .join(articleDAO.image_info).fetchJoin()
                .where(articleDAO.article_id.eq(article_id))
                .fetchOne();
    }
}
