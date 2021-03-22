package ml.market.cors.repository.market;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.QCountDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.dto.QArticleDTO;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.market.entity.search.MarketSearchCondition;
import ml.market.cors.domain.market.enums.MarketStatus;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

import static ml.market.cors.domain.article.entity.dao.QArticleDAO.articleDAO;
import static ml.market.cors.domain.article.entity.dao.QCountDAO.countDAO;
import static ml.market.cors.domain.market.entity.QMarketDAO.marketDAO;

@Repository
public class MarketQueryRepository {

    private final JPAQueryFactory query;

    public MarketQueryRepository(EntityManager em) {
        this.query=new JPAQueryFactory(em);
    }

    public List<MarketDAO> findAll(MarketSearchCondition marketSearchCondition){

            //찜목록 많은순, 카운트 테이블 쿼리하여 찜 카운트 많은 순으로.
        return query
                .selectFrom(marketDAO)
                .join(marketDAO.member).fetchJoin()
                .where(
                    titleLike(marketSearchCondition.getTitle()),
                    marketIdLt(marketSearchCondition.getLastId()),
                    marketDAO.status.eq(MarketStatus.ACCEPT)
                )
                .orderBy(marketDAO.market_id.desc())
                .fetch();
    }
    public MarketDAO findById(Long marketId){

        //찜목록 많은순, 카운트 테이블 쿼리하여 찜 카운트 많은 순으로.
        return query
                .selectFrom(marketDAO)
                .join(marketDAO.member).fetchJoin()
                .where(
                        marketDAO.market_id.eq(marketId)
                )
                .fetchOne();
    }

    public List<MarketDAO> findByUserLocation(MemberDAO member,MarketSearchCondition marketSearchCondition){

        //찜목록 많은순, 카운트 테이블 쿼리하여 찜 카운트 많은 순으로.
        return query
                .selectFrom(marketDAO)
                .join(marketDAO.member).fetchJoin()
                .where(
                        articleLatGoe(member),
                        articleLatLoe(member),
                        articleLngLoe(member),
                        articleLatGoe(member),
                        titleLike(marketSearchCondition.getTitle()),
                        marketIdLt(marketSearchCondition.getLastId()),
                        marketDAO.status.eq(MarketStatus.ACCEPT)
                )
                .orderBy(marketDAO.market_id.desc())
                .fetch();
    }



    public List<ArticleDAO> findByMarketId(Long marketId){
        return query
                .selectFrom(articleDAO)
                .join(articleDAO.imageInfo).fetchJoin()
                .join(articleDAO.member).fetchJoin()
                .join(articleDAO.category).fetchJoin()
                .where(
                        marketIdEq(marketId),
                        articleDAO.progress.eq(Progress.POSTING).or(articleDAO.progress.eq(Progress.TRADING))
                )
                .orderBy(articleDAO.article_id.desc())
                .fetch();
    }

    public List<ArticleDAO> findByMarketIdLimit3(Long marketId){
        return query
                .selectFrom(articleDAO)
                .join(articleDAO.imageInfo).fetchJoin()
                .join(articleDAO.member).fetchJoin()
                .join(articleDAO.category).fetchJoin()
                .where(
                        marketIdEq(marketId),
                        articleDAO.progress.eq(Progress.POSTING).or(articleDAO.progress.eq(Progress.TRADING))
                )
                .limit(3)
                .orderBy(articleDAO.article_id.desc())
                .fetch();
    }









    private BooleanExpression titleLike(String name){
        return name != null ? marketDAO.name.startsWith(name) : null;
    }
    private BooleanExpression marketIdLt(Long marketId){
        return marketId != null ? marketDAO.market_id.lt(marketId) : null;
    }
    private BooleanExpression marketIdEq(Long marketId){
        return marketId != null ? articleDAO.market.market_id.eq(marketId) : null;
    }

    private BooleanExpression articleLatGoe(MemberDAO member){

        return member.getLatitude() != 0.0d ? marketDAO.latitude.goe(member.getLatitude()-0.035) : null;
    }
    private BooleanExpression articleLatLoe(MemberDAO member){
        return member.getLatitude() != 0.0d ? marketDAO.latitude.loe(member.getLatitude()+0.035) : null;
    }
    private BooleanExpression articleLngGoe(MemberDAO member){
        return member.getLongitude() != 0.0d ? marketDAO.longitude.goe(member.getLongitude()-0.035) : null;
    }
    private BooleanExpression articleLngLoe(MemberDAO member){
        return member.getLongitude() != 0.0d ?  marketDAO.longitude.loe(member.getLongitude()+0.035) : null;
    }

}
