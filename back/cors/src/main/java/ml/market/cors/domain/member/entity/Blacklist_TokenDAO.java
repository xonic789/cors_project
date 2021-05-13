package ml.market.cors.domain.member.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="blacklist_token")
@Getter()
@NoArgsConstructor
public class Blacklist_TokenDAO {
    @Id
    @Column(name = "hash")
    private String hash;
  
    @Column(name = "expire_date")
    private Long expire_date;

    public Blacklist_TokenDAO(String hash, Long expireDate) {
        this.hash = hash;
        this.expire_date = expireDate;
    }

    public Blacklist_TokenDAO(String hash) {
        this.hash = hash;
    }

}
