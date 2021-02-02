package ml.market.cors.controller.api.member;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.mail.service.EmailManagement;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.member.service.MemberManagement;
import ml.market.cors.domain.member.service.MemberVO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberManagement memberManagement;

    private final EmailManagement emailManagement;

    private final ResponseEntityUtils responseEntityUtils;

    @PostMapping("/check/nickname")
    public ResponseEntity<Message<Object>> existNickname(@ModelAttribute MemberVO memberVO){
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            if(memberManagement.existNickname(memberVO.getNickname())){
                throw new Exception();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("닉네임 중복 입니다");
        }
        return messageResponseEntity;
    }

    @PostMapping("/check/email")
    public ResponseEntity<Message<Object>> existEmail(@RequestParam("email") String email){
        ResponseEntity<Message<Object>> messageResponseEntity;
        try{
            emailManagement.insert(email);
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("이메일 중복 입니다.");
        }
        return messageResponseEntity;
    }

    @PostMapping("/check/code")
    public ResponseEntity<Message<Object>> isCode(@ModelAttribute MailVO mailVO){
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            if (!emailManagement.checkCode(mailVO)) {
                throw new Exception();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("코드 틀렸습니다.");
        }
        return messageResponseEntity;
    }

    @PostMapping("/join")
    public ResponseEntity<Message<Object>> join(@ModelAttribute MemberVO memberVo) {
        ResponseEntity<Message<Object>> messageResponseEntity;
        try{
            if(!memberManagement.create(memberVo)){
                throw new Exception();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("회원가입 실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/change/profile")
    public ResponseEntity<Message<Object>> change(@AuthenticationPrincipal JwtCertificationToken memberIdentify
                                                  ,@RequestParam Map<String, String> member) {
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            if (memberManagement.change(member,(Long) memberIdentify.getCredentials())) {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
            } else {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("프로필 변경 실패");
            }
        } catch (Exception e) {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("프로필 변경 실패");
        }

        return messageResponseEntity;
    }
}
