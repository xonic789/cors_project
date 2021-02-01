package ml.market.cors.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enu.SocialType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name="member")
@Getter
@NoArgsConstructor
public class MemberDAO implements UserDetails{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long member_id;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name = "passwd")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name ="social_type")
    private SocialType socialType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List grants = new LinkedList();
        grants.add(new MemberGrantAuthority(this.role));
        return grants;
    }

    public MemberDAO(Long member_id) {
        this.member_id = member_id;
    }

    public MemberDAO(String email, MemberRole role, String password, String address, double latitude, double longitude, String nickname
    ,SocialType socialType) {
        this.email = email;
        this.role = role;
        this.password = password;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nickname = nickname;
        this.socialType = socialType;
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
    public void createMember(String email){
        this.email=email;
    }

}
