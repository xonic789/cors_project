package ml.market.cors.repository.board;

import ml.market.cors.domain.board.entity.QuestionBoardDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionBoardDAO, Long> {
    @Query("select questionBoardTb from QuestionBoardDAO questionBoardTb where questionBoardTb.member.member_id=:member_id")
    Page<QuestionBoardDAO> findAllByMember(Pageable pageable, @Param("member_id") long memberId);

    @Query("select questionBoardTb.member from QuestionBoardDAO questionBoardTb where questionBoardTb.questionId=:questionId")
    MemberDAO findByQuestionId(@Param("questionId")Long questionId);

    @Query("select count(questionBoardTb.questionId) > 0 from QuestionBoardDAO questionBoardTb where questionBoardTb.questionId=:questionId and questionBoardTb.member.member_id=:memberId")
    boolean existsByQuestionIdAndMemberId(@Param("questionId")long questionId, @Param("memberId")long memberId);
}
