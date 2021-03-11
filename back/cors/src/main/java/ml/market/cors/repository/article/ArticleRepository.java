package ml.market.cors.repository.article;


import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.CountDAO;
import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.market.entity.MarketDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleDAO,Long>,ArticleRepositoryCustom {
    Page<ArticleDAO> findAllByMarket(Pageable pageable, MarketDAO market);

    ArticleDAO findById(long articleId);

    @Query("SELECT ArticleTb from ArticleDAO ArticleTb where ArticleTb.member.member_id=:memberId and ArticleTb.division=:division")
    Page<ArticleDAO> findAllByMemberIdAndDivision(@Param("memberId") long memberId, @Param("division") Division division, Pageable pageable);

    @Query("SELECT ArticleTb.imageInfo from ArticleDAO ArticleTb where ArticleTb.article_id=:articleId")
    Image_infoDAO getImageByArticleId(@Param("articleId") long articleId);

    @Query("SELECT ArticleTb.category from ArticleDAO ArticleTb where ArticleTb.article_id=:articleId")
    Book_CategoryDAO getCategoryByArticleId(@Param("articleId") long articleId);
    @Query("SELECT ArticleTb.countDAO from ArticleDAO ArticleTb where ArticleTb.article_id=:articleId")
    CountDAO getCount(@Param("articleId") long articleId);
}
