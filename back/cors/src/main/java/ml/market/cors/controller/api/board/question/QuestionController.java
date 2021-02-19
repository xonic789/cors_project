package ml.market.cors.controller.api.board.question;


import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.dto.QuestionViewDTO;
import ml.market.cors.domain.board.service.QuestionService;
import ml.market.cors.domain.comment.service.CommentService;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    private final ResponseEntityUtils responseEntityUtils;

    private final CommentService commentService;


    @PostMapping("/question/view")
    public ResponseEntity<Message<Object>> view(@RequestParam("page")int pageIndex, @RequestParam("questionId")long questionId,@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        List role = (List)jwtCertificationToken.getAuthorities();
        long memberId = (long)jwtCertificationToken.getCredentials();
        ResponseEntity<Message<Object>> messageResponseEntity;
        QuestionViewDTO questionViewDTO = questionService.view(pageIndex, role, questionId, memberId);
        if(questionViewDTO == null){
            messageResponseEntity =  responseEntityUtils.getMessageResponseEntityBadRequest("문의사항 보기 실패");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(questionViewDTO);
        }
        return messageResponseEntity;
    }

    @PostMapping("/question/save")
    public ResponseEntity<Message<Object>> saveArticle(@RequestParam("title")String title, @RequestParam("content") String content,
                                                       @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        ResponseEntity<Message<Object>> messageResponseEntity;
        long memberId = (long)jwtCertificationToken.getCredentials();
        boolean bResult = questionService.save(title, content, memberId);
        if(bResult){
            messageResponseEntity =  responseEntityUtils.getMessageResponseEntityOK("문의사항 등록 성공");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("문의사항 등록 실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/question/admin/delete")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> deleteArticle(@RequestParam("questionId")long questionId){
        boolean bResult = questionService.delete(questionId);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(bResult){
            messageResponseEntity =  responseEntityUtils.getMessageResponseEntityOK("문의사항 삭제 성공");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("문의사항 삭제 실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/question/admin/comment/save")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> saveComment(@RequestParam("questionId") long questionId, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken, @RequestParam("content")String content){
        long memberId = (Long)jwtCertificationToken.getCredentials();
        boolean bResult = commentService.save(questionId, content, memberId);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(bResult){
            messageResponseEntity =  responseEntityUtils.getMessageResponseEntityOK("문의사항 삭제 성공");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("문의사항 삭제 실패");
        }
        return messageResponseEntity;
    }

    @PutMapping("/question/admin/comment/update")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> updateComment(@RequestParam("commentId") long commentId, @RequestParam("content") String content){
        boolean bResult = commentService.update(commentId, content);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(bResult){
            messageResponseEntity =  responseEntityUtils.getMessageResponseEntityOK("문의사항 댓글 수정 성공");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("문의사항 댓글 수정 실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/question/admin/comment/delete")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> deleteComment(@RequestParam("commentId") long commentId){
        boolean bResult = commentService.delete(commentId);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(bResult){
            messageResponseEntity =  responseEntityUtils.getMessageResponseEntityOK("문의사항 댓글 삭제 성공");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("문의사항 댓글 삭제 실패");
        }
        return messageResponseEntity;
    }

}
