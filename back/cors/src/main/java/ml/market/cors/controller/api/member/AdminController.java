package ml.market.cors.controller.api.member;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.service.NoticeBoardService;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import ml.market.cors.repository.board.NoticeBoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ResponseEntityUtils responseEntityUtils;

    private final NoticeBoardService noticeBoardService;

    @GetMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> list(){
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("ok");
        return messageResponseEntity;
    }

    @GetMapping("notice")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> listNotice(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("ok");
        long memberId =(Long)jwtCertificationToken.getCredentials();

        return messageResponseEntity;
    }

    @GetMapping("notice/save")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Message<Object>> save(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("ok");
        long memberId =(Long)jwtCertificationToken.getCredentials();
        try{
            noticeBoardService.save("dd","ewe", memberId);
        } catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest(null);
        }
        return messageResponseEntity;
    }

}
