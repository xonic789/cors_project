package ml.market.cors.domain.util.token;

import io.jsonwebtoken.*;
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

@Component
@RequiredArgsConstructor
public class JwtTokenManagement {
    private final MemberRepository memberRepository;

    private final TokenInfoRepository tokenInfoRepository;

    private final Blacklist_TokenRepository blacklist_tokenRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 20)
    protected void cleaner(){
        Date nowDate = new Date();
        Long expireDate;
        List<Blacklist_TokenDAO> blacklistTokenInfoList = blacklist_tokenRepository.findAll();
        for (Blacklist_TokenDAO blacklistTokenInfoDAO : blacklistTokenInfoList) {
            expireDate = blacklistTokenInfoDAO.getExpire_date();
            if(nowDate.after(new Date(expireDate))){
                blacklist_tokenRepository.delete(new Blacklist_TokenDAO(blacklistTokenInfoDAO.getHash()));
            }
        }
        List<TokenInfoDAO> tokenInfoExpireList = tokenInfoRepository.findAll();
        for (TokenInfoDAO tokenInfoDAO : tokenInfoExpireList) {
            expireDate = tokenInfoDAO.getExpire_date();
            if(nowDate.after(new Date(expireDate))){
                tokenInfoRepository.delete(new TokenInfoDAO(tokenInfoDAO.getTokenindex()));
            }
        }
    }

    public boolean delete(String token, String tokenType) {
        Date date = new Date();
        long expireTime = 0;
        long token_Index = 0;
        TokenInfoDAO tokenInfoDAO;
        if(tokenType == TokenAttribute.REFRESH_TOKEN){
            token_Index = Long.parseLong(token);
            tokenInfoDAO = tokenInfoRepository.findByTokenindex(token_Index);
            if(tokenInfoDAO == null){
                return true;
            }
            token = tokenInfoDAO.getHash();
            expireTime = date.getTime() + TokenAttribute.REFRESH_EXPIRETIME;
        }else if(tokenType == TokenAttribute.ACCESS_TOKEN){
            expireTime = date.getTime() + TokenAttribute.ACCESS_EXPIRETIME;
        }

        Blacklist_TokenDAO blacklistTokenInfoDAO = new Blacklist_TokenDAO(token, expireTime);
        blacklist_tokenRepository.save(blacklistTokenInfoDAO);
        if (tokenType.equals(TokenAttribute.REFRESH_TOKEN)) {
            try{
                tokenInfoRepository.deleteById(token_Index);
            }catch (EmptyResultDataAccessException e){
                return true;
            }catch(Exception e){
                return false;
            }
        }
        return true;
    }


    public boolean isBlackList(String token) {
        Optional<Blacklist_TokenDAO> optional = blacklist_tokenRepository.findById(token);
        try {
            Blacklist_TokenDAO blacklistTokenInfoDAO = optional.get();
        } catch (NoSuchElementException e) {
            return false;
        }catch (Exception e){
            return false;
        }

        return true;
    }

    private Key createSigningKey() {
        String secretKey = TokenAttribute.SECRETKEY;
        byte[] secretKeyBytes;
        try {
            secretKeyBytes = secretKey.getBytes(TokenAttribute.CHARSET);
        } catch (UnsupportedEncodingException except) {
            return null;
        }
        String jcaName = SignatureAlgorithm.HS256.getJcaName();
        return new SecretKeySpec(secretKeyBytes, jcaName);
    }

    public Date createExpireDate(long expireTime) {
        Date date = new Date();
        long time = date.getTime() + expireTime;
        date.setTime(time);
        return date;
    }


    public String create(Date expireDate, Map<String, Object> header, Map<String, Object> claim) {
        Key key = createSigningKey();
        if (key == null) {
            return null;
        }
        JwtBuilder builder;
        if(expireDate == null){
            builder = Jwts.builder()
                    .setHeader(header)
                    .setClaims(claim)
                    .signWith(SignatureAlgorithm.HS256, key);
        }
        else{
            builder = Jwts.builder()
                    .setHeader(header)
                    .setClaims(claim)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS256, key);
        }
        String token = builder.compact();
        return token;
    }

    public boolean isVerify(String token) {
        String secretKey = TokenAttribute.SECRETKEY;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(TokenAttribute.CHARSET))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException except) {
            return true;
        } catch (Exception except) {
            return false;
        }
        return true;
    }

    public Map getClaims(String token) {
        String secretKey = TokenAttribute.SECRETKEY;
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(TokenAttribute.CHARSET))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException except) {
            return null;
        } catch (Exception except) {
            return null;
        }
        return claims;
    }

    public TokenInfoDAO findTokenIndex(long tokenIndex){
        TokenInfoDAO tokenInfoDAO = tokenInfoRepository.findByTokenindex(tokenIndex);
        if(tokenInfoDAO == null){
            return null;
        }
        return tokenInfoDAO;
    }

    public boolean isAvailRefresh(Cookie[] cookies) {
        Cookie cook = CookieManagement.search(TokenAttribute.REFRESH_TOKEN, cookies);
        if(cook == null){
            return false;
        }

        long token_Index = Long.parseLong(cook.getValue());
        TokenInfoDAO tokenInfoDAO = findTokenIndex(token_Index);
        if(tokenInfoDAO == null){
            return false;
        }

        if(isBlackList(tokenInfoDAO.getHash())){
            return false;
        }
        if(isVerify(tokenInfoDAO.getHash()) == false){
            return false;
        }
        return true;
    }

    private boolean insertBlackList(String token, String type){
        Date date = new Date();
        long expireTime = 0;
        if(type == TokenAttribute.REFRESH_TOKEN){
            expireTime = date.getTime() + TokenAttribute.REFRESH_EXPIRETIME;
        }else if(type == TokenAttribute.ACCESS_TOKEN){
            expireTime = date.getTime() + TokenAttribute.ACCESS_EXPIRETIME;
        }
        Blacklist_TokenDAO insertDAO = new Blacklist_TokenDAO(token, expireTime);
        Blacklist_TokenDAO resultDAO = null;
        try {
            resultDAO = blacklist_tokenRepository.save(insertDAO);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean deleteByIdTokenInfo(long token_index){
        try {
            tokenInfoRepository.deleteById(token_index);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public void deleteAllTokenDB(String accessToken, long token_index ,String refreshToken) {
        boolean result = true;
        if(accessToken != null && !accessToken.equals("")){
            if(insertBlackList(accessToken, TokenAttribute.ACCESS_TOKEN) == false){
                result = false;
            }
        }
        if(refreshToken != null && !refreshToken.equals("")){
            if(insertBlackList(refreshToken, TokenAttribute.REFRESH_TOKEN) == false){
                result = false;
            }
            if(deleteByIdTokenInfo(token_index) == false){
                result = false;
            }
        }
        if(result == false){
            //log
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String refresh(Cookie[] cookies, HttpServletResponse res) {
        Cookie cook = CookieManagement.search(TokenAttribute.ACCESS_TOKEN, cookies);
        String accessToken = null;
        if(cook != null){
            accessToken = cook.getValue();
        }

        cook = CookieManagement.search(TokenAttribute.REFRESH_TOKEN, cookies);
        long refreshTokenIndex = 0;
        String refreshToken = null;
        TokenInfoDAO tokenInfoDAO = null;
        if(cook != null){
            refreshTokenIndex = Long.parseLong(cook.getValue());
            tokenInfoDAO = findTokenIndex(refreshTokenIndex);
            if(tokenInfoDAO == null){
                deleteAllTokenDB(accessToken, refreshTokenIndex, null);
                CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
                CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
                return null;
            }
            refreshToken = tokenInfoDAO.getHash();
        }

        if(isAvailRefresh(cookies) == false){
            deleteAllTokenDB(accessToken, refreshTokenIndex , refreshToken);
            CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
            CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
            return null;
        }

        Date expireDate = null;
        Map claims = getClaims(refreshToken);
        Map headers = null;
        Map paramClaims = null;
        eCookie cookAttr;
        if(claims == null) {
            deleteAllTokenDB(accessToken, refreshTokenIndex, refreshToken);
            CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
            CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
            return null;
        }

        headers = setHeader();
        long member_id = tokenInfoDAO.getMember_id();
        Optional<MemberDAO> optional = memberRepository.findById(member_id);
        MemberDAO memberDao = optional.get();
        MemberRole memberRole = memberDao.getRole();
        List memberRoles = new LinkedList();
        memberRoles.add(new MemberGrantAuthority(memberRole));
        paramClaims = setClaim(member_id, memberRoles);
        expireDate = createExpireDate(TokenAttribute.REFRESH_EXPIRETIME);
        refreshToken = create(expireDate, headers, paramClaims);
        if (refreshToken == null) {
            try {
                deleteAllTokenDB(accessToken, refreshTokenIndex, tokenInfoDAO.getHash());
            } catch (Exception e) {
                //log
            }
            CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
            CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
            return null;
        }

        boolean result = updateTokenInfo(refreshTokenIndex, refreshToken, tokenInfoDAO.getHash(), member_id, expireDate.getTime());
        if (result == false) {
            deleteAllTokenDB(accessToken, refreshTokenIndex, tokenInfoDAO.getHash());
            CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
            CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
            return null;
        }
        claims = getClaims(refreshToken);
        if(claims == null){
            return null;
        }

        headers = setHeader();
        expireDate = createExpireDate(TokenAttribute.ACCESS_EXPIRETIME);
        memberRoles = (List) claims.get(TokenAttribute.MEMBER_ROLE);
        paramClaims = setClaim(((Number) claims.get(TokenAttribute.ID_CLAIM)).longValue(), memberRoles);
        accessToken = create(expireDate, headers, paramClaims);
        if(accessToken.toString() == null){
            //log
            try{
                deleteAllTokenDB(accessToken, refreshTokenIndex ,refreshToken);
            } catch (Exception except){
                //log
            }
            CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
            CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
            return null;
        }
        cookAttr = eCookie.ACCESS_TOKEN;
        cook = CookieManagement.add(TokenAttribute.ACCESS_TOKEN, cookAttr.getMaxAge(), cookAttr.getPath(), accessToken);
        res.addCookie(cook);
        return accessToken;
    }

    private boolean updateTokenInfo(long tokenIndex, String dst, String src, long member_id, long expireTime) {
        if(insertBlackList(src, TokenAttribute.REFRESH_TOKEN) == false){
            return false;
        }
        tokenInfoRepository.save(new TokenInfoDAO(tokenIndex, dst, member_id, expireTime));
        return true;
    }

    private Map setClaim(long id, List memberRoles){
        Map claims = new HashMap();
        claims.put(TokenAttribute.MEMBER_ROLE, memberRoles);
        claims.put(TokenAttribute.ID_CLAIM, id);
        claims.put(TokenAttribute.IAT_CLAIM, System.currentTimeMillis());
        return claims;
    }

    private Map setHeader(){
        Map headers = new HashMap();
        headers.put(TokenAttribute.ALG_HEADER, TokenAttribute.HS256);
        headers.put(TokenAttribute.TYP_HEADER, TokenAttribute.JWT);
        return headers;
    }

}

