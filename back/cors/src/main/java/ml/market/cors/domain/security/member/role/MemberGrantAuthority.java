package ml.market.cors.domain.security.member.role;

import org.springframework.security.core.GrantedAuthority;

public class MemberGrantAuthority implements GrantedAuthority {
    private MemberRole memberRole;

    public MemberGrantAuthority(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
}
