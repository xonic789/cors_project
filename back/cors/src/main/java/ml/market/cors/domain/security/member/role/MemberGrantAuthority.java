package ml.market.cors.domain.security.member.role;

import org.springframework.security.core.GrantedAuthority;

/*
List로 넣어서 json으로 흘러들어가고 다시 응답해서 올떄
List안에 객체는 사라지고 객체 안에 멤버필드만 존재한다.
{
    MEMBER_ROLE [{
        authority : ROLE_ADMIN
    }]
}
 */
public class MemberGrantAuthority implements GrantedAuthority {
    private MemberRole memberRole;

    public MemberGrantAuthority(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    @Override
    public String getAuthority() {
        return memberRole.getRole();
    }
}
