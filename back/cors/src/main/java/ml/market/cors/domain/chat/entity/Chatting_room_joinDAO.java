package ml.market.cors.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chatting_room_join")
@Getter
@NoArgsConstructor
public class Chatting_room_joinDAO {

    @Id
    @Column(name = "join_id")
    private String join_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleDAO article;

    @OneToOne
    @JoinColumn(name = "chatting_id")
    private Chatting_roomDAO chatting_room;

    public Chatting_room_joinDAO(String join_id, MemberDAO member, ArticleDAO article, Chatting_roomDAO chatting_room) {
        this.join_id = join_id;
        this.member = member;
        this.article = article;
        this.chatting_room = chatting_room;
    }

    public static Chatting_room_joinDAO createChatting(ArticleDAO articleDAO,MemberDAO memberDAO){
        return new Chatting_room_joinDAO(
                UUID.randomUUID().toString(),
                memberDAO,
                articleDAO,
                new Chatting_roomDAO());
    }
}
