package com.liuscraft.tradingplatform.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author helen
 * @since 2019/10/16
 */
public class JwtUtils {

    public enum JwtClaim {
        ID("id"),
        NICKNAME("nickname"),
        ROLE_ID("roleId");
        private final String value;
        JwtClaim(String value){
            this.value = value;
        }
    
        public String getValue() {
          return value;
        }
    }

    

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成token
     * @param id 用户ID
     * @param nickname 用户昵称
     * @return token
     */
    public static String getJwtToken(String id, String nickname, Integer roleId){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim(JwtClaim.ID.getValue(), id)
                .claim(JwtClaim.NICKNAME.getValue(), nickname)
                .claim(JwtClaim.ROLE_ID.getValue(), roleId)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 根据userName创建token
     * 这是一个不推荐的方法，建议使用 "getJwtToken(String id, String nickname)"
     * @param username 用户名
     * @return token
     */
    @Deprecated
    public static String getJwtToken(String username){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static <T> T getMemberClaimByJwtToken(String jwtToken,JwtClaim jwtClaim) {

        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = null;
        try {

           claimsJws= Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        }catch (Exception e) {
            return null;
        }
        Claims claims = claimsJws.getBody();
        //获取
        Object object = claims.get(jwtClaim.getValue());
        if(ObjectUtils.isEmpty(object)) return null;
        return (T)object;
    }

    /**
     * 根据token获取User的Claim
     * @param request
     * @param jwtClaim 使用JwtUtils.
     * @return
     */
    public static <T> T getMemberClaimByJwtToken(HttpServletRequest request,JwtClaim jwtClaim) {
        //查看请求头 放入哪里
        String jwtToken = request.getHeader("token");

        return getMemberClaimByJwtToken(jwtToken, jwtClaim);
    }

    public static Claims getMemberIdByJwtToken(String jwtToken) {
        //查看请求头 放入哪里
        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        //获取
        return claims;
    }

}
