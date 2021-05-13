package ml.market.cors.repository.chat;

import ml.market.cors.domain.chat.entity.MessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageDAO, Long>,MessageCustom{
}
