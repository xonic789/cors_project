package ml.market.cors.repository.board;

import ml.market.cors.domain.board.entity.Notic_boardDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface NoticeBoardRepository extends JpaRepository<Notic_boardDAO, Long> {
    Notic_boardDAO findById(long noticeId);
}
