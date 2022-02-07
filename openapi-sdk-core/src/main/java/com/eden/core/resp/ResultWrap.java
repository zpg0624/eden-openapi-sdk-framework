package com.eden.core.resp;


import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.ex.ValidateParamsException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 结果集封装
 *
 * @author eden
 * @since 2019-10-28 10:47
 */
@Getter
@Setter
@ToString
public class ResultWrap<R> implements Serializable {

    private Boolean success = Boolean.FALSE;

    private R result;

    private Integer code;

    private String msg;

    public ResultWrap() {
    }

    public static <R> ResultWrap getInstance() {
        ResultWrap<R> resultWrap = new ResultWrap<>();
        return resultWrap;
    }

    public <R> ResultWrap<R> buildFailed(ResultMsgEnum msgEnum) {
        this.code = msgEnum.getCode();
        this.msg = msgEnum.getMsg();
        return (ResultWrap<R>) this;
    }

    public <R> ResultWrap<R> buildFailedThenThrow(ResultMsgEnum msgEnum) {
        this.code = msgEnum.getCode();
        this.msg = msgEnum.getMsg();
        throw new ValidateParamsException(this);
    }

    public void buildFailedMsg(String msg) {
        this.msg = msg;
        throw new ValidateParamsException(this);
    }

    public <R> ResultWrap<R> buildFailedThenMsg(String msg) {
        this.code = ResultMsgEnum.RESULT_AUTH_PARAM_ERROR.getCode();
        this.msg = msg;
        return (ResultWrap<R>) this;
    }


    public ResultWrap<R> buildSuccess(R result, ResultMsgEnum msgEnum) {
        buildFailed(msgEnum);
        this.success = Boolean.TRUE;
        this.result = result;
        return this;
    }
}
