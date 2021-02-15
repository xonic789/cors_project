package ml.market.cors.repository.chat;


import com.querydsl.jpa.impl.JPAQueryFactory;
import ml.market.cors.domain.article.entity.dao.QArticleDAO;
import ml.market.cors.domain.chat.entity.Chatting_roomDAO;
import ml.market.cors.domain.chat.entity.QChatting_roomDAO;
import ml.market.cors.domain.chat.entity.dto.ChattingRoomDTO;
import ml.market.cors.domain.chat.entity.dto.QChattingRoomDTO;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static ml.market.cors.domain.article.entity.dao.QArticleDAO.articleDAO;
import static ml.market.cors.domain.chat.entity.QChatting_roomDAO.chatting_roomDAO;
import static ml.market.cors.domain.chat.entity.QChatting_room_joinDAO.chatting_room_joinDAO;
import static ml.market.cors.domain.chat.entity.QMessageDAO.messageDAO;
import static ml.market.cors.domain.member.entity.QMemberDAO.memberDAO;

@Repository
public class ChattingRoomJoinRepositoryImpl implements ChattingRoomJoinCustom{

    private final JPAQueryFactory query;

    public ChattingRoomJoinRepositoryImpl(EntityManager em) {
        this.query=new JPAQueryFactory(em);
    }


    @Override
    public List<ChattingRoomDTO> findChatAll(MemberDAO member) {

        return query
                .select(new QChattingRoomDTO(
                        chatting_room_joinDAO.join_id,
                        chatting_room_joinDAO.article.member.nickname,
                        chatting_room_joinDAO.article.title,
                        chatting_room_joinDAO.article.tprice,
                        chatting_room_joinDAO.chatting_room.chatting_id
                ))
                .from(chatting_room_joinDAO)
                .join(chatting_room_joinDAO.member,memberDAO)
                .join(chatting_room_joinDAO.article, articleDAO)
                .join(chatting_room_joinDAO.chatting_room, chatting_roomDAO)
                .where(
                        chatting_room_joinDAO.member.member_id.eq(member.getMember_id())
                                .or(chatting_room_joinDAO.article.member.member_id.eq(member.getMember_id())))
                .orderBy(chatting_room_joinDAO.chatting_room.chatting_id.desc())
                .fetch();
    }


    @Override
    public ChattingRoomDTO findByJoinId(String join_id) {
        return query
                .select(new QChattingRoomDTO(
                        chatting_room_joinDAO.join_id,
                        chatting_room_joinDAO.article.title,
                        chatting_room_joinDAO.chatting_room.chatting_id
                ))
                .from(chatting_room_joinDAO)
                .join(chatting_room_joinDAO.chatting_room)
                .join(chatting_room_joinDAO.article)
                .where(
                        chatting_room_joinDAO.join_id.eq(join_id))
                .fetchOne();
    }

    @Override
    public ChattingRoomDTO findByMemberIdAndArticleId(Long memberId,Long articleId) {

        return query
                .select(new QChattingRoomDTO(
                        chatting_room_joinDAO.join_id,
                        chatting_room_joinDAO.article.title,
                        chatting_room_joinDAO.chatting_room.chatting_id
                ))
                .from(chatting_room_joinDAO)
                .join(chatting_room_joinDAO.chatting_room,chatting_roomDAO)
                .join(chatting_room_joinDAO.article,articleDAO)
                .join(chatting_room_joinDAO.member,memberDAO)
                .where(
                        chatting_room_joinDAO.member.member_id.eq(memberId),
                        chatting_room_joinDAO.article.article_id.eq(articleId))
                .fetchOne();
    }


}
