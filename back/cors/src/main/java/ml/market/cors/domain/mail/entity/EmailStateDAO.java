package ml.market.cors.domain.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="email_state")
public class EmailStateDAO {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "authenticated_flag")
    private char authenticatedFlag;

    @Column(name = "expire_time")
    private long expireTime;

    @Column(name = "code")
    private int code;
}
