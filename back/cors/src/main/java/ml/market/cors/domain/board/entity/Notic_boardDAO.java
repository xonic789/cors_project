package ml.market.cors.domain.board.entity;

import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notic_board")
public class Notic_boardDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long notice_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "write_date")
    private LocalDateTime write_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberDAO member;



}
