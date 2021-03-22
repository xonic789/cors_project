package ml.market.cors.repository.chat;

import ml.market.cors.domain.chat.entity.Chatting_room_joinDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRoomJoinRepository extends JpaRepository<Chatting_room_joinDAO, String>,ChattingRoomJoinCustom {

}
