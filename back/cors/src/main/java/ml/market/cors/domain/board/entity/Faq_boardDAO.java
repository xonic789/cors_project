package ml.market.cors.domain.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="faq_board")
@Getter()
@NoArgsConstructor
public class Faq_boardDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long faq_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

}
