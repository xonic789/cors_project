package ml.market.cors.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name="member")
@Getter
@NoArgsConstructor
public class MemberDAO {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "passwd")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longtitude")
    private Integer longtitude;

    @Column(name = "nickname")
    private String nickname;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public void createMember(String email){
        this.email=email;
    }

}
