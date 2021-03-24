package ml.market.cors.domain.member.query;

import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class MemberQuerys {
    @PersistenceContext
    EntityManager entityManager;

    public List<Object> searchMemberWishArticleList(long memberId) {
        MemberDAO memberDAO = new MemberDAO(memberId);
        String jpql = "SELECT wishList.article.article_id FROM  Wish_listDAO wishList where wishList.member = :memberId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("memberId", memberDAO);
        return query.getResultList();
    }

    public List<Object> searchMemberArticleIdList(long memberId) {
        MemberDAO memberDAO = new MemberDAO(memberId);
        String jpql = "SELECT articleList.article_id FROM  ArticleDAO articleList where articleList.member = :memberId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("memberId", memberDAO);
        return query.getResultList();
    }

    public List<Object> searchMemberMarketList(long memberId) {
        MemberDAO memberDAO = new MemberDAO(memberId);
        String jpql = "SELECT marketTb.market_id FROM MarketDAO marketTb where marketTb.member = :memberId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("memberId", memberDAO);
        return query.getResultList();
    }
}
