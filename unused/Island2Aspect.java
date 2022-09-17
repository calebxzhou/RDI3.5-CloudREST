package calebzhou.rdi.microservice.annotation;

import calebzhou.rdi.microservice.annotation.PidTokenCheck;
import calebzhou.rdi.microservice.constant.ResultCode;
import calebzhou.rdi.microservice.exception.RdiTokenFailureException;
import calebzhou.rdi.microservice.utils.JwtUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by calebzhou on 2022-09-10,11:16.
 */

@Aspect
@Component
public class Island2Aspect {

    @Around("@annotation(status) && args(token,pid,..)")
    public void beforeIsland(ProceedingJoinPoint point, PidTokenCheck status, String token,String pid) {
        if(!JwtUtils.getPid(token).equals(pid))
            throw new RdiTokenFailureException(ResultCode.sourceNotOwnTarget.getMessage());
    }
}
