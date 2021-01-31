package ml.market.cors.domain.article.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.CountDAO;
import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.article.entity.search.ArticleSearch;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.article.CountRepository;
import ml.market.cors.repository.article.Image_info_Repository;
import ml.market.cors.repository.article.query.ArticleQueryRepository;
import ml.market.cors.repository.bookcategory.Book_Category_Repository;
import ml.market.cors.repository.market.MarketRepository;
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
    private final Book_Category_Repository book_category_repository;
    private final MarketRepository marketRepository;


    @Override
    @Transactional(readOnly = false)
    public ArticleDAO saveArticle(ArticleForm articleForm, MemberDAO memberDAO){
        Book_CategoryDAO book_categoryDAO = book_category_repository.findById(articleForm.getCid()).get();
        ArticleDAO createArticle = ArticleDAO.createArticleForm(articleForm,memberDAO,book_categoryDAO);
        countRepository.save(createArticle.getCountDAO());
        image_info_repository.save(createArticle.getImage_info());
        return articleRepository.save(createArticle);
    }

    @Override
    @Transactional(readOnly = false)
    public ArticleDAO saveMarketArticle(ArticleForm articleForm, MemberDAO memberDAO){
        Book_CategoryDAO book_categoryDAO = book_category_repository.findById(articleForm.getCid()).get();
        ArticleDAO createArticle = ArticleDAO.createArticleForm(articleForm,memberDAO,book_categoryDAO);
        countRepository.save(createArticle.getCountDAO());
        image_info_repository.save(createArticle.getImage_info());
        return articleRepository.save(createArticle);
    }

    @Override
    public ArticleDAO findById(Long id){
        return articleQueryRepository.findById(id);
    }

    /**
     * 게시물 수정
     * @param article_id
     * @param articleForm
     */

    @Override
    public ArticleDAO updateArticle(Long article_id,ArticleForm articleForm) {
        ArticleDAO findArticle = findById(article_id);
        Image_infoDAO findImage = image_info_repository.findById(findArticle.getImage_info().getIndex_id()).get();
        CountDAO countDAO = countRepository.findById(findArticle.getCountDAO().getCount_id()).get();
        return findArticle.updateArticle(articleForm, findImage,countDAO);
    }

    /**
     * progress 는 @PathVariable로 받을것.
     * 예상) 채팅시 Chatting_room이 insert 되는 시점에 Progress를 TRADING으로 바꿔준다.
     * @param article_id
     * @param progress
     */
    @Override
    @Transactional(readOnly = false)
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
    public List<ArticleDTO> findAll(Division division, Pageable pageable, ArticleSearch articleSearch) {
        return articleQueryRepository.findByDivision(division,pageable,articleSearch);

    }

    @Override
    @Transactional(readOnly = false)
    public void deleteArticle(Long article_id) {
        ArticleDAO findArticle = articleQueryRepository.findById(article_id);
        image_info_repository.deleteById(findArticle.getImage_info().getIndex_id());
        countRepository.deleteById(findArticle.getCountDAO().getCount_id());
        articleRepository.deleteById(findArticle.getArticle_id());
    }
}
