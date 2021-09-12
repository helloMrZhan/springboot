package com.zjq.aop.filter;

import com.zjq.aop.bean.RequestDetail;
import com.zjq.aop.utils.IpUtil;
import com.zjq.aop.utils.RequestDetailThreadLocal;
import com.zjq.aop.utils.RequestUtil;
import com.zjq.aop.wrapper.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>请求详情信息Filter</p>
 *
 * @author zjq
 * @date 2021/7/22
 */
@Slf4j
public class RequestDetailFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("RequestDetailFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置请求详情信息
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String contentType = httpServletRequest.getContentType();
        String ip = IpUtil.getRequestIp(httpServletRequest);
        String path = httpServletRequest.getRequestURI();
        String param = "";

        // 文件上传跳过
        //RequestDetailFilter里面如果post 协议是application/x-www-form-urlencoded的 会导致后面的接口获取不到参数 如果你们有这样的请求 可以暂时在这里暂时排除掉
        if (contentType == null || !contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)
                &&!contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
            param = RequestUtil.getRequestParams(requestWrapper);
            httpServletRequest = requestWrapper;
        }

        RequestDetail requestDetail = new RequestDetail()
                .setIp(ip)
                .setPath(path)
                .setParams(param);
        // 设置请求详情信息
        RequestDetailThreadLocal.setRequestDetail(requestDetail);
        chain.doFilter(httpServletRequest, response);
        // 释放
        RequestDetailThreadLocal.remove();
    }

    @Override
    public void destroy() {
        log.info("RequestDetailFilter destroy");
    }
}
