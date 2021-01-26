package ml.market.cors.domain.article.service;

import ml.market.cors.domain.article.entity.ArticleDAO;

public interface ArticleService {

    void saveArticle(ArticleDAO articleDAO);
    ArticleDAO findOne(Long id);
    void updateArticle(Long article_id,ArticleForm articleForm);
    void updateArticleProgress(Long article_id,String progress);
}

