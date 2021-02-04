package ml.market.cors.domain.article.entity.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="count")
@Getter
@NoArgsConstructor
public class CountDAO {
    @Column(name = "chat_count")
    private int chatCount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "count_id")
    private Long countId;


    @Column(name = "views")
    private int views;

    @Column(name = "wish_count")
    private int wishCount;

    public void updateViewCount(int views){
        this.views=views;
    }
    public void updateChatCount(int chatCount){
        this.chatCount =chatCount;
    }
    public void updateWishCount(int wishCount){
        this.wishCount =wishCount;
    }

}
