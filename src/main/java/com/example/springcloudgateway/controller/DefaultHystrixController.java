package com.example.springcloudgateway.controller;

import com.example.springcloudgateway.dto.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @author: hqk
 * @date: 2020/5/11 16:38
 * @Description: 网关熔断降级返回
 */
@RestController
public class DefaultHystrixController {


    @RequestMapping("/defaultfallback")
    public Object defaultfallback(){
        System.out.println("降级操作...");

        return CommonResult.error(401,"网关服务熔断");
    }

    /*@RequestMapping("/defaultfallback")
    public Map<String,String> defaultfallback(){
        System.out.println("降级操作...");
        Map<String,String> map = new HashMap<>();
        map.put("resultCode","false");
        map.put("resultMessage","服务异常");
        map.put("resultObj","这里测试网关服务熔断");
        return map;
    }*/
}
