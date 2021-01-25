package ml.market.cors.domain.article.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.ArticleDAO;
import ml.market.cors.domain.article.entity.CountDAO;
import ml.market.cors.repository.ArticleRepository;
import ml.market.cors.repository.CountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CountRepository countRepository;

    public void saveArticle(ArticleDAO articleDAO){
        countRepository.save(new CountDAO());
        articleRepository.save(articleDAO);
    }

    



}
