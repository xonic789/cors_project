package ml.market.cors.domain.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="questionBoard")
@Getter()
@NoArgsConstructor
public class QuestionBoardDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionid")
    private Long questionId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writedate")
    private LocalDateTime writeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    public QuestionBoardDAO(String title, String content, LocalDateTime writeDate, MemberDAO member) {
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.member = member;
    }

    public QuestionBoardDAO(Long questionId) {
        this.questionId = questionId;
    }
}
