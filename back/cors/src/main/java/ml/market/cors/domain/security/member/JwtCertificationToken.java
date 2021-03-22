package ml.market.cors.domain.security.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtCertificationToken implements Authentication {
    private String email;

    private long id;

    private List roles;

    public JwtCertificationToken(long id, String email, List roles) {
        this.email = email;
        this.roles = roles;
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return this.id;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    /*
    싱글톤으로 객체가 반환되면서 멤버필드에 동기화 문제가 발생한다.
    리팩토링 하면서 고친다.
     */
    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return this.email;
    }
}
