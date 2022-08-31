package calebzhou.rdicloudrest.module.qqbind;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//满足什么条件才执行QQ操作
public @interface CheckQBindStatus {
    boolean needQBind();
}
