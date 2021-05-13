package ml.market.cors.domain.security.member.filter;

import ml.market.cors.domain.security.member.handler.MemberLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;


public class MemberLogoutFilter extends LogoutFilter {
    public MemberLogoutFilter(String logoutSuccessUrl, MemberLogoutHandler handlers) {
        super(logoutSuccessUrl, handlers);
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setFilterProcessesUrl(filterProcessesUrl);
    }
}
