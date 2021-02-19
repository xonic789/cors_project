package ml.market.cors.controller.api.member.admin;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.dto.QuestionMemberDTO;
import ml.market.cors.domain.board.service.QuestionService;
import ml.market.cors.domain.market.entity.dto.MarketApproveStatusUpdateDTO;
import ml.market.cors.domain.market.entity.vo.MarketApproveListVO;
import ml.market.cors.domain.market.service.MarketService;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final ResponseEntityUtils responseEntityUtils;

    private final MarketService marketService;

    private final QuestionService questionService;

    @GetMapping("/api/admin/markets/{page}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> getApproveList(@PathVariable("page") int pageIndex){
        List<MarketApproveListVO> marketList = marketService.list(pageIndex);
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(marketList);
        return messageResponseEntity;
    }

    @PutMapping("/api/admin/market/{marketId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> update(@PathVariable("marketId") long marketId, @ModelAttribute MarketApproveStatusUpdateDTO marketApproveStatusUpdateDTO, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        boolean bResult = marketService.updateStatus(marketId, marketApproveStatusUpdateDTO);
        if(!bResult){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("상태 수정 실패");
        } else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("상태 수정 성공");
        }
        return messageResponseEntity;
    }

    @GetMapping("/api/question/admin/list")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> getQuestionList(@RequestParam("page") int pageIndex){
        QuestionMemberDTO questionMemberDTO = questionService.getAllQuestion(pageIndex);
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(questionMemberDTO);
        return messageResponseEntity;
    }
}
