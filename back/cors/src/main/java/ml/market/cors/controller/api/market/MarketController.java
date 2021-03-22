package ml.market.cors.controller.api.market;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.market.entity.dto.MarketArticleDTO;
import ml.market.cors.domain.market.entity.dto.MarketDTO;
import ml.market.cors.domain.market.entity.search.MarketSearchCondition;
import ml.market.cors.domain.market.enums.MarketStatus;
import ml.market.cors.domain.market.service.MarketMenuService;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Errors;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarketController {

    private final MarketMenuService marketMenuService;
    private final ResponseEntityUtils responseEntityUtils;

    @GetMapping("/api/markets")
    public ResponseEntity<Message<Object>> getMarkets(
            @ModelAttribute MarketSearchCondition marketSearchCondition){
        return responseEntityUtils.getMessageResponseEntityOK(marketMenuService.findAll(marketSearchCondition));
    }

    @GetMapping("/api/market/{marketId}")
    public ResponseEntity<Message<Object>> marketDetail(@PathVariable Long marketId){
        MarketDTO market = marketMenuService.findArticlesByMarketId(marketId);
        if(market==null){
            return responseEntityUtils.getMessageResponseEntityBadRequest("없는 게시물 입니다."+"marketId = "+marketId);
        }
        if(!market.getStatus().equals(MarketStatus.ACCEPT)){
            return responseEntityUtils.getMessageResponseEntityBadRequest("승인되지 않은 게시물 입니다."+"marketId = "+marketId);
        }

        return responseEntityUtils.getMessageResponseEntityOK(market);
    }

    @GetMapping("/api/member/markets")
    public ResponseEntity<Message<Object>> getMarketsByMemberLocation(
            @ModelAttribute MarketSearchCondition marketSearchCondition,
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        List<MarketDTO> allByMemberLocation = null;

        try{
            allByMemberLocation = marketMenuService.findAllByMemberLocation(jwtCertificationToken, marketSearchCondition);
        }catch (IllegalStateException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }
        return responseEntityUtils.getMessageResponseEntityOK(allByMemberLocation);
    }

}
