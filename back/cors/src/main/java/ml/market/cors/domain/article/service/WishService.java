package ml.market.cors.domain.article.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.CountDAO;
import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.article.entity.dto.MyWishListDTO;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.bookcategory.entity.dto.BookCategoryDTO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.QMemberDAO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.article.CountRepository;
import ml.market.cors.repository.article.Wish_list_Repository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WishService {
    private final Wish_list_Repository wish_list_repository;

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    private final CountRepository countRepository;

    public MyWishListDTO getMyWishList(long memberId, int pageIndex){
        if(memberId == 0){
            return null;
        }
        Pageable pageable = PageRequest.of(pageIndex, 10);
        Page<Wish_listDAO> wishPage = wish_list_repository.findAllByMemberId(pageable, memberId);
        List<Wish_listDAO> wishContent = wishPage.getContent();
        long wishId;
        ArticleDAO articleDAO;
        Book_CategoryDAO bookCategoryDAO;
        Image_infoDAO imageInfoDAO;
        BookCategoryDTO bookCategoryDTO;
        List<WishAndArticleDTO> wishList = new ArrayList<>();
        for (Wish_listDAO item : wishContent) {
            wishId = item.getWish_id();
            articleDAO = wish_list_repository.findByWishId(wishId);
            imageInfoDAO = articleRepository.getImageByArticleId(articleDAO.getArticle_id());
            bookCategoryDAO = articleRepository.getCategoryByArticleId(articleDAO.getArticle_id());
            bookCategoryDTO = new BookCategoryDTO(bookCategoryDAO.getCid(), bookCategoryDAO.getOneDepth(), bookCategoryDAO.getTwoDepth()
                    ,bookCategoryDAO.getThreeDepth(), bookCategoryDAO.getFourDepth(), bookCategoryDAO.getFiveDepth());
            wishList.add(new WishAndArticleDTO(wishId, articleDAO.getArticle_id(), articleDAO.getTitle()
                    ,imageInfoDAO.getImage1(), articleDAO.getProgress(), articleDAO.getTprice(), bookCategoryDTO));
        }
        MyWishListDTO myWishListDTO = new MyWishListDTO(wishList, wishPage.getTotalPages());
        return myWishListDTO;
    }

    public int getCount(long articleId) throws Exception{
        if(articleId == 0){
            throw new NullPointerException();
        }
        ArticleDAO articleDAO = new ArticleDAO(articleId);
        int count = wish_list_repository.countByArticle(articleDAO);
        return count;
    }

    public boolean save(long memberId, long articleId) {
        if(memberId == 0 || articleId == 0){
            return false;
        }
        MemberDAO memberDAO = new MemberDAO(memberId);
        ArticleDAO articleDAO = new ArticleDAO(articleId);
        boolean bResult = wish_list_repository.existsByMemberAndArticle(memberDAO, articleDAO);
        if(bResult){
            return false;
        }
        try{
            memberDAO = memberRepository.findById(memberId);
            articleDAO = articleRepository.findById(articleId);
            Wish_listDAO wishListDAO = new Wish_listDAO(memberDAO, articleDAO);
            wish_list_repository.save(wishListDAO);
            //int count = countRepository.countByArticle(articleDAO);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean delete(long articleId, long memberId) {
        if(articleId == 0 || memberId == 0){
            return false;
        }
        try{
            MemberDAO memberDAO = memberRepository.findById(memberId);
            ArticleDAO articleDAO = articleRepository.findById(articleId);
            wish_list_repository.deleteByMemberAndArticle(memberDAO, articleDAO);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}

@Data
@AllArgsConstructor
class WishAndArticleDTO{
    private long wishId;
    private long articleId;
    private String title;
    private String thumbnail;
    private Progress progress;
    private int tprice;
    private BookCategoryDTO category;
}



