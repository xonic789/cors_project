package ml.market.cors.repository.article;


import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Wish_list_Repository extends JpaRepository<Wish_listDAO,Long> {
}
