package ml.market.cors.domain.chat.entity;

import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;

@Entity
@Table(name = "chatting_room_join")
public class Chatting_room_joinDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_id")
    private Long join_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleDAO article;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_id")
    private Chatting_roomDAO chatting_room;


}
