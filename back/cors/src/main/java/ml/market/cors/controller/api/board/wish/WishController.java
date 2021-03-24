package ml.market.cors.controller.api.board.wish;


import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.service.WishService;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WishController {
    private final ResponseEntityUtils responseEntityUtils;

    private final WishService wishService;

    @GetMapping("/api/wish/count")
    public ResponseEntity<Message<Object>> getArticleWishCount(@RequestParam("articleId") long articleId) {
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            int count = wishService.getCount(articleId);
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK(count);
        } catch (Exception e) {
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("카운트 조회실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/api/wish/save")
    public ResponseEntity<Message<Object>> saveByArticleWish(@RequestParam("articleId") long articleId, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken) {
        long memberId = (long) jwtCertificationToken.getCredentials();
        ResponseEntity<Message<Object>> messageResponseEntity;
        try {
            boolean bResult = wishService.save(memberId, articleId);
            if (bResult) {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("찜 저장 성공");
            } else {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("찜 저장 실패");
            }
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("찜 저장 실패");
        }
        return messageResponseEntity;
    }

    @PostMapping("/api/wish/delete")
    public ResponseEntity<Message<Object>> deleteByArticleWish(@RequestParam("articleId") long articleId, @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken) {
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        long memberId = (long) jwtCertificationToken.getCredentials();
        try {
            boolean bResult = wishService.delete(articleId, memberId);
            if (bResult) {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityOK("찜 삭제 성공");
            } else {
                messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("찜 삭제 실패");
            }
        }catch (Exception e){
            messageResponseEntity = responseEntityUtils.getMessageResponseEntityBadRequest("찜 삭제 실패");
        }
        return messageResponseEntity;
    }
}