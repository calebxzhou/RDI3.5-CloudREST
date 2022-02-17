package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.constants.LogicCondition;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class IslandAspect {
    @Autowired
    IslandService service;

    @Around("@annotation(status) && args(pid,..)")
    public Object beforeIsland(ProceedingJoinPoint point,CheckIslandStatus status,String pid) throws Throwable {
        //TODO 创建了空岛以后还能创建一个
        boolean success = false;
        boolean isPlayerJoinIsland = service.isPlayerJoinIsland(pid);
        boolean isPlayerOwnIsland = service.isPlayerOwnIsland(pid);
        /*boolean flagJoin,flagOwn;
        if(status.needJoinIsland()){
            flagJoin= service.isPlayerJoinIsland(pid);
        }else{
            flagJoin= !service.isPlayerJoinIsland(pid);
        }
        log.info(flagJoin+"");
        if(status.needOwnIsland()){
            flagOwn=service.isPlayerOwnIsland(pid);
        }else {
            flagOwn=!service.isPlayerOwnIsland(pid);
        }*/
        if(status.condition() == LogicCondition.AND){
            success = (status.needJoinIsland() == isPlayerJoinIsland)
                    &&
                    (status.needOwnIsland() == isPlayerOwnIsland);
        }
        if(status.condition() == LogicCondition.OR){
            success = (status.needJoinIsland() == isPlayerJoinIsland)
                    ||
                    (status.needOwnIsland() == isPlayerOwnIsland);
        }

        if(!success){
            String errMsg="";
            if(status.needOwnIsland())
                errMsg+=("您需要拥有岛屿。");
            if(!status.needOwnIsland())
                errMsg+=("您不可以拥有岛屿。");
            if(status.condition()==LogicCondition.OR)
                errMsg+="或者，";
            if(status.condition()==LogicCondition.AND)
                errMsg+="并且，";
            if(status.needJoinIsland())
                errMsg+="您需要加入他人的岛屿。";
            if(!status.needJoinIsland())
                errMsg+="您不可以加入他人的岛屿。";

            throw new IslandException(errMsg);
        }
        Object proc = point.proceed();
        return proc;
    }
}
