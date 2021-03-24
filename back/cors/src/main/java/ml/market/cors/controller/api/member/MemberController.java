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
import ml.market.cors.domain.board.entity.dto.QuestionMemberDTO;
import ml.market.cors.domain.board.service.QuestionService;
import ml.market.cors.domain.mail.service.EmailManagement;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.market.entity.dto.MarketViewDTO;
import ml.market.cors.domain.market.enums.MarketKey;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    private final QuestionService questionService;

    @PostMapping("/check/nickname")
    public ResponseEntity<Message<Object>> existNickname(@RequestParam String nickname){
        ResponseEntity<Message<Object>> messageResponseEntity;
        boolean bResult = memberManagement.existNickname(nickname);
        if(bResult) {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("닉네임 중복 입니다");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("닉네임 중복 아닙니다.");
        }
        return messageResponseEntity;
    }

    @PostMapping("/check/email")
    public ResponseEntity<Message<Object>> existEmail(@RequestParam String email){
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

    @GetMapping("/mypage/markets/{page}")
    public ResponseEntity<Message<Object>> getMymarket(@PathVariable("page") int page, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken) {
        long memberId = (long) jwtCertificationToken.getCredentials();
        Object result = marketService.getMyMarketList(memberId, page);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if (result == null) {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("잘못된 아이디");
        } else {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(result);
        }
        return messageResponseEntity;
    }

    @GetMapping("/mypage/market/detail/{marketId}")
    public ResponseEntity<Message<Object>> view(@PathVariable("marketId") long marketId, @RequestParam("page") int pageIndex){
        MarketViewDTO marketViewDTO = marketService.view(marketId, pageIndex);
        ResponseEntity<Message<Object>> messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(marketViewDTO);
        return messageResponseEntity;
    }

    @PutMapping("/mypage/market/{marketId}")
    public ResponseEntity<Message<Object>> updateMyMarket(@PathVariable("marketId") long marketId, @RequestParam Map<MarketKey, Object> marketInfo, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
            , @RequestPart(value = "image", required = false) MultipartFile imageFile){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        String email = jwtCertificationToken.getName();
        long memberId = (long)jwtCertificationToken.getCredentials();
        try{
            boolean bResult = marketService.updateMyMarket(marketId, marketInfo, email, memberId, imageFile);
            if(!bResult){
                throw new RuntimeException();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("변경 성공");
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("변경 실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/mypage/market")
    public ResponseEntity<Message<Object>> requestRegisterMarket(@RequestParam Map<MarketKey, Object> marketInfo, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
            , @RequestPart("image") MultipartFile imageFile){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        String email = jwtCertificationToken.getName();
        long memberId = (long)jwtCertificationToken.getCredentials();
        try{
            long marketId = marketService.save(marketInfo, email, memberId, imageFile);
            if(marketId == 0){
                throw new RuntimeException();
            }
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(marketId);
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("마켓 승인 요청 실패");
        }
        return messageResponseEntity;
    }

    @DeleteMapping("/mypage/market/{marketId}")
    @Secured("ROLE_CEO")
    public ResponseEntity<Message<Object>> delete(HttpServletResponse response, HttpServletRequest request, @PathVariable("marketId") long marketId, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        long memberId = (long)jwtCertificationToken.getCredentials();
        String email = jwtCertificationToken.getName();
        boolean bResult = marketService.delete(email, marketId, memberId, response, request);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(!bResult){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("삭제 실패");
        } else{

            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("삭제 성공");
        }

        return messageResponseEntity;
    }

    @GetMapping("/mypage/question")
    public ResponseEntity<Message<Object>> getMyQuestionArticle(@RequestParam("page") int page, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        long memberId = (long)jwtCertificationToken.getCredentials();
        String email = jwtCertificationToken.getName();
        ResponseEntity<Message<Object>> messageResponseEntity;
        QuestionMemberDTO questionMemberDTO = questionService.getMyQuestion(page, memberId, email);
        if(questionMemberDTO == null){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("나의페이지 문의하기 목록 가져오기 실패");
        } else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(questionMemberDTO);
        }
        return messageResponseEntity;
    }

    @GetMapping("/mypage/request/mymarket")
    public ResponseEntity<Message<Object>> getRequestMyMarket(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        long memberId = (long)jwtCertificationToken.getCredentials();
        boolean bResult = marketService.exsistsMyMarket(memberId);
        ResponseEntity<Message<Object>> messageResponseEntity;
        if(bResult){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("나의 마켓 등록확인 성공");
        }else{
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("나의 마켓 등록확인실패");
        }
        return messageResponseEntity;
    }
}
