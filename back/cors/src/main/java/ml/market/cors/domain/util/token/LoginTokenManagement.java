package ml.market.cors.domain.util.token;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.member.entity.Blacklist_TokenDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.repository.member.Blacklist_TokenRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
@RequiredArgsConstructor
public class LoginTokenManagement extends JwtTokenManagement{
    public static final String EMAIL = "email";

    private final TokenInfoRepository tokenInfoRepository;

    private final Blacklist_TokenRepository blacklist_tokenRepository;

    public static final String ID = "id";

    public static final String IAT = "iat";

    public static final String ROLE = "role";

    public static final String HS256 = "HS256";

    public static final String JWT = "JWT";

    public static final long ACCESS_EXPIRETIME = 1000 * 60 * 30;

    public static final long REFRESH_EXPIRETIME = 1000 * 60 * 60;

    public static final String ALG_HEADER = "alg";

    public static final String TYP_HEADER = "typ";

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    public static final String REFRESH_TOKEN_EXPIRETIME = "refresh_token_expire_key";

    public static final String REFRESH_TOKEN_INDEX = "refresh_token_index";

    private final long AUTO_REFRESH_INTERVAL = 1000 * 60 * 5;

    public String create(Long refreshTokenIndex){
        Map<String, Object> claims = new HashMap<>();
        claims.put(REFRESH_TOKEN_INDEX, refreshTokenIndex);
        Date expireDate = createExpireDate(REFRESH_EXPIRETIME);
        return create(expireDate, setHeaders(), claims);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 20)
    protected void cleaner(){
        Date nowDate = new Date();
        Long expireDate;
        List<Blacklist_TokenDAO> blacklistTokenInfoList = blacklist_tokenRepository.findAll();
        for (Blacklist_TokenDAO blacklistTokenInfoDAO : blacklistTokenInfoList) {
            expireDate = blacklistTokenInfoDAO.getExpire_date();
            if(!nowDate.after(new Date(expireDate))){
                continue;
            }
            blacklist_tokenRepository.delete(new Blacklist_TokenDAO(blacklistTokenInfoDAO.getHash()));
        }
        List<TokenInfoDAO> tokenInfoExpireList = tokenInfoRepository.findAll();
        for (TokenInfoDAO tokenInfoDAO : tokenInfoExpireList) {
            expireDate = tokenInfoDAO.getExpire_date();
            if(!nowDate.after(new Date(expireDate))){
                continue;
            }
            tokenInfoRepository.delete(new TokenInfoDAO(tokenInfoDAO.getTokenindex()));
        }
    }

    public void delete(long refreshTokenIndex, long expireDate) {
        if(refreshTokenIndex == 0 || expireDate == 0){
            return;
        }
        tokenInfoRepository.deleteById(refreshTokenIndex);
    }
    public void delete(@NonNull String accessToken, long expireDate) {
        if(expireDate == 0){
            return;
        }
        Blacklist_TokenDAO blacklistTokenInfoDAO = new Blacklist_TokenDAO(accessToken, expireDate);
        blacklist_tokenRepository.save(blacklistTokenInfoDAO);
    }

    public boolean isBlackList(@NonNull String token) {
        return blacklist_tokenRepository.existsById(token);
    }

    private boolean isAvailRefresh(long refreshTokenIndex) {
        if(refreshTokenIndex == 0){
            return false;
        }

        TokenInfoDAO tokenInfoDAO = tokenInfoRepository.findByTokenindex(refreshTokenIndex);
        if(tokenInfoDAO == null){
            return false;
        }

        String refreshTokenHash = tokenInfoDAO.getHash();
        if(isBlackList(refreshTokenHash)){
            return false;
        }
        if(isVerify(refreshTokenHash) == false){
            return false;
        }
        return true;
    }

