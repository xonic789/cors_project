package ml.market.cors.controller.api.member;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.mail.service.EmailManagement;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.member.service.MemberManagement;
import ml.market.cors.domain.member.service.MemberVO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import ml.market.cors.repository.article.Wish_list_Repository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberManagement memberManagement;

    private final EmailManagement emailManagement;

    private final ResponseEntityUtils responseEntityUtils;

    private final MemberRepository memberRepository;

    private final Wish_list_Repository wish_list_repository;

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

    private Map<String, Object> setMember(long memberId){
        Map<String, Object> member = new HashMap<>();
        MemberDAO memberDAO = memberRepository.findByMemberId(memberId).get(0);
        member.put(MemberParam.LATITUDE, memberDAO.getLatitude());
        member.put(MemberParam.LONGITUDE, memberDAO.getLongitude());
        member.put(MemberParam.ROLE, memberDAO.getRole());
        member.put(MemberParam.NICKNAME, memberDAO.getNickname());
        member.put(MemberParam.EMAIL, memberDAO.getEmail());
        member.put(MemberParam.PROFILE_IMG, memberDAO.getProfile_img());
        List<Wish_listDAO> wish_listDAOList = memberDAO.getWish_listDAO();
        if(wish_listDAOList.size() > 0){
            member.put(MemberParam.WISHLIST,wish_listDAOList);
        }
        return member;
    }

    @PutMapping("/change/profile")
    public ResponseEntity<Message<Object>> change(@AuthenticationPrincipal JwtCertificationToken memberIdentify
                                                  , @RequestParam Map<String, Object> member
            , @RequestPart("profile_img")MultipartFile multipartFile) {
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            long memberId = (Long) memberIdentify.getCredentials();
            if (memberManagement.change(member, memberId, multipartFile)) {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(setMember(memberId));
            } else {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("프로필 변경 실패");
            }
        } catch (Exception e) {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("프로필 변경 실패");
        }

        return messageResponseEntity;
    }

    @GetMapping("/mypage")
    public ResponseEntity<Message<Object>> getMypage(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
    , HttpServletResponse response){
        ResponseEntity<Message<Object>> messageResponseEntity = null;

        Map result = memberManagement.viewProfile(response, (long)jwtCertificationToken.getCredentials());
        if(result == null){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("프로필 로드 실패");
        }
        else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(result);
        }
        return messageResponseEntity;
    }


}
