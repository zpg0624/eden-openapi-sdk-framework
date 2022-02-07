package com.eden.core.annotations;


import com.eden.core.enums.LimitType;

import java.lang.annotation.*;

/**
*   
 *   限流
* @author eden 【】
* @since 2019/11/25
*/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {

    /**
     * 资源的名字
     *
     * @return String
     */
    String name() default "";

    /**
     * 资源的key
     *
     */
    String key() default "";

    /**
     * Key的prefix
     *
     * @return String
     */
    String prefix() default "";

    /**
     * 给定的时间段
     * 单位秒
     *
     * @return int
     */
    int period();

    /**
     * 最多的访问限制次数
     *
     * @return int
     */
    int count();

    /**
     * 类型
     *
     * @return LimitType
     */
    LimitType limitType() default LimitType.CUSTOMER;
}