package com.eden.web.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.param.BaseSignParam;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.SignValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 公共的服务
 *
 * @author eden 【】
 * @since 2019-10-29 14:16
 */
@Component
@Slf4j
public class SignHandlerAdvice {

    /**
     * 检查时间戳
     *
     * @param signParam
     * @param expiredminutetime
     * @author eden 【】
     * @since 2019/10/29
     */
    public void checkTimeStamp(BaseSignParam signParam, String expiredminutetime) {
        boolean validateTimeStamp = SignValidateUtils.validateTimeStamp(signParam.getTimestamp(),
                Integer.valueOf(expiredminutetime));
        if (!validateTimeStamp) {
            ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_TIME_EXPIRED);
        }
    }

    /**
     * 检查签名
     *
     * @param signParam
     * @author eden 【】
     * @since 2019/10/29
     */
    public void checkSign(BaseSignParam signParam) {
        Map params = JSONObject.parseObject(JSONObject.toJSONString(signParam), Map.class);
        String signStr = SignValidateUtils.sortMapByKey(params);
        log.info("resolveCheckSign params:{}, signStr:{}", params, signStr);
        if (!Objects.equals(new String(DigestUtils.md5Digest(signStr.getBytes())).toUpperCase(), signParam.getSign())) {
            ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_SIGN_ERROR);
        }
    }

}
