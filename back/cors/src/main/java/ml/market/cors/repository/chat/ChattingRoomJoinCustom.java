package ml.market.cors.repository.chat;

import ml.market.cors.domain.chat.entity.Chatting_roomDAO;
import ml.market.cors.domain.chat.entity.dto.ChattingRoomDTO;
import ml.market.cors.domain.member.entity.MemberDAO;

import java.util.List;

public interface ChattingRoomJoinCustom {

    List<ChattingRoomDTO> findChatAll(MemberDAO memberDAO);

    ChattingRoomDTO findByJoinId(String join_id);

    ChattingRoomDTO findByMemberIdAndArticleId(Long memberId,Long articleId);

    long ownerArticleMember(Long memberId,Long articleId);

}
