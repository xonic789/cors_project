package ml.market.cors.domain.security.member;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JwtCertificationToken implements Authentication {
    private String email;

    private MemberRole role;

    public JwtCertificationToken(String email, MemberRole role) {
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List grantList = new LinkedList<GrantedAuthority>();
        grantList.add(new MemberGrantAuthority(role));
        return grantList;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return this.email;
    }
}
