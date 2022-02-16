package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.constants.LogicCondition;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IslandAspect {

    @Around("@annotation(status)")
    public Object beforeIsland(ProceedingJoinPoint point,CheckIslandStatus status) throws Throwable {
        boolean flag=false;
        if(status.condition() == LogicCondition.AND)
            flag = status.joinIsland() && status.ownIsland();
        else if(status.condition() == LogicCondition.OR)
            flag = status.joinIsland() || status.ownIsland();

        if(!flag)
            throw new IslandException("您不符合指令使用条件");
        Object proc = point.proceed();
        return proc;
    }
}
