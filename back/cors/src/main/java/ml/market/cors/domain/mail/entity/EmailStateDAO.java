package ml.market.cors.domain.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="email_state")
public class EmailStateDAO {
    @Id
    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "authenticated_flag")
    private eMailAuthenticatedFlag authenticatedFlag;

    @Column(name = "expire_time")
    private long expireTime;

    @Column(name = "code")
    private int code;
}
