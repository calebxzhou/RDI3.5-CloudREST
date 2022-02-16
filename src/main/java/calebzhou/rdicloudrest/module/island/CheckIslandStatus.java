package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.constants.LogicCondition;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//满足什么条件才执行岛操作
public @interface CheckIslandStatus {
    boolean ownIsland();
    LogicCondition condition();
    boolean joinIsland();
}
