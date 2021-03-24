package ml.market.cors.domain.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.board.entity.QuestionBoardDAO;
import ml.market.cors.domain.comment.enums.eBoardType;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="commentBoard")
@Getter()
@NoArgsConstructor
public class CommentBoardDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentid")
    private Long commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "writedate")
    private LocalDateTime writeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionid")
    private QuestionBoardDAO question;

    public CommentBoardDAO(String content, LocalDateTime writeDate, MemberDAO member, QuestionBoardDAO question) {
        this.content = content;
        this.writeDate = writeDate;
        this.member = member;
        this.question = question;
    }

    public String updateContent(String content){
        this.content = content;
        return this.content;
    }
}
