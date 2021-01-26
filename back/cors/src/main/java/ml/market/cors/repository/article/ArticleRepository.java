package ml.market.cors.repository.article;


import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.ArticleDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final EntityManager em;

    public void save(ArticleDAO article){
        em.persist(article);
    }

    public ArticleDAO findOne(Long id){
        return em.find(ArticleDAO.class,id);
    }

    public List<ArticleDAO> findAll(){
        return em.createQuery("select a from ArticleDAO a",ArticleDAO.class)
                .getResultList();
    }




}
