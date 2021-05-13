package ml.market.cors.domain.chat.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.chat.entity.dto.ChatMessage;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "message")
@Getter
@NoArgsConstructor
public class MessageDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long message_id;

    @Column(name = "chat_contents")
    private String chat_contents;

    @Column(name = "written_date")
    private LocalDateTime written_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_id")
    private Chatting_roomDAO chatting_room;

    public MessageDAO(String chat_contents, LocalDateTime written_date, MemberDAO member, Chatting_roomDAO chatting_room) {
        this.chat_contents = chat_contents;
        this.written_date = written_date;
        this.member = member;
        this.chatting_room = chatting_room;
    }

    public static MessageDAO createMessage(ChatMessage message,MemberDAO member,Chatting_roomDAO chatting_room){
        return new MessageDAO(
                message.getContent(),
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                member,
                chatting_room);
    }
}
