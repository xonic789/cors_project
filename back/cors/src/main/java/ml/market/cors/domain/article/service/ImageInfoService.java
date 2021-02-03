package ml.market.cors.domain.article.service;


import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.article.entity.enums.Division;

public interface ImageInfoService {

    void updateImage(ArticleDAO articleDAO,String... images);
}
