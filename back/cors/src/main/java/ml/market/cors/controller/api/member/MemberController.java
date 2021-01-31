package ml.market.cors.controller.api.member;

import ml.market.cors.domain.mail.service.EmailManagement;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.member.service.MemberManagement;
import ml.market.cors.domain.member.service.MemberVO;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class MemberController {
    @Autowired
    private MemberManagement memberManagement;

    @Autowired
    private EmailManagement emailManagement;

    @Autowired
    private ResponseEntityUtils responseEntityUtils;

    @PostMapping("/check/nickname")
    public ResponseEntity<Message<Object>> existNickname(@ModelAttribute MemberVO memberVO, HttpServletResponse response){
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            if(memberManagement.existNickname(memberVO.getNickname()) == false){
                throw new Exception();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("닉네임 중복 입니다");
        }
        return messageResponseEntity;
    }

    @PostMapping("/check/email")
    public ResponseEntity<Message<Object>> existEmail(@RequestParam("email") String email, HttpServletResponse response){
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
    public ResponseEntity<Message<Object>> isCode(@ModelAttribute MailVO mailVO, HttpServletResponse response){
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            if (emailManagement.checkCode(mailVO) == false) {
                throw new Exception();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("코드 틀렸습니다.");
        }
        return messageResponseEntity;
    }

    @PostMapping("/join")
    public ResponseEntity<Message<Object>> join(@ModelAttribute MemberVO memberVo, HttpServletResponse response) {
        ResponseEntity<Message<Object>> messageResponseEntity;
        try{
            if(memberManagement.create(memberVo) == false){
                throw new Exception();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(null);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("회원가입 실패");
        }
        return messageResponseEntity;
    }
}
