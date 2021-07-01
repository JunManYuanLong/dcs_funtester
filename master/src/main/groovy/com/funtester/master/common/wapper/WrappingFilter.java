package com.funtester.master.common.wapper;


import com.funtester.base.bean.Result;
import com.funtester.base.constaint.ThreadBase;
import com.funtester.config.Constant;
import com.funtester.frame.Output;
import com.funtester.slave.common.basedata.DcsConstant;
import com.funtester.utils.Time;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;


@Component
@WebFilter(urlPatterns = "/*", filterName = "wrappingFilter")
public class WrappingFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String ipAddress = getIpAddress(req);
        HttpServletResponse resp = (HttpServletResponse) response;
        ResponseWrapper responseWrapper = new ResponseWrapper(resp);
        RequestWrapper requestWrapper = new RequestWrapper(req);
        String url = requestWrapper.getRequestURI();
        String queryArgs = requestWrapper.getQueryString();
//        queryArgs = queryArgs == null ? DecodeEncode.unicodeToString(requestWrapper.getBody()) : queryArgs;

        String headerKey = req.getHeader(DcsConstant.HEADER_KEY);
//        if(StringUtils.isBlank(requestId)){
//            resp.getWriter().write(Result.fail(CommonCode.REQUESTID_ERROR).toString());
//            resp.flushBuffer();
//            return;
//        }
//        MDC.put("id", requestId);
        String method = requestWrapper.getMethod();
        if (method.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
            if (StringUtils.isEmpty(headerKey) || !headerKey.equalsIgnoreCase(DcsConstant.HEADER_VALUE)){
                response.getOutputStream().write(Result.fail("验证失败!").toString().getBytes());
                return;
            }
            if (url.startsWith("/run/")) {
                if (ThreadBase.needAbort()) {
                    response.getOutputStream().write(Result.fail("正在运行其他用例").toString().getBytes());
                    return;
                }
            }
        }
        if (url.equalsIgnoreCase("/test/ip")) {
            response.getOutputStream().write(Result.success(ipAddress).toString().getBytes());
            return;
        }
        long start = Time.getTimeStamp();
        chain.doFilter(requestWrapper == null ? request : requestWrapper, responseWrapper);
        long end = Time.getTimeStamp();
        byte[] bytes = responseWrapper.getContent();
        String respContent = new String(bytes, Constant.UTF_8);
        if (!(url.startsWith("/ws") || url.contains("swagger"))) {
            logger.info("请求:{},耗时:{} ms,参数:{},响应:{},来源:{}", url, end - start, queryArgs, respContent, ipAddress);
            try {
                Output.showStr(queryArgs);
                Output.showStr(respContent);
            } catch (Exception e) {

            }
        }
        response.getOutputStream().write(respContent.getBytes(Constant.UTF_8));
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
