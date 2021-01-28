package ml.market.cors.domain.security.member.role;

import lombok.Getter;

@Getter
public enum MemberRole{
    ADMIN("ROLE_ADMIN")
    ,USER("ROLE_USER")
    ,CEO("ROLE_CEO");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }
}
