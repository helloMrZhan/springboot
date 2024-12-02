package com.example.quartz.job.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zjq
 * @Description: 通过反射执行bean中的方法
 **/
@Service
@Slf4j
public class SysServiceJob {
    /**
     * 参数由任务中的 invokeTarget 解析而来，参数个数以及类型需要与invokeTarget中的一致
     *
     * @param jobId
     * @param jobName
     */
    public void test(String jobId, String jobName,String businessCode,String businessId) {
        log.info("ID：{}，Name：{},businessCode:{},businessId:{}", jobId, jobName,businessCode,businessId);
        // 打印参数
        log.info("任务执行完毕！");
    }

}
