package com.eden.core.enums;

/**
*  
 *  限流类型
* @author eden
* @since 2019/11/25
*/
public enum LimitType {
                       /**
                        * 自定义key
                        */
                       CUSTOMER,
                       /**
                        * 根据请求者IP
                        */
                       IP;
}