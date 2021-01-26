package ml.market.cors.domain.security.member;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.security.member.role.CustomGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JwtCertificationToken implements Authentication {
    private String email;

    private List roles;

    public JwtCertificationToken(String email, List roles) {
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List grants = new LinkedList<GrantedAuthority>();
        grants.add(new CustomGrantAuthority());
        return grants;
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
