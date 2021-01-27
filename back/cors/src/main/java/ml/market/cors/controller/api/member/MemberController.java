package ml.market.cors.controller.api.member;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.member.service.MemberManagement;
import ml.market.cors.domain.member.service.MemberVO;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class MemberController {
    @Autowired
    private MemberManagement memberManagement;

    @PostMapping("/check/nickname")
    public void existNickname(@RequestBody String nickname, HttpServletResponse response){
        if(memberManagement.existNickname(nickname)) {
            response.setStatus(421);
        }
    }

    @PostMapping("/check/email")
    public void existEmail(@RequestBody String email, HttpServletResponse response){
        if(memberManagement.existEmail(email)){
            response.setStatus(411);
        }
    }

    @PostMapping("/join")
    public void join(@RequestBody MemberVO memberVo, HttpServletResponse response) {
        try{
            if(!memberManagement.create(memberVo)){
                response.setStatus(400);
                return;
            }
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
    }
}
