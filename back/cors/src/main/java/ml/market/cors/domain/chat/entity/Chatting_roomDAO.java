package ml.market.cors.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "chatting_room")
@Getter
@NoArgsConstructor
public class Chatting_roomDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_id")
    private Long chatting_id;
}
