package ml.market.cors.controller.api.member;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.mail.service.EmailManagement;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.member.service.MemberManagement;
import ml.market.cors.domain.member.service.MemberVO;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.map.KaKaoMapManagement;
import ml.market.cors.domain.util.map.KakaoResMapVO;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class MemberController {
    @Autowired
    private MemberManagement memberManagement;

    @Autowired
    private EmailManagement emailManagement;

    @PostMapping("/check/nickname")
    public void existNickname(@ModelAttribute MemberVO memberVO, HttpServletResponse response){
        try {
            if (memberManagement.existNickname(memberVO.getNickname())) {
                throw new Exception();
            }
        }catch (Exception e){
            response.setStatus(421);
        }
    }

    @PostMapping("/check/email")
    public void existEmail(@RequestParam("email") String email, HttpServletResponse response){
        try{
            emailManagement.insert(email);
        }catch (Exception e){
            response.setStatus(411);
        }
    }

    @PostMapping("/check/code")
    public void isCode(@ModelAttribute MailVO mailVO, HttpServletResponse response){
        try{
            if(!emailManagement.checkCode(mailVO)){
                response.setStatus(411);
            }
        }catch (Exception e){
            response.setStatus(411);
        }
    }

    @PostMapping("/join")
    public void join(@ModelAttribute MemberVO memberVo, HttpServletResponse response) {
        try{
            if(!memberManagement.create(memberVo)){
                response.setStatus(411);
                return;
            }
        }catch (Exception e){
            response.setStatus(411);
            return;
        }
    }
}
