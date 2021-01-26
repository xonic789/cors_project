package ml.market.cors.domain.security.member.role;

import lombok.Getter;

@Getter
public enum MemberRole{
    ADMIN("ADMIN")
    ,USER("USER")
    ,CEO("CEO");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }
}
