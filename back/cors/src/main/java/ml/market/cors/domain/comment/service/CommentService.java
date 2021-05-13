package ml.market.cors.domain.comment.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.QuestionBoardDAO;
import ml.market.cors.domain.comment.entity.CommentBoardDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public boolean save(long questionId, String content, long memberId) {
        if(content == null || memberId == 0 || questionId == 0){
            return false;
        }
        MemberDAO memberDAO = new MemberDAO(memberId);
        QuestionBoardDAO questionBoardDAO = new QuestionBoardDAO(questionId);
        try {
            commentRepository.save(new CommentBoardDAO(content, LocalDateTime.now(), memberDAO, questionBoardDAO));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long commentId) {
        if(commentId == 0){
            return false;
        }
        try {
            commentRepository.deleteById(commentId);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean update(long commentId, String content) {
        if(commentId == 0 || content == null){
            return false;
        }

        CommentBoardDAO commentBoardDAO = commentRepository.getOne(commentId);
        commentBoardDAO.updateContent(content);
        return true;
    }
}
