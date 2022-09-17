package calebzhou.rdi.microservice.component;

/**
 * Created by calebzhou on 2022-09-09,21:55.
 */
import calebzhou.rdi.microservice.exception.RdiTokenFailureException;
import calebzhou.rdi.microservice.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Lehr
 * @create: 2020-02-03
 */
//JWT验证拦截器
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull Object object) {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        // 从请求头中取出 token  这里需要和前端约定好把jwt放到请求头一个叫token的地方
        String token = httpServletRequest.getHeader("rauth");
        //检查是否有passtoken注释，有则跳过认证
        if (handlerMethod.getMethodAnnotation(PassToken.class)!=null) {
            return true;
        }
        // 执行认证
        if (token == null) {
                //这里其实是登录失效,没token了
            throw new RdiTokenFailureException("token is null");
        }
            // 验证 token
        JwtUtils.verifyToken(token, JwtUtils.getPid(token));
        return true;

    }


}
