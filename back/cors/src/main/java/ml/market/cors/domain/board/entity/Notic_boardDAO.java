package ml.market.cors.domain.board.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notic_board")
public class Notic_boardDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long notice_id;

    private String title;

    private String content;

    private LocalDateTime write_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberDAO member;



}
