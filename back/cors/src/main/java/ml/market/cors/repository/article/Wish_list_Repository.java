package ml.market.cors.repository.article;


import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Wish_list_Repository extends JpaRepository<Wish_listDAO,Long> {
    @Query("SELECT wishTb FROM Wish_listDAO wishTb WHERE wishTb.member.member_id=:memberId")
    Page<Wish_listDAO> findAllByMemberId(Pageable pageable, @Param("memberId")long memberId);

    @Query("SELECT wishTb.article FROM Wish_listDAO wishTb WHERE wishTb.wish_id=:wishId")
    ArticleDAO findByWishId(@Param("wishId")long wishId);

    int countByArticle(ArticleDAO articleDAO);

    void deleteByMemberAndArticle(MemberDAO memberDAO, ArticleDAO articleDAO);
}
