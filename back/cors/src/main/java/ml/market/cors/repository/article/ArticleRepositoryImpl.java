package ml.market.cors.repository.article;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.dto.QArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.search.ArticleSearchCondition;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.bookcategory.BookCategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    private final BookCategoryRepository bookCategoryRepository;

    public ArticleRepositoryImpl(EntityManager em,BookCategoryRepository bookCategoryRepository) {
        this.query=new JPAQueryFactory(em);
        this.bookCategoryRepository=bookCategoryRepository;
    }

    @Override
    public List<ArticleDTO> findByDivision(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition){
        String[] categoryList = getCategoryList(articleSearchCondition);
        String title = articleSearchCondition.getTitle();
        System.out.println(title);

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
                    .where(
                            titleLike(articleSearchCondition.getTitle()),
                            articleIdLt(articleSearchCondition.getLastId()),
                            divisionEq(division),
                            articleDAO.market.isNull(),
                            articleDAO.category.cid.in(
                                    JPAExpressions
                                            .select(book_CategoryDAO.cid)
                                            .from(book_CategoryDAO)
                                            .where(
                                                    one_depthEq(categoryList[0]),
                                                    two_depthEq(categoryList[1]),
                                                    three_depthEq(categoryList[2]),
                                                    four_depthEq(categoryList[3]),
                                                    five_depthEq(categoryList[4]))
                    ))
                    .limit(10)
                    .orderBy(articleDAO.article_id.desc())
                    .fetch();
    }


    private String[] getCategoryList(ArticleSearchCondition articleSearchCondition) {
        String[] categories = new String[5];

        if(articleSearchCondition.getCategory()!=null) {
            if (!articleSearchCondition.getCategory().equals("")) {
                String category = articleSearchCondition.getCategory();
                String[] split = category.split(">");
                for(int i=0;i<split.length;i++){
                    categories[i]=split[i];
                }
                return categories;
            }
        }
        return categories;
    }

    @Override
    public List<ArticleDTO> findByMarketDivision(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition){
        String[] categoryList = getCategoryList(articleSearchCondition);
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
                .where(
                        titleLike(articleSearchCondition.getTitle()),
                        articleIdLt(articleSearchCondition.getLastId()),
                        divisionEq(division),
                        articleDAO.market.isNotNull(),
                        articleDAO.category.cid.in(
                                JPAExpressions
                                        .select(book_CategoryDAO.cid)
                                        .from(book_CategoryDAO)
                                        .where(
                                                one_depthEq(categoryList[0]),
                                                two_depthEq(categoryList[1]),
                                                three_depthEq(categoryList[2]),
                                                four_depthEq(categoryList[3]),
                                                five_depthEq(categoryList[4]))
                        ))
                .limit(10)
                .orderBy(articleDAO.article_id.desc())
                .fetch();
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

    @Override
    public List<ArticleDTO> findByDivisionAndUserLocation(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition, MemberDAO member) {
        String[] categoryList = getCategoryList(articleSearchCondition);
        String title = articleSearchCondition.getTitle();
        System.out.println(title);

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
                .where(
                        articleLatGoe(member),
                        articleLatLoe(member),
                        articleLngGoe(member),
                        articleLngLoe(member),
                        titleLike(articleSearchCondition.getTitle()),
                        articleIdLt(articleSearchCondition.getLastId()),
                        divisionEq(division),
                        articleDAO.market.isNull(),
                        articleDAO.category.cid.in(
                                JPAExpressions
                                        .select(book_CategoryDAO.cid)
                                        .from(book_CategoryDAO)
                                        .where(
                                                one_depthEq(categoryList[0]),
                                                two_depthEq(categoryList[1]),
                                                three_depthEq(categoryList[2]),
                                                four_depthEq(categoryList[3]),
                                                five_depthEq(categoryList[4]))
                        ))
                .limit(10)
                .orderBy(articleDAO.article_id.desc())
                .fetch();
    }

    private BooleanExpression divisionEq(Division divisionCond) {
        if (divisionCond == null){
            return null;
        }
        return articleDAO.division.eq(divisionCond);
    }

    private BooleanExpression one_depthEq(String one_depth){
        return one_depth != null ? book_CategoryDAO.oneDepth.eq(one_depth) : null;
    }

    private BooleanExpression two_depthEq(String two_depth){
        return two_depth != null ? book_CategoryDAO.twoDepth.eq(two_depth) : null;
    }
    private BooleanExpression three_depthEq(String three_depth){
        return three_depth != null ? book_CategoryDAO.threeDepth.eq(three_depth) : null;
    }
    private BooleanExpression four_depthEq(String four_depth){
        return four_depth != null ? book_CategoryDAO.fourDepth.eq(four_depth) : null;
    }
    private BooleanExpression five_depthEq(String five_depth){
        return five_depth != null ? book_CategoryDAO.fiveDepth.eq(five_depth) : null;
    }
    private BooleanExpression titleLike(String title){
        return title != null ? articleDAO.title.startsWith(title) : null;
    }
    private BooleanExpression articleIdLt(Long articleId){
        return articleId != null ? articleDAO.article_id.lt(articleId) : null;
    }


    private BooleanExpression articleLatGoe(MemberDAO member ){

        return member.getLatitude() != 0.0d ? articleDAO.member.latitude.goe(member.getLatitude()-0.035) : null;
    }
    private BooleanExpression articleLatLoe(MemberDAO member){
        return member.getLatitude() != 0.0d ? articleDAO.member.latitude.loe(member.getLatitude()+0.035) : null;
    }
    private BooleanExpression articleLngGoe(MemberDAO member){
        return member.getLongitude() != 0.0d ? articleDAO.member.longitude.goe(member.getLongitude()-0.035) : null;
    }
    private BooleanExpression articleLngLoe(MemberDAO member){
        return member.getLongitude() != 0.0d ?  articleDAO.member.longitude.loe(member.getLongitude()+0.035) : null;
    }




}
