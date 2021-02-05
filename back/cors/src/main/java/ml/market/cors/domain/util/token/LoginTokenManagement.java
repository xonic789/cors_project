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
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
@RequiredArgsConstructor
public abstract class LoginTokenManagement extends JwtTokenManagement{
    private final TokenInfoRepository tokenInfoRepository;

    private final Blacklist_TokenRepository blacklist_tokenRepository;
    public static final String ID_CLAIM = "id";
    public static final String IAT_CLAIM = "iat";
    public static final String MEMBER_ROLE_CLAIM = "member_role";
    public static final String HS256 = "HS256";
    public static final String JWT = "JWT";
    public static final long ACCESS_EXPIRETIME = 1000 * 60 * 1;
    public static final long REFRESH_EXPIRETIME = 1000 * 60 * 2;
    public static final String ALG_HEADER = "alg";
    public static final String TYP_HEADER = "typ";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    private final long AUTO_REFRESH_INTERVAL = 1000 * 60 * 5;

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

    public boolean isBlackList(String token) {
        if(blacklist_tokenRepository.findByHash(token) != null){
            return false;
        }
        return true;
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String refresh(long refreshTokenIndex) {
        if(!isAvailRefresh(refreshTokenIndex)){
            return null;
        }

        TokenInfoDAO tokenInfoDAO = tokenInfoRepository.findByTokenindex(refreshTokenIndex);
        Map claimsValue = super.getClaims(tokenInfoDAO.getHash());
        if(claimsValue == null) {
            return null;
        }

        long prevExpireTime = tokenInfoDAO.getExpire_date();
        long remainTime = prevExpireTime - System.currentTimeMillis();
        Map<String, Object> headers = this.setHeaders();
        Map<String, Object> claims = this.setClaims(claimsValue);
        Date expireDate = null;
        if(remainTime < 0 || remainTime < AUTO_REFRESH_INTERVAL){
            expireDate = createExpireDate(REFRESH_EXPIRETIME);
            String refreshToken = super.create(expireDate, headers, claims);
            if(refreshToken == null){
                return null;
            }
            tokenInfoRepository.save(new TokenInfoDAO(refreshTokenIndex, refreshToken, tokenInfoDAO.getMember_id(), expireDate.getTime()));
        }
        expireDate = createExpireDate(ACCESS_EXPIRETIME);
        return super.create(expireDate, headers, claims);
    }

    @Override
    public Map<String, Object> setClaims(@NonNull Map<String, Object> claimValue){
        Map<String, Object> claims = new HashMap<>();
        claims.put(MEMBER_ROLE_CLAIM, claimValue.get(MEMBER_ROLE_CLAIM));
        claims.put(ID_CLAIM, claimValue.get(ID_CLAIM));
        claims.put(IAT_CLAIM, System.currentTimeMillis());
        return claims;
    }

    @Override
    public Map<String, Object> setHeaders(){
        Map<String, Object> headers = new HashMap<>();
        headers.put(ALG_HEADER, HS256);
        headers.put(TYP_HEADER, JWT);
        return headers;
    }
}
