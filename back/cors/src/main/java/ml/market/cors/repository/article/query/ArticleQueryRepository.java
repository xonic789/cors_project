package ml.market.cors.repository.article.query;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.dto.QArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ml.market.cors.domain.article.entity.dao.QArticleDAO.articleDAO;
import static ml.market.cors.domain.article.entity.dao.QCountDAO.countDAO;
import static ml.market.cors.domain.bookcategory.entity.QBook_CategoryDAO.book_CategoryDAO;
import static ml.market.cors.domain.member.entity.QMemberDAO.memberDAO;

@Repository
@Transactional(readOnly = true)
public class ArticleQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ArticleQueryRepository(EntityManager em) {
        this.em = em;
        this.query=new JPAQueryFactory(em);
    }

    public List<ArticleDTO> findByDivision(Division division, Pageable pageable){
        return query
                .select(new QArticleDTO(
                        articleDAO.article_id,
                        articleDAO.countDAO,
                        articleDAO.title,
                        articleDAO.tprice,
                        articleDAO.progress,
                        articleDAO.category,
                        articleDAO.member.nickname))
                .from(articleDAO)
                .join(articleDAO.member, memberDAO)
                .join(articleDAO.countDAO, countDAO)
                .join(articleDAO.category, book_CategoryDAO)
                .where(divisionEq(division))
                .fetch();
    }
    private BooleanExpression divisionEq(Division divisionCond) {
        if (divisionCond == null){
            return null;
        }
        return articleDAO.division.eq(divisionCond);
    }

    public ArticleDAO findById(Long article_id){
        return query
                .selectFrom(articleDAO)
                .from(articleDAO)
                .join(articleDAO.countDAO).fetchJoin()
                .join(articleDAO.image_info).fetchJoin()
                .where(articleDAO.article_id.eq(article_id))
                .fetch().get(0);
    }

}
