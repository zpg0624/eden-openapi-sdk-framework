package com.eden.core.ex;

import com.eden.core.resp.ResultWrap;
import lombok.Getter;
import lombok.ToString;

/**
 * 校验参数异常
 *
 * @author eden
 * @since 2019-10-28 11:23
 */
@Getter
@ToString
public class ValidateParamsException extends RuntimeException {

    private ResultWrap<?> failedResult;

    public ValidateParamsException(ResultWrap<?> failedResult) {
        this.failedResult = failedResult;
    }
}
