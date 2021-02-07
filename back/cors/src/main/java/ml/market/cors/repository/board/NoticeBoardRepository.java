package ml.market.cors.repository.board;

import ml.market.cors.domain.board.entity.Notic_boardDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<Notic_boardDAO, Long> {
}
