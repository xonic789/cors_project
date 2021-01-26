package ml.market.cors.domain.security.member.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberProviderManager extends ProviderManager {
    public MemberProviderManager(List<AuthenticationProvider> mProviders){
        super(mProviders);
    }

    private Authentication getAuthentication(AuthenticationProvider provider, Authentication authentication) throws AuthenticationException{
        Authentication token;
        try {
            token = provider.authenticate(authentication);
        }catch(UsernameNotFoundException except){
            throw new RuntimeException(except);
        }catch (BadCredentialsException except){
            throw new RuntimeException(except);
        }catch(AuthenticationException except){
            throw new RuntimeException(except);
        }catch (Exception exception){
            throw new RuntimeException();
        }

        return token;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<AuthenticationProvider> providers = super.getProviders();
        Class<? extends Authentication> classCompare = authentication.getClass();
        Authentication token = authentication;
        boolean bSearch = false;
        for (AuthenticationProvider mProvider : providers) {
            if(mProvider.supports(classCompare)){
                bSearch = true;
                token = getAuthentication(mProvider, authentication);
                break;
            }
        }

        if(bSearch == false){
            throw new ProviderNotFoundException("Provider Not Found");
        }
        return token;
    }
}
