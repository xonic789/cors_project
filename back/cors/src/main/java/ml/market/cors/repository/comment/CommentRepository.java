package ml.market.cors.repository.comment;

import ml.market.cors.domain.board.entity.QuestionBoardDAO;
import ml.market.cors.domain.comment.entity.CommentBoardDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentBoardDAO, Long> {

    Page<CommentBoardDAO> findAllByQuestion(QuestionBoardDAO question, Pageable pageable);
}
