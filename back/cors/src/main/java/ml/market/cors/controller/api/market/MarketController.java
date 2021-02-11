package ml.market.cors.controller.api.market;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.market.entity.dto.MarketApproveStatusUpdateDTO;
import ml.market.cors.domain.market.entity.vo.MarketViewVO;
import ml.market.cors.domain.market.enums.MarketKey;
import ml.market.cors.domain.market.service.MarketService;
import ml.market.cors.domain.market.entity.vo.MarketApproveListVO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/market/")
@RequiredArgsConstructor
public class MarketController {
    private final ResponseEntityUtils responseEntityUtils;

    private final MarketService marketService;

    @GetMapping("/approve/list")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> list(Pageable pageable){
        List<MarketApproveListVO> marketList = marketService.list(pageable);
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(marketList);
        return messageResponseEntity;
    }

    /*
    ACCEPT면은 멤버 권한 변경
     */
    @PutMapping("/approve/update")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> update(@ModelAttribute MarketApproveStatusUpdateDTO marketApproveStatusUpdateDTO, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        long memberId = (long)jwtCertificationToken.getCredentials();
        boolean bResult = marketService.updateStatus(marketApproveStatusUpdateDTO, memberId);
        if(!bResult){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("상태 수정 실패");
        } else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("상태 수정 성공");
        }
        return messageResponseEntity;
    }

    /*
    페이징 처리하기
     */
    @PostMapping("/view")
    public ResponseEntity<Message<Object>> view(@RequestParam("marketId") long marketId, Pageable pageable){
        MarketViewVO marketViewVO = marketService.view(marketId, pageable);
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(marketViewVO);
        return messageResponseEntity;
    }

    @PostMapping("/save")
    public ResponseEntity<Message<Object>> save(@RequestParam Map<MarketKey, Object> marketInfo, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
    , @RequestPart("image") MultipartFile imageFile){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        String email = jwtCertificationToken.getName();
        long memberId = (long)jwtCertificationToken.getCredentials();
        try{
            marketService.save(marketInfo, email, memberId, imageFile);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("마켓 승인 요청 실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/delete")
    @Secured("ROLE_CEO")
    public ResponseEntity<Message<Object>> delete(@RequestParam("marketId") long marketId, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        long memberId = (long)jwtCertificationToken.getCredentials();
        boolean bResult = marketService.delete(marketId, memberId);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(!bResult){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("삭제 실패");
        } else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("삭제 성공");
        }

        return messageResponseEntity;
    }
}
