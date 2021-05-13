package ml.market.cors.repository.article;


import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.CountDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountRepository extends JpaRepository<CountDAO,Long> {

    int countByArticle(ArticleDAO articleDAO);
}
