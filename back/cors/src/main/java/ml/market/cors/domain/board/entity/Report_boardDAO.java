package ml.market.cors.domain.board.entity;

import lombok.Getter;
import ml.market.cors.domain.board.entity.dto.ReportForm;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "report_board")
@Getter
public class Report_boardDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long report_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "write_date")
    private LocalDateTime write_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    public Report_boardDAO(String title, String content, LocalDateTime write_date, MemberDAO member) {
        this.title = title;
        this.content = content;
        this.write_date = write_date;
        this.member = member;
    }

    public static Report_boardDAO createReportBoard(ReportForm reportForm, MemberDAO member){
        return new Report_boardDAO(
                reportForm.getTitle(),
                reportForm.getContent(),
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                member
        );
    }

}
