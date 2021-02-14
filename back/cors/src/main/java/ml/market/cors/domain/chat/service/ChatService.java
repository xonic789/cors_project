package ml.market.cors.domain.chat.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.chat.entity.dto.ChatMessage;
import ml.market.cors.domain.chat.entity.Chatting_roomDAO;
import ml.market.cors.domain.chat.entity.Chatting_room_joinDAO;
import ml.market.cors.domain.chat.entity.MessageDAO;
import ml.market.cors.domain.chat.entity.dto.ChattingRoomDTO;
import ml.market.cors.domain.chat.entity.dto.MessageDTO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.repository.chat.ChattingRoomJoinRepository;
import ml.market.cors.repository.chat.ChattingRoomRepository;
import ml.market.cors.repository.chat.MessageRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChattingRoomJoinRepository chattingRoomJoinRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;


    public Chatting_room_joinDAO createChattingRoom(JwtCertificationToken jwtCertificationToken, ArticleDAO articleDAO) throws IllegalStateException{
        MemberDAO member = findMember(jwtCertificationToken).orElseThrow(() -> new IllegalStateException());

        Chatting_room_joinDAO chatting = Chatting_room_joinDAO.createChatting(articleDAO, member);
        chattingRoomRepository.save(chatting.getChatting_room());
        return chattingRoomJoinRepository.save(chatting);
    }

    public MessageDAO createMessage(JwtCertificationToken jwtCertificationToken, ChatMessage chatMessage,Chatting_roomDAO chatting_roomDAO) throws IllegalStateException{
        MemberDAO member = findMember(jwtCertificationToken).orElseThrow(() -> new IllegalStateException());
        MessageDAO message = MessageDAO.createMessage(chatMessage, member , chatting_roomDAO);
        return messageRepository.save(message);
    }

    public List<ChattingRoomDTO> findAllByChatRoom(JwtCertificationToken jwtCertificationToken) {
        MemberDAO member = findMember(jwtCertificationToken).orElseThrow(() -> new IllegalStateException());
        return chattingRoomJoinRepository.findChatAll(member);
    }

    public ChattingRoomDTO findOne(String joinId){
        return chattingRoomJoinRepository.findByJoinId(joinId);
    }

    public List<MessageDTO> findAllByMessage(Long chattingId){
        return messageRepository.findAllMessage(chattingId);
    }


    private Optional<MemberDAO> findMember(JwtCertificationToken jwtCertificationToken) {
        if(jwtCertificationToken!=null){
            String email = jwtCertificationToken.getName();
            return Optional.of(memberRepository.findByEmail(email));
        }
        return Optional.empty();
    }




}
