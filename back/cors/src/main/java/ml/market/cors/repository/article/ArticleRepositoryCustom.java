package ml.market.cors.repository.article;


import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.search.ArticleSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<ArticleDTO> findByDivision(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition);

    List<ArticleDTO> findByMarketDivision(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition);

    List<ArticleDTO> findByDivisionAndUserLocation(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition);

    List<ArticleDTO> findByMarketDivisionAndUserLocation(Division division, Pageable pageable, ArticleSearchCondition articleSearchCondition);


    ArticleDAO findByIdFetch(Long article_id);



}
