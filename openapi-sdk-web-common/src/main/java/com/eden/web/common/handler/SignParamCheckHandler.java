package com.eden.web.common.handler;

import com.eden.core.enums.ResultMsgEnum;
import com.eden.core.param.BaseSignParam;
import com.eden.core.resp.ResultWrap;
import com.eden.core.utils.SignValidateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 公共的服务
 *
 * @author eden 【】
 * @since 2019-10-29 14:16
 */
@Component
@Slf4j
public class SignParamCheckHandler {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 检查时间戳
     *
     * @param signParam
     * @param expiredminutetime
     * @author eden 【】
     * @since 2019/10/29
     */
    @SneakyThrows
    public BaseSignParam checkTimeStamp(BaseSignParam signParam, String expiredminutetime) {
        boolean validateTimeStamp = SignValidateUtils.validateTimeStamp(signParam.getTimestamp(), Integer.valueOf(expiredminutetime));
        Optional.of(!validateTimeStamp)
                .ifPresent($ -> ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_TIME_EXPIRED));
        return signParam;
    }

    /**
     * 检查签名
     *
     * @param signParam
     * @author eden 【】
     * @since 2019/10/29
     */
    @SneakyThrows
    public void checkSign(BaseSignParam signParam) {
        final Map params = objectMapper.convertValue(signParam, Map.class);
        String signStr = SignValidateUtils.sortMapByKey(params);
        log.debug("resolveCheckSign params:{}, signStr:{}", params, signStr);
        Optional.ofNullable(signStr)
                .filter(__ -> !Objects.equals(new String(DigestUtils.md5Digest(signStr.getBytes())).toUpperCase(), signParam.getSign()))
                .ifPresent($ -> ResultWrap.getInstance().buildFailedThenThrow(ResultMsgEnum.RESULT_SIGN_ERROR));
    }

}
