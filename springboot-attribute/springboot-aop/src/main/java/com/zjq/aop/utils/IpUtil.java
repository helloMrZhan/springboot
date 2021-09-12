package com.zjq.aop.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * <p>IP工具类</p>
 *
 * @author zjq
 * @date 2020/10/22
 */
@Slf4j
public class IpUtil {

    private static final String UNKNOWN = "unknown";
    private static final String IPV6_LOCAL = "0:0:0:0:0:0:0:1";

    private IpUtil() {
        throw new AssertionError();
    }

    /**
     * 获取请求用户的IP地址
     *
     * @return
     */
    public static String getRequestIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        return getRequestIp(request);
    }

    /**
     * 获取请求用户的IP地址
     *
     * @param request
     * @return
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (IPV6_LOCAL.equals(ip)) {
            ip = getLocalhostIp();
        }

        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String getLocalhostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        return null;
    }

    /**
     * 获取ip地理位置
     *
     * @param ip IP
     * @return String
     */
    public static String getLocation(String ip) {
        InputStream inputStream = IpUtil.class.getClassLoader().getResourceAsStream("ip2region.db");
        try {
            return getLocation(ip, readStrem(Objects.requireNonNull(inputStream)));
        } catch (Exception e) {
            log.error("IpUtil getLocation", e);
        }
        return null;
    }

    private static byte[] readStrem(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    /**
     * 获取ip地理位置
     *
     * @param ip       IP
     * @param dbBinStr ip库
     * @return String
     */
    public static String getLocation(String ip, byte[] dbBinStr) {
        DataBlock dataBlock;
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbBinStr);

            Method method = searcher.getClass().getMethod("memorySearch", String.class);

            if (!Util.isIpAddress(ip)) {
                return "";
            } else {
                dataBlock = (DataBlock) method.invoke(searcher, ip);
                try {
                    searcher.close();
                } catch (Exception e) {
                    // 目前该代码存在bug，会空指针，直接不予处理异常
                }

                return dataBlock.getRegion()
                        .replaceAll("0\\|", "")
                        .replaceAll("\\|0", "");
            }

        } catch (Exception e) {
            log.error("IpUtil getLocation", e);
        }
        return "";
    }
}
