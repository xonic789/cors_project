package ml.market.cors;

import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.article.service.ArticleForm;
import ml.market.cors.domain.article.service.ArticleService;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.article.CountRepository;
import ml.market.cors.repository.article.Image_info_Repository;
import ml.market.cors.repository.article.query.ArticleQueryRepository;
import ml.market.cors.repository.bookcategory.Book_Category_Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ArticleServiceImplTest {

    @PersistenceContext EntityManager em;
    @Autowired ArticleRepository articleRepository;
    @Autowired ArticleService articleService;
    @Autowired CountRepository countRepository;
    @Autowired Image_info_Repository image_info_repository;
    @Autowired Book_Category_Repository book_category_repository;
    @Autowired ArticleQueryRepository articleQueryRepository;


    @Test
    public void 게시물_생성() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        Book_CategoryDAO book_categoryDAO = new Book_CategoryDAO(1L);
        book_category_repository.save(book_categoryDAO);


        ArticleForm articleForm = getArticleFormSales();


        //when
        ArticleDAO articleDAO = articleService.saveArticle(articleForm,memberDAO);

        em.flush();
        em.clear();

        ArticleDAO findArticle = articleService.findById(articleDAO.getArticle_id());


        //then
        assertEquals(articleForm.getTitle(),findArticle.getTitle());
    }

    private ArticleForm getArticleFormSales() {
        ArticleForm articleForm = new ArticleForm();
        articleForm.setMemberId(1L);
        articleForm.setContent("내용입니다");
        articleForm.setTitle("제목입니다");
        articleForm.setCid(1L);
        articleForm.setRprice(10000);
        articleForm.setProgress(Progress.POSTING);
        articleForm.setDivision(Division.SALES);
        return articleForm;
    }
    private ArticleForm getArticleFormPurchase() {
        ArticleForm articleForm = new ArticleForm();
        articleForm.setMemberId(1L);
        articleForm.setContent("내용입니다");
        articleForm.setTitle("제목입니다");
        articleForm.setCid(1L);
        articleForm.setRprice(10000);
        articleForm.setProgress(Progress.POSTING);
        articleForm.setDivision(Division.PURCHASE);
        return articleForm;
    }

    @Test
    public void 게시물_조회() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        Book_CategoryDAO book_categoryDAO1 = new Book_CategoryDAO(1L);
        book_category_repository.save(book_categoryDAO1);

        ArticleForm articleFormSales = getArticleFormSales();
        ArticleForm articleFormPurchase = getArticleFormPurchase();
        //when
        for(int i=0;i<100;i++){
            articleService.saveArticle(articleFormSales,memberDAO);
            articleService.saveArticle(articleFormPurchase,memberDAO);
        }


        //then

    }

    @Test
    public void 게시물_수정() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        Book_CategoryDAO book_categoryDAO1 = new Book_CategoryDAO(1L);
        book_category_repository.save(book_categoryDAO1);

        ArticleForm articleForm = getArticleFormSales();

        ArticleDAO articleDAO = articleService.saveArticle(articleForm,memberDAO);

        em.flush();
        em.clear();

        System.out.println(articleDAO.getArticle_id());

        //when
        ArticleForm updateForm = new ArticleForm();
        updateForm.setContent("바뀌었다.");
        updateForm.setDivision(Division.PURCHASE);
        updateForm.setProgress(Progress.POSTING);
        updateForm.setRprice(10000);
        updateForm.setTprice(2000);
        ArticleDAO findArticle = articleService.findById(articleDAO.getArticle_id());

        articleService.updateArticle(findArticle.getArticle_id(),updateForm);
        //then
        assertNotEquals(articleForm.getContent(),findArticle.getContent());
    }

    @Test
    public void 게시물_상태_수정() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        Book_CategoryDAO book_categoryDAO1 = new Book_CategoryDAO(1L);
        book_category_repository.save(book_categoryDAO1);

        ArticleForm articleForm1 = getArticleFormSales();
        ArticleForm articleForm2 = getArticleFormSales();
        ArticleForm articleForm3 = getArticleFormSales();

        ArticleDAO.createArticleForm(articleForm1,memberDAO,book_categoryDAO1);
        ArticleDAO.createArticleForm(articleForm2,memberDAO,book_categoryDAO1);
        ArticleDAO.createArticleForm(articleForm3,memberDAO,book_categoryDAO1);

        ArticleDAO articleDAO1 = articleService.saveArticle(articleForm1,memberDAO);
        ArticleDAO articleDAO2 = articleService.saveArticle(articleForm2,memberDAO);
        ArticleDAO articleDAO3 = articleService.saveArticle(articleForm3,memberDAO);

        String progress1 = "hide";
        String progress2 = "trading";
        String progress3 = "completed";

        //when

        ArticleDAO findArticle1 = articleService.findById(articleDAO1.getArticle_id());
        ArticleDAO findArticle2 = articleService.findById(articleDAO2.getArticle_id());
        ArticleDAO findArticle3 = articleService.findById(articleDAO3.getArticle_id());

        articleService.updateArticleProgress(findArticle1.getArticle_id(),progress1);
        articleService.updateArticleProgress(findArticle2.getArticle_id(),progress2);
        articleService.updateArticleProgress(findArticle3.getArticle_id(),progress3);

        em.flush();
        em.clear();

        ArticleDAO result1 = articleService.findById(articleDAO1.getArticle_id());
        ArticleDAO result2 = articleService.findById(articleDAO2.getArticle_id());
        ArticleDAO result3 = articleService.findById(articleDAO3.getArticle_id());



        //then
        assertEquals(result1.getProgress(),Progress.HIDE);
        assertEquals(result2.getProgress(),Progress.TRADING);
        assertEquals(result3.getProgress(),Progress.COMPLETED);

    }
}