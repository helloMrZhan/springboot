package com.zjq.redis.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import com.zjq.redis.exception.BadRequestException;
import org.springframework.util.CollectionUtils;

public class IpUtil {
    private static final String IP_REG = "^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$";
    private static final Pattern IP_PATTERN = Pattern.compile("^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$");

    public IpUtil() {
    }

    public static boolean isIpv4Address(String ipAddress) {
        Matcher matcher = IP_PATTERN.matcher(ipAddress);
        return matcher.matches();
    }

    public static String getRequestIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.length() > 15) {
                String[] ips = ip.split(",");

                for(int index = 0; index < ips.length; ++index) {
                    String strIp = ips[index];
                    if (!"unknown".equalsIgnoreCase(strIp)) {
                        ip = strIp;
                        break;
                    }
                }
            }
        } else {
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
        }

        return ip;
    }

    public static List<String> getLocalHostLANAddress() throws UnknownHostException {
        ArrayList ipList = new ArrayList();

        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            label78:
            while(true) {
                NetworkInterface ni;
                do {
                    do {
                        if (!interfaces.hasMoreElements()) {
                            Collections.sort(ipList);
                            break label78;
                        }

                        ni = (NetworkInterface)interfaces.nextElement();
                    } while(!ni.isUp());
                } while(ni.isLoopback());

                Enumeration ipAddrEnum = ni.getInetAddresses();

                while(ipAddrEnum.hasMoreElements()) {
                    InetAddress addr = (InetAddress)ipAddrEnum.nextElement();
                    if (!addr.isLoopbackAddress() && !addr.isLinkLocalAddress()) {
                        String ip = addr.getHostAddress();
                        if (ip.indexOf(":") == -1) {
                            ipList.add(ip);
                        }
                    }
                }
            }
        } catch (Exception var7) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + var7.getMessage());
            unknownHostException.initCause(var7);
            throw unknownHostException;
        }

        if (ipList.isEmpty()) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address");
            throw unknownHostException;
        } else {
            String hostAddress = "";

            try {
                InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
                if (jdkSuppliedAddress == null) {
                    throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
                }

                hostAddress = jdkSuppliedAddress.getHostAddress();
            } catch (UnknownHostException var6) {
                UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + var6.getMessage());
                unknownHostException.initCause(var6);
                throw unknownHostException;
            }

            if (null != hostAddress && hostAddress.trim().length() != 0 && !"127.0.0.1".equals(hostAddress) && !"localhost".equalsIgnoreCase(hostAddress) && !"unknown".equalsIgnoreCase(hostAddress)) {
                if (ipList.contains(hostAddress)) {
                    ArrayList<String> targetIpList = new ArrayList();
                    targetIpList.add(hostAddress);
                    return targetIpList;
                } else {
                    return ipList;
                }
            } else {
                return ipList;
            }
        }
    }

    public static void checkIpFormat(List<String> ipList) {
        if (!CollectionUtils.isEmpty(ipList)) {
            Iterator var1 = ipList.iterator();

            String ip;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                ip = (String)var1.next();
            } while(isIpv4Address(ip));

            throw new BadRequestException("IP地址错误，IP = " + ip);
        }
    }
}
