package calebzhou.rdi.microservice.utils;

/**
 * Created by calebzhou on 2022-09-09,21:05.
 */


import calebzhou.rdi.microservice.App;
import calebzhou.rdi.microservice.exception.RdiTokenFailureException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Lehr
 * @create: 2020-02-04
 */
public class JwtUtils {
    public static final String SIGN_SUFFIX = "_RDIPlayer";

    /**
     签发对象：这个用户的id
     签发时间：现在
     有效时间：30分钟
     载荷内容：暂时设计为：这个人的名字，这个人的昵称
     加密密钥：这个人的id加上一串字符串
     */
    public static String createToken(String pid,String pname) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,30);
        Date expiresDate = nowTime.getTime();

        return JWT.create()
                .withAudience(pid)   //签发对象
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expiresDate)  //有效时间
                .withClaim("pname", pname) //玩家昵称
                .sign(Algorithm.HMAC256(pid+ SIGN_SUFFIX));   //加密
    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的id
     * @param token
     */
    public static DecodedJWT verifyToken(String token, String pid) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(pid+ SIGN_SUFFIX)).build();
            jwt = verifier.verify(token);
        } catch (JWTVerificationException j) {
            //效验失败
            throw new RdiTokenFailureException(j.getMessage()+j.getCause());
        }
        return jwt;
    }

    /**
     * 获取签发对象(Pid)
     */
    public static String getPid(String token)  {
        String pid = null;
        try {
            pid = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException decodeException){
            if(App.DEBUG)
                decodeException.printStackTrace();
            throw new RdiTokenFailureException("invalid token");
        }
        catch (Exception j) {
            j.printStackTrace();
            //这里是token解析失败
            throw new RdiTokenFailureException(j.getMessage()+j.getCause());
        }
        return pid;
    }
//获取玩家名
    public static String getPname(String token){
        return getClaimByName(token, "pname").asString();
    }

    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}

