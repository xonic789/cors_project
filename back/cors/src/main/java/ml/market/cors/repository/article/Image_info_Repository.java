package ml.market.cors.repository.article;

import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Image_info_Repository extends JpaRepository<Image_infoDAO,Long> {
}
