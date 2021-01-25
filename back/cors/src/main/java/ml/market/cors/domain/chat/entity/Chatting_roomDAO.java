package ml.market.cors.domain.chat.entity;

import javax.persistence.*;

@Entity
@Table(name = "chatting_room")
public class Chatting_roomDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_id")
    private Long chatting_id;
}
