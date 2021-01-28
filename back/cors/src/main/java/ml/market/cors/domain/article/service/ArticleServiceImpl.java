package ml.market.cors.domain.article.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.article.CountRepository;
import ml.market.cors.repository.article.Image_info_Repository;
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
    private final Image_info_Repository image_info_repository;

    @Override
    public ArticleDAO saveArticle(ArticleForm articleForm, MemberDAO memberDAO){
        ArticleDAO createArticle = ArticleDAO.createArticleForm(articleForm,memberDAO);
        countRepository.save(createArticle.getCountDAO());
        image_info_repository.save(createArticle.getImage_info());
        return articleRepository.save(createArticle);
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
    public ArticleDAO updateArticle(Long article_id,ArticleForm articleForm) {
        ArticleDAO findArticle = findById(article_id);
        Image_infoDAO findImage = image_info_repository.findById(
                findArticle.getImage_info().getIndex_id()).get();
        findImage.update_Image_info(articleForm.getImage2(), articleForm.getImage3(), articleForm.getDivision());
        return findArticle.updateArticle(articleForm, findImage);
    }

    /**
     * progress 는 @PathVariable로 받을것.
     * 예상) 채팅시 Chatting_room이 insert 되는 시점에 Progress를 TRADING으로 바꿔준다.
     * @param article_id
     * @param progress
     */
    @Override
    public Progress updateArticleProgress(Long article_id, String progress){
        String upperCase = progress.toUpperCase();
        ArticleDAO findArticle = findById(article_id);
        if(upperCase != null && upperCase.equals(Progress.TRADING.toString())){
            return findArticle.updateProgress(Progress.TRADING);
        }else if(upperCase != null && upperCase.equals(Progress.COMPLETED.toString())){
            return findArticle.updateProgress(Progress.COMPLETED);
        }else if(upperCase != null && upperCase.equals(Progress.HIDE.toString())){
            return findArticle.updateProgress(Progress.HIDE);
        }

        return findArticle.getProgress();
    }

    @Override
    public List<ArticleDTO> findByDivision(Division division, Pageable pageable) {
        return articleQueryRepository.findByDivision(division,pageable);

    }


}
