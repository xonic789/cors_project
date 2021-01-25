package ml.market.cors.domain.article.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.ArticleDAO;
import ml.market.cors.domain.article.entity.CountDAO;
import ml.market.cors.domain.article.entity.Progress;
import ml.market.cors.repository.ArticleRepository;
import ml.market.cors.repository.CountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final CountRepository countRepository;

    @Override
    public void saveArticle(ArticleDAO articleDAO){
        countRepository.save(articleDAO.getCountDAO());
        articleRepository.save(articleDAO);
    }

    @Override
    public ArticleDAO findOne(Long id){
        return articleRepository.findOne(id);
    }

    @Override
    public void updateArticle(Long article_id,ArticleForm articleForm) {
        ArticleDAO findArticle = articleRepository.findOne(article_id);
        findArticle.updateArticle(articleForm.getContent(), articleForm.getRprice(),
                LocalDateTime.now(),articleForm.getProgress(),articleForm.getTprice(),articleForm.getDivision());
    }

    /**
     * progress 는 @PathVariable로 받을것.
     * 예상) 채팅시 Chatting_room이 insert 되는 시점에 Progress를 TRADING으로 바꿔준다.
     * @param article_id
     * @param progress
     */
    @Override
    public void updateArticleProgress(Long article_id,String progress){
        String upperCase = progress.toUpperCase();
        ArticleDAO findArticle = articleRepository.findOne(article_id);
        if(upperCase != null && upperCase.equals(Progress.TRADING.toString())){
            findArticle.updateProgress(Progress.TRADING);
        }else if(upperCase != null && upperCase.equals(Progress.COMPLETED.toString())){
            findArticle.updateProgress(Progress.COMPLETED);
        }else if(upperCase != null && upperCase.equals(Progress.HIDE.toString())){
            findArticle.updateProgress(Progress.HIDE);
        }
    }



}
