package ml.market.cors.repository.chat;

import ml.market.cors.domain.chat.entity.Chatting_roomDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRoomRepository extends JpaRepository<Chatting_roomDAO, Long> {
}
