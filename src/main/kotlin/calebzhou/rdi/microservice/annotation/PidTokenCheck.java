package calebzhou.rdi.microservice.annotation;

import java.lang.annotation.*;

/**
 * Created by calebzhou on 2022-09-10,11:21.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PidTokenCheck {
}
