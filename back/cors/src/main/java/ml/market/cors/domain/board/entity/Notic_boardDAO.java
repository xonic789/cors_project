package ml.market.cors.domain.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Notic_board")
@NoArgsConstructor
@Getter
public class Notic_boardDAO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name ="write_date", updatable = false)
    private LocalDateTime writeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberDAO member;

    public Notic_boardDAO(Long noticeId, String title, String content, MemberDAO member) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Notic_boardDAO(String title, String content, LocalDateTime writeDate, MemberDAO member) {
        this.title = title;
        this.writeDate = writeDate;
        this.content = content;
        this.member = member;
    }
}
