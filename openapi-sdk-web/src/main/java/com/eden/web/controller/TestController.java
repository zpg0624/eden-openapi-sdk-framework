package com.eden.web.controller;

import com.eden.core.annotations.Access;
import com.eden.core.annotations.Limit;
import com.eden.web.common.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author eden 【】
 * @since 2019-10-28 15:15
 */

@RestController
@Access
@RequestMapping("/v1")
@Slf4j
public class TestController extends BaseController {


    @Limit(key = "test", period = 10, count = 1, name = "resource", prefix = "limit")
    @GetMapping("/test")
    public int testLimiter() {

        return 1;
    }


}
