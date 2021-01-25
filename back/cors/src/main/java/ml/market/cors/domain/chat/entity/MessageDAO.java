package ml.market.cors.domain.chat.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.chat.entity.Chatting_roomDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "massage")
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
    @JoinColumn(name = "member_id" )
    private MemberDAO member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chatting_roomDAO chatting_room;
}
