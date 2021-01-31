package ml.market.cors.repository.article;


import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleDAO,Long>,ArticleRepositoryCustom {

}
