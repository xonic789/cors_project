package ml.market.cors.repository.chat;

import ml.market.cors.domain.chat.entity.dto.MessageDTO;
import ml.market.cors.domain.member.entity.MemberDAO;

import java.util.List;

public interface MessageCustom {

    List<MessageDTO> findAllMessage(Long chattingId);
}
