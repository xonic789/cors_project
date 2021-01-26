package ml.market.cors.domain.article.service;

import ml.market.cors.domain.article.entity.ArticleDAO;
import ml.market.cors.domain.article.entity.Division;
import ml.market.cors.domain.article.entity.Progress;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.article.ArticleRepository;
import ml.market.cors.repository.article.CountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ArticleServiceImplTest {

    @PersistenceContext EntityManager em;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired ArticleService articleService;
    @Autowired
    CountRepository countRepository;

    @Test
    public void 게시물_생성() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);


        ArticleDAO articleDAO = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), new Book_CategoryDAO(), 10000, Division.PURCHASE);

        //when
        articleService.saveArticle(articleDAO);

        //then
        assertEquals(articleDAO.getArticle_id(),articleService.findOne(articleDAO.getArticle_id()).getArticle_id());
    }

    @Test
    public void 모든_게시물_조회() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        Book_CategoryDAO book_categoryDAO1 = new Book_CategoryDAO(1L);
        Book_CategoryDAO book_categoryDAO2 = new Book_CategoryDAO(2L);
        Book_CategoryDAO book_categoryDAO3 = new Book_CategoryDAO(3L);
        Book_CategoryDAO book_categoryDAO4 = new Book_CategoryDAO(4L);
        Book_CategoryDAO book_categoryDAO5 = new Book_CategoryDAO(5L);

        em.persist(book_categoryDAO1);
        em.persist(book_categoryDAO2);
        em.persist(book_categoryDAO3);
        em.persist(book_categoryDAO4);
        em.persist(book_categoryDAO5);


        ArticleDAO articleDAO1 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO1, 10000, Division.PURCHASE);
        ArticleDAO articleDAO2 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO2, 10000, Division.PURCHASE);
        ArticleDAO articleDAO3 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO3, 10000, Division.PURCHASE);
        ArticleDAO articleDAO4 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO4, 10000, Division.PURCHASE);
        ArticleDAO articleDAO5 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO5, 10000, Division.PURCHASE);


        //when
        articleService.saveArticle(articleDAO1);
        articleService.saveArticle(articleDAO2);
        articleService.saveArticle(articleDAO3);
        articleService.saveArticle(articleDAO4);
        articleService.saveArticle(articleDAO5);

        //then
        assertEquals(articleRepository.findAll().size(),5);

    }

    @Test
    public void 게시물_수정() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        Book_CategoryDAO book_categoryDAO1 = new Book_CategoryDAO(1L);
        em.persist(book_categoryDAO1);

        ArticleDAO articleDAO1 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO1, 10000, Division.PURCHASE);
        articleService.saveArticle(articleDAO1);
        ArticleDAO findArticle = articleService.findOne(articleDAO1.getArticle_id());


        //when
        ArticleForm articleForm = new ArticleForm();
        articleForm.setContent("바뀌었다.");
        articleForm.setDivision(Division.PURCHASE);
        articleForm.setProgress(Progress.POSTING);
        articleForm.setRprice(10000);
        articleForm.setTprice(2000);
        articleForm.setWriteDate(LocalDateTime.now());

        articleService.updateArticle(findArticle.getArticle_id(),articleForm);

        em.flush();
        em.clear();

        ArticleDAO resultArticle = articleService.findOne(articleDAO1.getArticle_id());

        //then
        assertEquals(findArticle,resultArticle);
    }

    @Test
    public void 게시물_상태_수정() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        Book_CategoryDAO book_categoryDAO1 = new Book_CategoryDAO(1L);
        em.persist(book_categoryDAO1);

        Book_CategoryDAO book_categoryDAO2 = new Book_CategoryDAO(2L);
        em.persist(book_categoryDAO2);

        Book_CategoryDAO book_categoryDAO3 = new Book_CategoryDAO(3L);
        em.persist(book_categoryDAO3);

        ArticleDAO articleDAO1 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO1, 10000, Division.PURCHASE);
        articleService.saveArticle(articleDAO1);

        ArticleDAO articleDAO2 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO2, 10000, Division.PURCHASE);
        articleService.saveArticle(articleDAO2);

        ArticleDAO articleDAO3 = ArticleDAO.createArticle(memberDAO, "테스트", 10000,
                LocalDateTime.now(), book_categoryDAO3, 10000, Division.PURCHASE);
        articleService.saveArticle(articleDAO3);

        String progress1 = "hide";
        String progress2 = "trading";
        String progress3 = "completed";

        //when

        ArticleDAO findArticle1 = articleService.findOne(articleDAO1.getArticle_id());
        ArticleDAO findArticle2 = articleService.findOne(articleDAO2.getArticle_id());
        ArticleDAO findArticle3 = articleService.findOne(articleDAO3.getArticle_id());

        articleService.updateArticleProgress(findArticle1.getArticle_id(),progress1);
        articleService.updateArticleProgress(findArticle2.getArticle_id(),progress2);
        articleService.updateArticleProgress(findArticle3.getArticle_id(),progress3);

        em.flush();
        em.clear();

        ArticleDAO result1 = articleService.findOne(articleDAO1.getArticle_id());         
        ArticleDAO result2 = articleService.findOne(articleDAO2.getArticle_id());
        ArticleDAO result3 = articleService.findOne(articleDAO3.getArticle_id());



        //then
        assertEquals(result1.getProgress(),Progress.HIDE);
        assertEquals(result2.getProgress(),Progress.TRADING);
        assertEquals(result3.getProgress(),Progress.COMPLETED);

    }
}