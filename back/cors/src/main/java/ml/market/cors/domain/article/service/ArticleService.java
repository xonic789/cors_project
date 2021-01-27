package ml.market.cors.domain.article.service;

import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    void saveArticle(ArticleDAO articleDAO);
    ArticleDAO findById(Long id);
    void updateArticle(Long article_id,ArticleForm articleForm);
    void updateArticleProgress(Long article_id,String progress);

    List<ArticleDTO> findByDivision(Division division, Pageable pageable);
}

