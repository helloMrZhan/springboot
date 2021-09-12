package com.zjq.aop.config;

import com.zjq.aop.filter.RequestDetailFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>请求详情信息Config</p>
 *
 * @author zjq
 * @date 2021/7/27
 */
@Configuration
public class RequestDetailConfig {

    /**
     * RequestDetailFilter配置
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<RequestDetailFilter> requestDetailFilter() {
        FilterRegistrationBean<RequestDetailFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new RequestDetailFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setAsyncSupported(true);
        return filterRegistrationBean;
    }
}
