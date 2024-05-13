package com.zjq.redis.aspect;

import com.zjq.redis.constant.RateLimiter;
import com.zjq.redis.enums.LimitType;
import com.zjq.redis.exception.BadRequestException;
import com.zjq.redis.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 限流切面处理
 * @author 共饮一杯无
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    private final RedisTemplate redisTemplate;
    private final RedisScript<Long> limitScript;

    public RateLimiterAspect(RedisTemplate redisTemplate, RedisScript<Long> limitScript) {
        this.redisTemplate = redisTemplate;
        this.limitScript = limitScript;
    }

    @Around("@annotation(com.zjq.redis.constant.RateLimiter)")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        RateLimiter rateLimiter = methodSignature.getMethod().getAnnotation(RateLimiter.class);
        
		//判断该方法是否存在限流的注解
        if (null != rateLimiter){
        	//获得注解中的配置信息
            int count = rateLimiter.count();
            int time = rateLimiter.time();
            String key = rateLimiter.key();
			
			//调用getCombineKey()获得存入redis中的key   key -> 注解中配置的key前缀-ip地址-方法路径-方法名
            String combineKey = getCombineKey(rateLimiter, methodSignature);
            log.info("combineKey->,{}",combineKey);
            //将combineKey放入集合
            List<Object> keys = Collections.singletonList(combineKey);
            log.info("keys->",keys);
            try {
            	//执行lua脚本获得返回值
                Long number = (Long) redisTemplate.execute(limitScript, keys, count, time);
                //如果返回null或者返回次数大于配置次数，则限制访问
                if (number==null || number.intValue() > count) {
                    throw new BadRequestException("访问过于频繁，请稍候再试");
                }
                log.info("限制请求'{}',当前请求'{}',缓存key'{}'", count, number.intValue(), combineKey);
            } catch (BadRequestException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("服务器限流异常，请稍候再试");
            }
        }

        return joinPoint.proceed();
    }

	/**
     * Gets combine key.
     *
     * @param rateLimiter the rate limiter
     * @param signature   the signature
     * @return the combine key
     */
    public String getCombineKey(RateLimiter rateLimiter, MethodSignature signature) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            stringBuffer.append(IpUtil.getRequestIpAddress(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest())).append("-");
        }
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }


}