    public Map<String, Object> createTokenPair(@NonNull Map<String, Object> claims){
        Date expireDate = createExpireDate(LoginTokenManagement.ACCESS_EXPIRETIME);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> headers = setHeaders();
        StringBuilder tokenBuilder = new StringBuilder(create(expireDate, headers, claims));
        if(tokenBuilder.length() == 0){
            return null;
        }
        result.put(ACCESS_TOKEN, tokenBuilder.toString());

        tokenBuilder.delete(0, tokenBuilder.length());
        expireDate = createExpireDate(LoginTokenManagement.REFRESH_EXPIRETIME);
        tokenBuilder.append(create(expireDate, headers, claims));
        if(tokenBuilder.length() == 0){
            return null;
        }
        result.put(REFRESH_TOKEN, tokenBuilder.toString());
        result.put(REFRESH_TOKEN_EXPIRETIME, expireDate.getTime());
        return result;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        Cookie accessTokenCook = CookieManagement.search(eCookie.ACCESS_TOKEN.getName(), cookies);
        Cookie refreshTokenCook = CookieManagement.search(eCookie.REFRESH_TOKEN.getName(), cookies);
        StringBuilder tokenBuilder = new StringBuilder();
        Map<String, Object> claims = null;
        long expireTime;
        if(accessTokenCook != null){
            tokenBuilder.append(accessTokenCook.getValue());
            if(tokenBuilder.length() > 0){
                if(isVerify(tokenBuilder.toString())){
                    expireTime = System.currentTimeMillis() + ACCESS_EXPIRETIME;
                    blacklist_tokenRepository.save(new Blacklist_TokenDAO(tokenBuilder.toString(), expireTime));
                }
            }
            Cookie cook = CookieManagement.delete(eCookie.ACCESS_TOKEN.getName(), cookies);
            response.addCookie(cook);
        }


        if(refreshTokenCook != null) {
            claims = getClaims(refreshTokenCook.getValue());
            if(claims != null){
                long index = ((Number)claims.get(REFRESH_TOKEN_INDEX)).longValue();
                TokenInfoDAO tokenInfoDAO = tokenInfoRepository.findByTokenindex(index);
                if(tokenInfoDAO != null){
                    tokenInfoRepository.deleteById(index);
                    expireTime = System.currentTimeMillis() + REFRESH_EXPIRETIME;
                    blacklist_tokenRepository.save(new Blacklist_TokenDAO(tokenInfoDAO.getHash(), expireTime));
                }
            }
            Cookie cook = CookieManagement.delete(eCookie.REFRESH_TOKEN.getName(), cookies);
            response.addCookie(cook);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String refresh(long refreshTokenIndex) {
        if(!isAvailRefresh(refreshTokenIndex)){
            return null;
        }

        TokenInfoDAO tokenInfoDAO = tokenInfoRepository.findByTokenindex(refreshTokenIndex);
        Map<String, Object> claims = super.getClaims(tokenInfoDAO.getHash());
        if(claims == null) {
            return null;
        }

        long refreshTokenExpireTime = tokenInfoDAO.getExpire_date();
        Map<String, Object> headers = this.setHeaders();
        Date expireDate = null;
        Map<String, Object> result = new HashMap<>();
        long remainTime = refreshTokenExpireTime - System.currentTimeMillis();
        if(remainTime < 0 || remainTime < AUTO_REFRESH_INTERVAL){
            expireDate = createExpireDate(REFRESH_EXPIRETIME);
            String refreshToken = super.create(expireDate, headers, claims);
            if(refreshToken == null){
                return null;
            }
            tokenInfoDAO = tokenInfoRepository.save(new TokenInfoDAO(refreshTokenIndex, refreshToken, tokenInfoDAO.getMember_id(), expireDate.getTime()));
            result.put(REFRESH_TOKEN, tokenInfoDAO.getTokenindex());
        }

        expireDate = createExpireDate(ACCESS_EXPIRETIME);
        return create(expireDate, headers, claims);
    }

    @Override
    public Map<String, Object> setHeaders(){
        Map<String, Object> headers = new HashMap<>();
        headers.put(ALG_HEADER, HS256);
        headers.put(TYP_HEADER, JWT);
        return headers;
    }
}
