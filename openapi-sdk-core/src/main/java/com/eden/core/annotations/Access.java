package com.eden.core.annotations;

import java.lang.annotation.*;

/**
*
 *权限拦截注解
 * 
* @author eden
* @since 2019/10/28
*/
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {

    boolean required() default true;

}
