package com.example.springcloudgateway.filter;

import com.alibaba.fastjson.JSON;
import com.example.springcloudgateway.config.AppConstants;
import com.example.springcloudgateway.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.EncodingException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Arrays;

/**
 * @version V1.0
 * @author: hqk
 * @date: 2020/5/12 15:35
 * @Description:  转发之前拦截 白名单过滤 鉴权
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 白名单 排除无需验证的 token
    private static final String[]   whiteList = {"/auth/login", "/user/register"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String url =serverHttpRequest.getURI().getPath();

        log.info("url:{}", url);
        //System.out.println("请求地址："+url);

        //无需过滤的URL
        if (Arrays.asList(whiteList).contains(url)){

            return chain.filter(exchange);
        }

        String token =serverHttpRequest.getHeaders().getFirst(AppConstants.TOKEN);

        log.info("请求token:{}", token);
        //System.out.println("请求token："+token);

        /*if(StringUtils.isEmpty(token)){

            return setResponse(exchange, "token 不存在");
        }*/

        /*String redisToken=stringRedisTemplate.opsForValue().get(AppConstants.REDIS_KEY_TOKEN);

        if (StringUtils.isBlank(redisToken)) {

            return setResponse(exchange, "token 失效");
        }*/

        log.info("鉴权完毕");

        // 设置userId到request里，后续根据userId，获取用户信息
        /*ServerHttpRequest mutableReq = exchange.getRequest().mutate()
                .header("ticket", "1111").build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
        return chain.filter(mutableExchange);*/

        return chain.filter(exchange);
    }

    /**
     * 设置 拦截返回信息
     * @param exchange
     * @param msg
     * @return
     */
    private Mono<Void> setResponse(ServerWebExchange exchange, String msg) {

        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = null;
        try
        {
            log.info("token已失效");
            response = JSON.toJSONString(CommonResult.error(msg,"")).getBytes(AppConstants.UTF8);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }


    @Override
    public int getOrder() {
        return -200;
    }

}
