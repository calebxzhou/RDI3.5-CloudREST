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
                errMsg+=("您需要拥有一个岛屿，才能使用本指令。");
            if(!status.needOwnIsland())
                errMsg+=("要使用本指令，您不能拥有任何岛屿。");
            if(status.needJoinIsland())
                errMsg+="您需要加入一个岛屿，才能使用本指令。";
            if(!status.needJoinIsland())
                errMsg+="要使用本指令，您不能加入任何岛屿。";

            throw new IslandException(errMsg);
        }
        Object proc = point.proceed();
        return proc;
    }
}
