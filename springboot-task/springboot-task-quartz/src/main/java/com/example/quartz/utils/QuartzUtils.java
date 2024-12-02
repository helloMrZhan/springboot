package com.example.quartz.utils;

import com.example.quartz.model.entity.JobEntity;
import org.quartz.CronExpression;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * cron表达式工具类
 *
 * @author zjq
 */
public class QuartzUtils {

    /**
     * 解析invokeTarget内容
     *
     * @param jobEntity
     * @return
     */
    public static Map<String, Object> generatorJobDataMap(JobEntity jobEntity) {

        String jobId = jobEntity.getId();
        String jobName = jobEntity.getName();
        String cron = jobEntity.getCronExpression();
        /*
         * invokeTarget：格式bean.method(params)
         * String字符串类型，包含'、boolean布尔类型，等于true或者false
         * long长整形，包含L、double浮点类型，包含D、其他类型归类为整形
         */
        String invokeTarget = jobEntity.getInvokeTarget();

        String beanName = invokeTarget.substring(0, invokeTarget.indexOf("."));

        String methodName = invokeTarget.substring(invokeTarget.indexOf(".") + 1, invokeTarget.indexOf("("));


        // 获取参数
        List<Object[]> methodParams = new ArrayList<>();
        // 默认第一个参数 加上 id 参数, 第二个参数未name
        methodParams.add(new Object[]{jobId, String.class});
        methodParams.add(new Object[]{jobName, String.class});

        // 解析参数
        getMethodParams(methodParams, invokeTarget);
        Map<String, Object> map = new HashMap<>();
        map.put("beanName", beanName);
        map.put("methodName", methodName);
        map.put("jobId", jobId);
        map.put("jobName", jobName);
        map.put("cron", cron);
        map.put("methodParams", methodParams);

        return map;
    }

    /**
     * 获取参数类型
     *
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classs = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classs[index] = (Class<?>) os[1];
            index++;
        }
        return classs;
    }

    /**
     * 获取参数值
     *
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] objects = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            objects[index] = os[0];
            index++;
        }
        return objects;
    }

    /**
     * 解析表达式中的参数
     *
     * @param invokeTarget
     * @return
     */
    private static void getMethodParams(List<Object[]> methodParams, String invokeTarget) {
        String paramStr = invokeTarget.substring(invokeTarget.indexOf("(") + 1, invokeTarget.indexOf(")"));

        String[] params = paramStr.split(",");

        for (String param : params) {
            String str = param.trim();
            // String字符串类型，包含`'`；
            if (str.contains("'")) {
                methodParams.add(new Object[]{StringUtils.replace(str, "'", ""), String.class});
            }
            // boolean布尔类型，等于true或者false
            else if ("true".equals(str) || "false".equals(str)) {
                methodParams.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // long长整形，包含L
            else if (str.toUpperCase().contains("L")) {
                methodParams.add(new Object[]{Long.valueOf(StringUtils.replace(str.toUpperCase(), "L", "")), Long.class});
            }
            // double浮点类型，包含D
            else if (str.toUpperCase().contains("D")) {
                methodParams.add(new Object[]{Double.valueOf(StringUtils.replace(str.toUpperCase(), "D", "")), Double.class});
            }
            // 其他类型归类为整形
            else {
                methodParams.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
    }
}

