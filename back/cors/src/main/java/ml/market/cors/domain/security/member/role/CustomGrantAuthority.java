package ml.market.cors.domain.security.member.role;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
}
