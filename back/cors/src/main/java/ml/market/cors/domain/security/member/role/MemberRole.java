package ml.market.cors.domain.security.member.role;

import lombok.Getter;

@Getter
public enum MemberRole{
    ROLE_ADMIN("ROLE_ADMIN")
    ,ROLE_USER("ROLE_USER")
    ,ROLE_CEO("ROLE_CEO");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }
}
