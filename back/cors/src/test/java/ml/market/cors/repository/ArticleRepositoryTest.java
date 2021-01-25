package ml.market.cors.repository;

import ml.market.cors.domain.article.entity.ArticleDAO;
import ml.market.cors.domain.article.entity.CountDAO;
import ml.market.cors.domain.article.entity.DivisionEnum;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Member;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ArticleRepositoryTest {

    @PersistenceContext
    EntityManager em;
    @Autowired ArticleRepository articleRepository;
    @Autowired CountRepository countRepository;

    @Test
    public void createArticle() throws Exception{
        //given
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember("dltmddn@na.na");
        em.persist(memberDAO);

        CountDAO countDAO = new CountDAO();
        countRepository.save(countDAO);

        ArticleDAO articleDAO = new ArticleDAO();
        articleDAO.createArticle(memberDAO,countDAO,"테스트",10000, LocalDateTime.now(),"aa",new Book_CategoryDAO(),10000, DivisionEnum.PURCHASE);
        articleRepository.save(articleDAO);

        //when


        //then
    }

}