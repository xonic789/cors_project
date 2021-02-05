package ml.market.cors.domain.util.token;

import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.*;

@Slf4j
public abstract class JwtTokenManagement {
    public final String SECRETKEY = "key";

    public final String CHARSET = "UTF-8";

    private Key createSigningKey() {
        String secretKey = SECRETKEY;
        Key key = null;
        try {
            byte[] secretKeyBytes = secretKey.getBytes(CHARSET);
            String jcaName = SignatureAlgorithm.HS256.getJcaName();
            key = new SecretKeySpec(secretKeyBytes, jcaName);
        } catch (Exception except) {
            log.debug("키 생성 실패");
        }

        return key;
    }

    public Date createExpireDate(long expireTime) {
        if(expireTime == 0){
            return null;
        }
        Date date = new Date();
        long time = date.getTime() + expireTime;
        date.setTime(time);
        return date;
    }


    public String create(@NonNull Date expireDate,@NonNull Map<String, Object> header,@NonNull Map<String, Object> claims) {
        Key key = createSigningKey();
        if (key == null) {
            return null;
        }

        JwtBuilder builder = Jwts.builder()
                    .setHeader(header)
                    .setClaims(claims)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS256, key);
        return builder.compact();
    }

    public boolean isVerify(String token) {
        boolean bResult = false;
        try {
            Jwts.parser()
                    .setSigningKey(SECRETKEY.getBytes(CHARSET))
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException except) {
            bResult = true;
        } catch (Exception except) {
            bResult = false;
        }
        return bResult;
    }

    public Map<String, Object> getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRETKEY.getBytes(CHARSET))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException except) {
            return null;
        } catch (Exception except) {
            return null;
        }
        return claims;
    }

    public abstract Map<String, Object> setClaims(@NonNull Map<String, Object> claimValue);

    public abstract Map<String, Object> setHeaders();

}

