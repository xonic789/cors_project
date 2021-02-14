package ml.market.cors.repository.board;

import ml.market.cors.domain.board.entity.Report_boardDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportBoardRepository extends JpaRepository<Report_boardDAO,Long> {
}
