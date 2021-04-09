package ml.market.cors.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enums.eSocialType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name="members")
@Getter
@NoArgsConstructor
public class MemberDAO implements UserDetails{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long member_id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Wish_listDAO> wish_listDAO = new ArrayList<Wish_listDAO>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<ArticleDAO> articleDAO = new ArrayList<>();

    @Column(name="profilekey")
    private String profileKey;

    @Column(name="profileimg")
    private String profile_img;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name = "intro")
    private String intro;

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
    private eSocialType eSocialType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List grants = new LinkedList();
        grants.add(new MemberGrantAuthority(this.role));
        return grants;
    }

    public MemberDAO(String profileKey, String profile_img, String email, MemberRole role, String intro, String password, String address, double latitude, double longitude, String nickname, ml.market.cors.domain.security.oauth.enums.eSocialType eSocialType) {
        this.profileKey = profileKey;
        this.profile_img = profile_img;
        this.email = email;
        this.role = role;
        this.intro = intro;
        this.password = password;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nickname = nickname;
        this.eSocialType = eSocialType;
    }

    public MemberDAO(long member_id, String profileKey, String profile_img, String email, MemberRole role, String password, String address, double latitude, double longitude, String nickname, eSocialType eSocialType, String intro) {
        this.member_id = member_id;
        this.intro = intro;
        this.profileKey = profileKey;
        this.profile_img = profile_img;
        this.email = email;
        this.role = role;
        this.password = password;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nickname = nickname;
        this.eSocialType = eSocialType;
    }

    public MemberDAO(long memberId) {
        this.member_id = memberId;
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
