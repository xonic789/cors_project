package ml.market.cors.domain.util.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieManagement {
    public static Cookie add(String name, int maxAge, String path, String val){
        if(name == null){
            return null;
        }
        if(name.equals("")){
            return null;
        }
        if(val == null){
            return null;
        }
        if(val.equals("")){
            return null;
        }
        Cookie cookie = null;
        try{
            cookie = new Cookie(name, val);
            cookie.setMaxAge(maxAge);
            cookie.setPath(path);
        }catch (Exception e){
            return null;
        }
        return cookie;
    }

    public static Cookie search(String name, Cookie[] cookies){
        if(cookies == null){
            return null;
        }
        if(cookies.length == 0){
            return null;
        }
        if(name == null){
            return null;
        }
        if(name.equals("")){
            return null;
        }
        Cookie cookie = null;
        for (Cookie item : cookies) {
            if(item.getName().equals(name)){
                cookie = item;
            }
        }
        return cookie;
    }
    private static boolean exists(String name, Cookie[] cookies){
        boolean bResult = false;
        if(cookies == null){
            return bResult;
        }
        if(cookies.length == 0){
            return bResult;
        }
        if(name == null){
            return bResult;
        }
        if(name.equals("")){
            return bResult;
        }
        for (Cookie item : cookies) {
            if(item.getName().equals(name)){
                bResult = true;
                break;
            }
        }
        return bResult;
    }

    public static Cookie delete(String name, Cookie[] cookies){
        boolean bResult = exists(name, cookies);
        if(!bResult){
            return null;
        }
        Cookie cook = new Cookie(name, null);
        cook.setMaxAge(0);
        cook.setPath("/");
        return cook;
    }
}
