package ml.market.cors.controller.api.member;

import io.jsonwebtoken.Jwt;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ml.market.cors.controller.api.member.dto.DivisionPageDTO;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.article.entity.dto.MyWishListDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.service.WishService;
import ml.market.cors.domain.mail.service.EmailManagement;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.market.service.MarketService;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.member.service.MemberManagement;
import ml.market.cors.domain.member.service.MemberVO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import ml.market.cors.repository.article.Wish_list_Repository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberManagement memberManagement;

    private final MarketService marketService;

    private final EmailManagement emailManagement;

    private final ResponseEntityUtils responseEntityUtils;

    private final WishService wishService;

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

    @PutMapping("/change/profile")
    public ResponseEntity<Message<Object>> change(@AuthenticationPrincipal JwtCertificationToken memberIdentify
                                                  , @RequestParam Map<String, Object> member
            , @RequestPart(value = "profile_img", required = false)MultipartFile multipartFile) {
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            long memberId = (Long) memberIdentify.getCredentials();
            if (memberManagement.change(member, memberId, multipartFile)) {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(memberManagement.setMember(memberId));
            } else {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("프로필 변경 실패");
            }
        } catch (Exception e) {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("프로필 변경 실패");
        }

        return messageResponseEntity;
    }

    @GetMapping("/mypage")
    public ResponseEntity<Message<Object>> getView(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
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

    @GetMapping("/mypage/purchase")
    public ResponseEntity<Message<Object>> getMemberPurchase(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken, @RequestParam("page") int pageIndex){
        long memberId = (long)jwtCertificationToken.getCredentials();
        DivisionPageDTO divisionPageDTO = memberManagement.getDivisionList(memberId, Division.PURCHASE,pageIndex);
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(divisionPageDTO);
        return messageResponseEntity;
    }

    @GetMapping("/mypage/sales")
    public ResponseEntity<Message<Object>> getMemberSales(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
            , @RequestParam("page") int pageIndex){
        long memberId = (long)jwtCertificationToken.getCredentials();
        DivisionPageDTO divisionPageDTO = memberManagement.getDivisionList(memberId, Division.SALES, pageIndex);
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(divisionPageDTO);
        return messageResponseEntity;
    }

    @GetMapping("/mypage/wishs")
    public ResponseEntity<Message<Object>> getMemberWishs(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
            ,@RequestParam("page") int page){
        long memberId = (long)jwtCertificationToken.getCredentials();
        ResponseEntity<Message<Object>> messageResponseEntity;
        MyWishListDTO myWishListDTO = wishService.getMyWishList(memberId, page);
        if(myWishListDTO == null){
             messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("잘못된 아이디");
        }else {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(myWishListDTO);
        }
        return messageResponseEntity;
    }

    @GetMapping("/mypage/market")
    public ResponseEntity<Message<Object>> getMymarket(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
    , @RequestParam("page") int page){
        long memberId = (long)jwtCertificationToken.getCredentials();
        Object result = marketService.getMyMarketList(memberId, page);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(result == null) {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("잘못된 아이디");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(result);
        }
        return messageResponseEntity;
    }

    @GetMapping("/mypage/question")
    public void getMyArticle(){

    }
}
