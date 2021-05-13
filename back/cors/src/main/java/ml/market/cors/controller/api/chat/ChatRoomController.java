package ml.market.cors.controller.api.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.service.ArticleService;
import ml.market.cors.domain.chat.entity.Chatting_room_joinDAO;
import ml.market.cors.domain.chat.entity.dto.ChattingRoomDTO;
import ml.market.cors.domain.chat.service.ChatService;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Errors;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatService chatService;
    private final ArticleService articleService;
    private final ResponseEntityUtils responseEntityUtils;


    @PostMapping("/room")
    public ResponseEntity<Message<Object>> createRoom(
            @RequestParam Long articleId,
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
            ){
        ArticleDAO articleDAO =null;
        Chatting_room_joinDAO chattingRoom = null;
        try {
            articleDAO = findArticle(articleId)
                    .orElseThrow(() -> new IllegalArgumentException("article_id에 해당하는 게시글이 없습니다."));
            ChattingRoomDTO chattingRoomDTO = chatService.findByMemberIdAndArticleId(jwtCertificationToken, articleId);
            if(chattingRoomDTO!=null){
                return responseEntityUtils.getMessageResponseEntityOK(chattingRoomDTO);
            }else {
                if(!chatService.articleMemberEqMember(jwtCertificationToken,articleId)){
                    return responseEntityUtils.getMessageResponseEntityBadRequest(
                            "잘못된 요청입니다. 자기 자신과는 대화할 수 없습니다."
                    );
                }
                chattingRoom = chatService.createChattingRoom(jwtCertificationToken, articleDAO);
            }
        }catch (IllegalArgumentException e){
            return responseEntityUtils.getMessageResponseEntityBadRequest(
                    new Errors(
                            "URI",
                            "article_id",
                            articleId.toString(),
                            articleId + "에 해당하는 글이 없습니다.")
            );
        }catch (IllegalStateException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }


        return responseEntityUtils.getMessageResponseEntityCreated(
                new ChatRoom(
                        chattingRoom.getJoin_id(),
                        chattingRoom.getMember().getNickname(),
                        chattingRoom.getChatting_room().getChatting_id()
                )
        );
    }

    @GetMapping("/rooms")
    public ResponseEntity<Message<Object>> rooms(
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
    ){
        List<ChattingRoomDTO> chatRoomList = null;
        try{
            chatRoomList = chatService.findAllByChatRoom(jwtCertificationToken);
        }catch (IllegalStateException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }

        return responseEntityUtils.getMessageResponseEntityOK(chatRoomList);
    }

    @GetMapping("/room/{joinId}")
    public ResponseEntity<Message<Object>> roomDetail(
            @PathVariable String joinId,
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        ChattingRoomDTO findChatRoom = chatService.findOne(joinId);
        return responseEntityUtils.getMessageResponseEntityOK(chatService.findAllByMessage(findChatRoom.getChattingId()));
    }



    @Data
    @AllArgsConstructor
    static class ChatRoom{
        private String joinId;
        private String nickname;
        private Long chattingId;
    }

    private Optional<ArticleDAO> findArticle(Long article_id){
        ArticleDAO findArticle = articleService.findById(article_id);
        if(findArticle!=null){
            return Optional.of(findArticle);
        }
        return Optional.empty();
    }
}
