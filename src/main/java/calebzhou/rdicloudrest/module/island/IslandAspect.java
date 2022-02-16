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
    IslandRepo repo;

    @Around("@annotation(status) && args(pid,..)")
    public Object beforeIsland(ProceedingJoinPoint point,CheckIslandStatus status,String pid) throws Throwable {
        boolean joinRequired = status.joinIsland();
        boolean ownRequired = status.ownIsland();
        boolean join = repo.isPlayerJoinIsland(pid);
        boolean own = repo.isPlayerOwnIsland(pid);
        log.info("{} {} {} {}",joinRequired,ownRequired,join,own);
        boolean success = false;
        boolean flagJoin,flagOwn;
        if(joinRequired){
            flagJoin= join;
        }else{
            flagJoin= !join;
        }

        if(ownRequired){
            flagOwn=own;
        }else {
            flagOwn=!own;
        }
        if(status.condition() == LogicCondition.AND){
            success = flagJoin && flagOwn;
        }
        if(status.condition() == LogicCondition.OR){
            success = flagJoin || flagOwn;
        }

        if(!success){
            String errMsg="";
            if(joinRequired)
                errMsg+="您需要加入一个岛屿，才能使用本指令。";
            if(!joinRequired)
                errMsg+="要使用本指令，您不能加入任何岛屿。";
            if(ownRequired)
                errMsg+=("您需要拥有一个岛屿，才能使用本指令。");
            if(!ownRequired)
                errMsg+=("要使用本指令，您不能拥有任何岛屿。");
            throw new IslandException(errMsg);
        }
        Object proc = point.proceed();
        return proc;
    }
}
