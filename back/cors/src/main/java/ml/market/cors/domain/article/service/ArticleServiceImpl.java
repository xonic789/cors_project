package ml.market.cors.domain.article.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.article.CountRepository;
import ml.market.cors.repository.article.query.ArticleQueryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final CountRepository countRepository;
    private final ArticleQueryRepository articleQueryRepository;

    @Override
    public void saveArticle(ArticleDAO articleDAO){
        countRepository.save(articleDAO.getCountDAO());
        articleRepository.save(articleDAO);
    }

    @Override
    public ArticleDAO findById(Long id){
        return articleRepository.findById(id).get();
    }

    /**
     * 게시물 수정
     * @param article_id
     * @param articleForm
     */

    @Override
    public void updateArticle(Long article_id,ArticleForm articleForm) {
        ArticleDAO findArticle = findById(article_id);
        findArticle.updateArticle(articleForm);
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
        ArticleDAO findArticle = findById(article_id);
        if(upperCase != null && upperCase.equals(Progress.TRADING.toString())){
            findArticle.updateProgress(Progress.TRADING);
        }else if(upperCase != null && upperCase.equals(Progress.COMPLETED.toString())){
            findArticle.updateProgress(Progress.COMPLETED);
        }else if(upperCase != null && upperCase.equals(Progress.HIDE.toString())){
            findArticle.updateProgress(Progress.HIDE);
        }
    }

    @Override
    public List<ArticleDTO> findByDivision(Division division, Pageable pageable) {
        return articleQueryRepository.findByDivision(division,pageable);

    }


}
