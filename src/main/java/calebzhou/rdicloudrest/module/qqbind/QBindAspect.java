package calebzhou.rdicloudrest.module.qqbind;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class QBindAspect {
    @Autowired
    QBindRepo repo;

    @Around("@annotation(status) && args(qq,..)")
    public Object beforeIsland(ProceedingJoinPoint point, CheckQBindStatus status, String qq) throws Throwable {
        boolean success = false;
        if(status.needQBind()) {
            if(repo.existsById(qq))
                success = true;
        }else{
            if(!repo.existsById(qq))
                success= true;
        }

        if(!success)
            throw new QBindException("您"+(success?"未":"已")+"绑定QQ号与游戏角色！");
        Object proc = point.proceed();
        return proc;
    }
}
