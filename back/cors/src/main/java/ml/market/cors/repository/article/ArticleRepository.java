package ml.market.cors.repository.article;


import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.market.entity.MarketDAO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleDAO,Long>,ArticleRepositoryCustom {
    List<ArticleDAO> findAllByMarket(Pageable pageable, MarketDAO market);
}
