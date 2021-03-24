package ml.market.cors.repository.chat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ml.market.cors.domain.chat.entity.dto.MessageDTO;
import ml.market.cors.domain.chat.entity.dto.QMessageDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static ml.market.cors.domain.chat.entity.QChatting_roomDAO.chatting_roomDAO;
import static ml.market.cors.domain.chat.entity.QMessageDAO.messageDAO;
import static ml.market.cors.domain.member.entity.QMemberDAO.memberDAO;

@Repository
public class MessageRepositoryImpl implements MessageCustom{

    private final JPAQueryFactory query;

    public MessageRepositoryImpl(EntityManager em) {
        this.query=new JPAQueryFactory(em);
    }


    @Override
    public List<MessageDTO> findAllMessage(Long chattingId) {
        return query
                .select(new QMessageDTO(
                        messageDAO.chat_contents,
                        messageDAO.member.nickname,
                        messageDAO.written_date
                ))
                .from(messageDAO)
                .join(messageDAO.member,memberDAO).fetchJoin()
                .join(messageDAO.chatting_room, chatting_roomDAO).fetchJoin()
                .where(messageDAO.chatting_room.chatting_id.eq(chattingId))
                .orderBy(messageDAO.message_id.desc())
                .fetch();
    }
}
