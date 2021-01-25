package ml.market.cors.repository;


import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.CountDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CountRepository {

    private final EntityManager em;

    public void save(CountDAO countDAO){
        em.persist(countDAO);
    }

    public CountDAO findOne(Long id){
        return em.find(CountDAO.class,id);
    }


}
