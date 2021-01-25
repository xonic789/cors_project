package ml.market.cors.domain.board.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report_board")
public class Report_boardDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long report_id;

    private String title;

    private String content;

    private LocalDateTime write_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;


}
