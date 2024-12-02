package com.example.quartz.controller;

import com.example.quartz.model.param.BaseResponse;
import com.example.quartz.model.param.JobParam;
import com.example.quartz.model.vo.JobVo;
import com.example.quartz.service.JobService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 任务管理
 * @author zjq
 */
@Api(tags = "任务管理")
@RestController
@RequestMapping("/task")
@Slf4j
public class JobController {

    @Resource
    private JobService jobService;


    @ApiOperation(value = "新增任务", notes = "新增任务")
    @ApiOperationSupport(order = 5)
    @PostMapping("/insert")
    public String addJob(@RequestBody JobParam job) {
        jobService.addJob(job);
        return "操作成功";
    }

    /**
     * 查询任务列表
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "任务列表", notes = "任务列表", response = JobVo.class, responseContainer = "List")
    public Object jobList() {
        return BaseResponse.success(jobService.jobList());
    }




    @ApiOperation(value = "更新任务", notes = "更新任务")
    @ApiOperationSupport(order = 10)
    @PutMapping("/update")
    public String update(@RequestBody JobParam job) {
        jobService.updateJob(job);
        return "操作成功";
    }

    @ApiOperation(value = "删除任务", notes = "删除任务")
    @ApiOperationSupport(order = 10)
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        jobService.deleteJob(id);
        return "操作成功";
    }

    @ApiOperation(value = "禁用任务", notes = "禁用任务")
    @ApiOperationSupport(order = 10)
    @DeleteMapping("/forbid/{id}")
    public String forbidJob(@PathVariable String id) {
        jobService.forbidJob(id);
        return "操作成功";
    }

    @ApiOperation(value = "暂停任务", notes = "暂停任务")
    @ApiOperationSupport(order = 10)
    @PostMapping("/pause/{id}")
    public String pause(@PathVariable String id) {
        jobService.pauseJob(id);
        return "操作成功";
    }


    @ApiOperation(value = "恢复任务", notes = "恢复任务")
    @ApiOperationSupport(order = 10)
    @PostMapping("/resume/{id}")
    public String resume(@PathVariable String id) {
        jobService.resumeJob(id);
        return "操作成功";
    }

    @ApiOperation(value = "执行一次任务", notes = "执行一次任务")
    @ApiOperationSupport(order = 20)
    @PostMapping("/once/{id}")
    public String runOnceJob(@PathVariable String id) {
        jobService.runOnceJob(id);
        return "操作成功";
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @GetMapping("info/{id}")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParam(name = "id", value = "任务id", paramType = "path", required = true, dataType = "String")
    public Object getJobById(@PathVariable("id") String id) {
        // 查询数据库
        return jobService.getJobById(id);
    }
}