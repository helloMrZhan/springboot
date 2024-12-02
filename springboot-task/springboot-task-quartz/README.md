```
```
 分布式定时任务
# 1、前言
1、动态任务的实现主要通过`SpringBoot+Quartz`进行实现，并且配合自定义封装类通过反射机制实现完整的动态任务流程，支持、分布式、自定义corn；
2、通过数据库记录具体的任务信息；
 

# 2、数据库设计
数据库有两张自定义表与是一张Quartz表构成，如下：

**自定义数据表(2张)：**
- `sys_job：自定义任务信息`
- `sys_job_log：自定义任务日志表`

**Quartz集群必需表(11张)：**
- `QRTZ_JOB_DETAILS：任务详细信息表`
- `QRTZ_TRIGGERS：触发器详细信息表`
- `QRTZ_SIMPLE_TRIGGERS：简单触发器的信息表`
- `QRTZ_CRON_TRIGGERS：Cron类型的触发器表`
- `QRTZ_BLOB_TRIGGERS：Blob类型的触发器表`
- `QRTZ_CALENDARS：日历信息表`
- `QRTZ_PAUSED_TRIGGER_GRPS：暂停的触发器表`
- `QRTZ_FIRED_TRIGGERS：已触发的触发器表`
- `QRTZ_SCHEDULER_STATE：调度器状态表`
- `QRTZ_LOCKS：存储的悲观锁信息表`
- `QRTZ_SIMPROP_TRIGGERS：同步机制的行锁表`

# 3、业务代码实现
只进行核心业务类的编写，包括`JobMapper.xml`、`JobMapper`、`JobService`、`JobController`；
## 3.1 JobMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.quartz.mapper.JobMapper">

    <resultMap type="com.example.quartz.model.entity.JobEntity" id="BaseResultMap">
        <id property="id" column="id" jdbcType="VARCHAR"/>
        <result property="name" column="name" jdbcType="VARCHAR"/>
        <result property="cycle" column="cycle" jdbcType="VARCHAR"/>
        <result property="invokeTarget" column="invoke_target" jdbcType="VARCHAR"/>
        <result property="cronExpression" column="cron_expression" jdbcType="VARCHAR"/>
        <result property="policy" column="policy" jdbcType="INTEGER"/>
        <result property="situation" column="situation" jdbcType="INTEGER"/>
        <result property="version" column="version" jdbcType="INTEGER"/>
        <result property="lastRunTime" column="last_run_time" jdbcType="TIMESTAMP"/>
        <result property="nextRunTime" column="next_run_time" jdbcType="TIMESTAMP"/>
        <result property="status" column="status" jdbcType="INTEGER"/>
        <result property="delFlag" column="del_flag" jdbcType="INTEGER"/>
        <result property="remark" column="remark" jdbcType="VARCHAR"/>
        <result property="createBy" column="create_by" jdbcType="VARCHAR"/>
        <result property="createTime" column="create_time" jdbcType="TIMESTAMP"/>
        <result property="updateBy" column="update_by" jdbcType="VARCHAR"/>
        <result property="updateTime" column="update_time" jdbcType="TIMESTAMP"/>
    </resultMap>

    <sql id="Base_Column_List">
        id,name,cycle,invoke_target,cron_expression,policy,situation,version,last_run_time,next_run_time,status,del_flag,create_by,create_time,update_by,update_time,remark
    </sql>

    <select id="jobList" parameterType="java.lang.Integer" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from sys_job
        <where>
            `del_flag` = 0
            <if test="status != null">
                and status = #{status}
            </if>
        </where>
    </select>

    <select id="selectById" parameterType="string" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from sys_job where id =#{id}
    </select>


    <insert id="insert" parameterType="com.example.quartz.model.entity.JobEntity">
        insert into sys_job(id, name, cycle, invoke_target, cron_expression, situation, version, policy, last_run_time,
                             next_run_time, status, del_flag,
                             create_by, create_time, update_by, update_time, remark)
        values (#{id}, #{name}, #{cycle}, #{invokeTarget}, #{cronExpression}, #{situation}, #{version}, #{policy},
                #{lastRunTime}, #{nextRunTime}, #{status}, #{delFlag},
                #{createBy}, #{createTime}, #{updateBy}, #{updateTime}, #{remark})
    </insert>

    <update id="updateVersion">
        update sys_job
        <set>
            version = #{version} + 1,
            <if test="job.lastRunTime != null">
                last_run_time = #{job.lastRunTime},
            </if>
            <if test="job.nextRunTime != null">
                next_run_time = #{job.nextRunTime},
            </if>
            <if test="job.situation != null">
                situation = #{job.situation},
            </if>
        </set>
        where id = #{job.id} and version = #{version}
    </update>

    <update id="update" parameterType="com.example.quartz.model.entity.JobEntity">
        update sys_job
        <set>
            <if test="name != null">
                name = #{name},
            </if>
            <if test="situation != null">
                situation = #{situation},
            </if>
            <if test="status != null">
                status = #{status},
            </if>
            <if test="invokeTarget != null">
                invoke_target = #{invokeTarget},
            </if>
            <if test="cronExpression != null">
                cron_expression = #{cronExpression},
            </if>
            <if test="situation != null">
                situation = #{situation},
            </if>
            <if test="cycle != null">
                cycle = #{cycle},
            </if>
            <if test="policy != null">
                policy = #{policy},
            </if>
            <if test="version != null">
                version = #{version} + 1,
            </if>
            <if test="lastRunTime != null">
                last_run_time = #{lastRunTime},
            </if>
            <if test="nextRunTime != null">
                next_run_time = #{nextRunTime},
            </if>
        </set>
        where id = #{id,jdbcType=VARCHAR}
    </update>

    <delete id="delete" parameterType="string">
        delete
        from sys_job
        where id = #{id}
    </delete>

    <insert id="insertTaskLog" parameterType="com.example.quartz.model.entity.JobLog">
        insert into sys_job_log
            value (#{id}, #{taskId}, #{time}, #{status}, #{exceptionInfo}, #{createTime})
    </insert>

    <update id="updateSituationStatus">
        update sys_job
        set situation = #{situation}
        where id IN
        <foreach collection="ids" item="id" separator="," open="(" close=")">
            #{id}
        </foreach>
    </update>
</mapper>
```
## 3.2 JobMapper
```java
@Mapper
public interface JobMapper {
    /**
     * 任务列表
     *
     * @return
     */
    List<JobEntity> jobList(@Param("status") Integer status);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    JobEntity selectById(String id);

    /**
     * 新增
     *
     * @param job
     * @return
     */
    int insert(JobEntity job);

    /**
     * 更新
     *
     * @param job
     * @return
     */
    int update(JobEntity job);

    /**
     * 更新版本号
     *
     * @param job
     * @param version
     * @return
     */
    int updateVersion(@Param("job") JobEntity job, @Param("version") Integer version);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 插入任务日志
     *
     * @param log
     */
    void insertTaskLog(JobLog log);

    /**
     * 更新运行状态
     *
     * @param ids
     * @param situation
     */
    void updateSituationStatus(@Param("ids") List<String> ids, @Param("situation") Integer situation);
}
```
## 3.3 JobService
```java
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private JobManager jobManager;

    /***
     *任务列表查询
     * @return o
     */
    @Override
    public Object jobList() {
        return jobMapper.jobList(null);
    }

    /**
     * 新增列表
     *
     * @param param 新增参数
     */
    @Override
    public void addJob(JobParam param) {
        // 解析表达式，此表达式由后端根据规则进行解析，可以直接由前端进行传递
        String cron = CronUtils.dateConvertToCron(param);

        //查询执行周期
        Date nextTime = CronUtils.nextCurrentTime(cron);
        //生成实体
        JobEntity job = new JobEntity();
        BeanUtils.copyProperties(param, job);
        job.setId(UUIDUtils.getUuId());
        job.setDelFlag(0);
        job.setCronExpression(cron);
        job.setNextRunTime(nextTime);
        // 执行策略(1手动-暂停状态(2)，2-自动-执行中状态(1))
        Integer situation = param.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.PAUSE : JobConstant.EXECUTE;
        job.setSituation(situation);
        //设置版本好为0
        job.setVersion(0);

        job.setCreateBy("");
        job.setCreateTime(new Date());
        job.setUpdateBy("");
        job.setUpdateTime(new Date());

        // 执行任务
        String runType = param.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.USER_RUN : JobConstant.SYSTEM_RUN;
        jobManager.startJob(job, runType);

        //插入数据库
        jobMapper.insert(job);
    }

    /**
     * 修改任务
     *
     * @param param 修改参数
     */
    @Override
    public void updateJob(JobParam param) {
        JobEntity job = jobMapper.selectById(param.getId());
        if (job == null) {
            throw new RuntimeException("更新失败,任务不存在");
        }

        //解析表达式
        String cron = CronUtils.dateConvertToCron(param);
        //查询执行周期
        Date nextTime = CronUtils.nextCurrentTime(cron);
        //生成实体
        BeanUtils.copyProperties(param, job);
        job.setCronExpression(cron);
        job.setNextRunTime(nextTime);
        // 执行策略(1手动-暂停状态(2)，2-自动-执行中状态(1))
        int situation = param.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.PAUSE : JobConstant.EXECUTE;
        job.setSituation(situation);
        job.setUpdateBy("");
        job.setUpdateTime(new Date());


        // 执行策略(1手动，2-自动)：根据手动还是自动决定如何处理 执行任务
        if (job.getPolicy().equals(JobConstant.MANUAL)) {
            // 手动模式则从quartz中删除
            log.info("======== 手动模式,删除执行中任务 ========");
            jobManager.deleteJob(job.getId());
            return;
        }

        // 更新任务
        jobManager.updateJob(job);

        // 更新数据库
        jobMapper.update(job);
    }


    /**
     * 执行任务
     *
     * @param id 任务id
     */
    @Override
    public void runOnceJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("执行失败,任务不存在");
        }

        // 执行
        jobManager.runOnceJob(id);
    }

    /**
     * 暂停任务
     *
     * @param id 任务id
     */
    @Override
    public void pauseJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("暂停任务失败,任务不存在");
        }

        job.setSituation(JobConstant.PAUSE);

        // 暂停
        jobManager.pauseJob(id);

        // 更新数据库
        jobMapper.update(job);
    }

    /**
     * 恢复任务
     *
     * @param id 任务id
     */
    @Override
    public void resumeJob(String id) {
        // 更新数据库
        JobEntity job = jobMapper.selectById(id);
        if (job == null) {
            throw new RuntimeException("暂停任务失败,任务不存在");
        }
        job.setStatus(JobConstant.ENABLE);
        job.setSituation(JobConstant.EXECUTE);
        // 启动任务
        jobManager.resumeJob(job);

        // 更新数据库
        jobMapper.update(job);
    }

    /**
     * 删除任务
     *
     * @param id 任务id
     */
    @Override
    public void deleteJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("删除任务失败,任务不存在");
        }
        // 删除执行的任务
        jobManager.deleteJob(id);

        //数据库删除
        jobMapper.delete(id);
    }

    /**
     * 禁用任务
     *
     * @param id 任务id
     */
    @Override
    public void forbidJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("禁用失败,任务不存在");
        }

        // 删除执行的任务
        jobManager.deleteJob(id);

        //禁用
        job.setStatus(JobConstant.DISABLE);
        job.setSituation(JobConstant.PAUSE);
        jobMapper.update(job);
    }

    /**
     * 查询详情
     *
     * @param id 任务id
     */
    @Override
    public JobVo getJobById(String id) {
        JobEntity job = jobMapper.selectById(id);
        if (job == null) {
            throw new RuntimeException("任务不存在！");
        }
        JobVo jobVo = new JobVo();
        BeanUtils.copyProperties(job, jobVo);
        List<String> nextExecution = (List<String>) CronUtils.getNextExecution(job.getCronExpression(), 8, true);
        jobVo.setNext(nextExecution);
        return jobVo;
    }

    /**
     * 任务日志
     */
    @Override
    @Async
    public void insertTaskLog(JobLog log) {
        jobMapper.insertTaskLog(log);
    }
}
```
## 3.4 JobController
```java

@Api(tags = "任务管理")
@RestController
@RequestMapping("/task")
@Slf4j
public class JobController {

    @Resource
    private JobService jobService;

    /**
     * 查询任务列表
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "任务列表", notes = "任务列表", response = JobVo.class, responseContainer = "List")
    public Object jobList() {
        return jobService.jobList();
    }


    @ApiOperation(value = "新增任务", notes = "新增任务")
    @ApiOperationSupport(order = 5)
    @PostMapping("/insert")
    public String addJob(@RequestBody JobParam job) {
        jobService.addJob(job);
        return "操作成功";
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
```

# 4、任务核心代码
## 4.1 SysServiceJob
```java
@Service
@Slf4j
public class SysServiceJob {
    /**
     * 参数由任务中的 invokeTarget 解析而来，参数个数以及类型需要与invokeTarget中的一致
     *
     * @param jobId
     * @param jobName
     * @param str
     * @param l
     * @param d
     * @param b
     */
    public void test(String jobId, String jobName, String str, Long l, Double d, Boolean b) {
        log.info("ID：{}，Name：{}", jobId, jobName);
        // 打印参数
        log.info("参数：str={},l={},d={},b={}", str, l, d, b);
    }
}
```
## 4.2 BaseQuartzJobBean
```java

// 持久化
@PersistJobDataAfterExecution
// 禁止并发执行
@DisallowConcurrentExecution
@Component
@Slf4j
public class BaseQuartzJobBean extends QuartzJobBean {

    @Resource
    private JobManager jobManager = MyApplicationContext.getBean(JobManager.class);

    @Resource
    private JobMapper jobMapper = MyApplicationContext.getBean(JobMapper.class);

    /**
     * 是否进行校验时间差，第一次执行任务时，不校验时间差
     */
    private boolean checkTime = false;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String jobId = jobDetail.getKey().getName();
        log.info("执行任务，任务ID：" + jobId);
        if (true) {
            return;
        }
        //获取当前执行时间戳
        long currentTime = System.currentTimeMillis();

        // 获取参数
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Map<String, Object> paramMap = (Map<String, Object>) jobDataMap.get("paramMap");


        String cron = paramMap.get("cron").toString();
        Date nextCurrentTime = CronUtils.nextCurrentTime(cron);
        log.info("任务：{}，当前：{}，下次：{}", jobId, new Date(), nextCurrentTime);
        // 时间差
        long diffTime = Math.abs(currentTime - nextCurrentTime.getTime());

        //执行时，允许200ms误差，为了防止服务器时间钟摆出现误差
        if (diffTime > 200 && checkTime) {
            String msg = "任务执行异常，时间节点错误！";
            //开发中出现了错误情况，可以采用发生邮箱提醒给开发者
            log.error(msg);

            // 记录日志
            JobLogRecord.recordTaskLog(jobId, currentTime, msg);
            return;
        }

        JobEntity job = jobMapper.selectById(jobId);
        try {
            // 模拟耗时
            TimeUnit.SECONDS.sleep(1);

            // 通过反射执行方法
            String invokeTarget = paramMap.get("beanName").toString();
            log.info("beanName：" + invokeTarget);

            String methodStr = paramMap.get("methodName").toString();
            log.info("methodName：" + methodStr);

            // 执行具体类的方法
            List<Object[]> methodParams = (List<Object[]>) paramMap.get("methodParams");
            Object beanName = MyApplicationContext.getBean(invokeTarget);
            Method method = beanName.getClass().getMethod(methodStr, QuartzUtils.getMethodParamsType(methodParams));

            // 执行任务
            method.invoke(beanName, QuartzUtils.getMethodParamsValue(methodParams));
            // 记录日志
            JobLogRecord.recordTaskLog(jobId, currentTime, null);
        } catch (Exception e) {
            String message = "没有找到可执行方法：" + e.getMessage();
            log.error(message);
            // 需要关闭任务
            jobManager.deleteJob(jobId);
            job.setSituation(JobConstant.PAUSE);

            // 记录日志
            JobLogRecord.recordTaskLog(jobId, currentTime, message);
        } finally {
            // 当任务执行完成后，后续开启时间校验
            checkTime = true;

            // 更新任务
            job.setId(jobId);
            job.setLastRunTime(new Date());
            job.setNextRunTime(nextCurrentTime);
            jobMapper.updateVersion(job, job.getVersion());
        }
    }
}
```
## 4.3 JobManager
```java

@Component
@Slf4j
public class JobManager {

    @Resource
    private Scheduler scheduler;

    @Resource
    private JobMapper jobMapper;

    /**
     * 初始化任务
     */
    public void initTask() throws SchedulerException {
        // 读取自动执行任务列表状态为：正常(启用)
        List<JobEntity> jobs = jobMapper.jobList(0);
        // 过滤状态为执行中的任务
        List<JobEntity> startList = jobs.stream().filter(t -> t.getPolicy().equals(JobConstant.AUTO) || t.getSituation().equals(JobConstant.EXECUTE)).collect(Collectors.toList());
        List<String> startIdList = startList.stream().map(JobEntity::getId).collect(Collectors.toList());
        // 获取quartz中的任务,并且对比,如果存在于quartz中则删除(避免自启)
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<String> existsIdList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            String id = jobKey.getName();
            if (!startIdList.contains(id)) {
                deleteJob(id);
            } else {
                existsIdList.add(id);
            }
        }

        // 执行任务
        for (JobEntity entity : startList) {
            // 比对cron是否一致,如果不一致则删除，如果一致则不做处理
            TriggerKey triggerKey = TriggerKey.triggerKey(entity.getId());
            if (existsIdList.contains(triggerKey.getName())) {
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                String cronExpression = trigger.getCronExpression();
                String entityCron = entity.getCronExpression();
                if (!cronExpression.equals(entityCron)) {
                    deleteJob(entity.getId());
                    // 删除后再次执行
                    startJob(entity, JobConstant.SYSTEM_RUN);
                }
            } else {
                startJob(entity, JobConstant.SYSTEM_RUN);
            }
        }
        // 开启所有任务
        scheduler.resumeAll();
        scheduler.start();
    }

    public void destroyTask() throws SchedulerException {
        log.info("######## 结束任务 #########");

        // 查询运行中的任务，进行停止操作
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<String> ids = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            String id = jobKey.getName();
            // 暂停任务
            pauseJob(id);
            ids.add(id);
        }

        // 更新运行状态为暂停
        if (ids.size() > 0) {
            jobMapper.updateSituationStatus(ids, JobConstant.PAUSE);
        }
    }

    /**
     * 开启一个定时任务
     *
     * @param jobEntity 任务实体
     * @throws Exception
     */
    public void startJob(JobEntity jobEntity, String runType) {
        try {
            Integer status = jobEntity.getStatus();
            Integer policy = jobEntity.getPolicy();
            // 任务暂停(禁用)状态则不执行后续操作
            if (status.equals(JobConstant.DISABLE)) {
                throw new RuntimeException("禁用状态无法执行");
            }
            // 如果人不是自启、并且不是系统自动触发则不会被执行
            if (!policy.equals(JobConstant.AUTO) && !runType.equals(JobConstant.SYSTEM_RUN)) {
                return;
            }

            String jobId = jobEntity.getId();
            // 判断是否存在
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            if (exists) {
                throw new RuntimeException("任务已经存在!");
            }

            log.info("======== 创建任务：{} ========", jobId);

            // 构建参数
            JobDataMap jobDataMap = new JobDataMap();
            Map<String, Object> map = QuartzUtils.generatorJobDataMap(jobEntity);
            jobDataMap.put("paramMap", map);
            // 以任务的ID来构建定时任务
            Class<? extends Job> aClass = BaseQuartzJobBean.class;
            JobBuilder jobBuilder = JobBuilder.newJob(aClass).withIdentity(jobId);
            jobBuilder.setJobData(jobDataMap);
            JobDetail jobDetail = jobBuilder.build();

            // 设置cron
            String cron = jobEntity.getCronExpression();
            // 验证cron
            CronUtils.isValid(cron);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            // 任务触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobId).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);

            // 更新任务状态为已执行
            // 执行中
            jobEntity.setSituation(JobConstant.EXECUTE);
            jobMapper.update(jobEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("任务启动失败!");
        }
    }


    /**
     * 重新执行任务 - 更新任务
     *
     * @param jobEntity 任务实体
     * @throws SchedulerException
     */
    public void updateJob(JobEntity jobEntity) {
        try {
            String jobId = jobEntity.getId();
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            // 任务原有没有执行，则重新创建任务
            if (!exists) {
                log.info("======== 更新的任务暂未运行,将创建新任务 ========");
                startJob(jobEntity, JobConstant.SYSTEM_RUN);
                return;
            }

            log.info("======== 更新任务：{} ========", jobId);

            // 先删除，再新建
            deleteJob(jobId);
            // 新增
            String runType = jobEntity.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.USER_RUN : JobConstant.SYSTEM_RUN;
            startJob(jobEntity, runType);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务更新失败！");
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobId 定时任务名称
     * @throws SchedulerException
     */
    public void deleteJob(String jobId) {
        try {
            log.info("删除任务：" + jobId);
            JobKey jobKey = JobKey.jobKey(jobId);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务删除失败！ ");
        }
    }


    /**
     * 暂停任务
     *
     * @param jobId 定时任务ID，与构造任务时的ID一致
     * @throws SchedulerException
     */
    public void pauseJob(String jobId) {
        try {
            log.info("暂停任务：" + jobId);
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            if (!exists) {
                log.info("任务未运行!");
                return;
            }
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务暂停失败：" + e.getMessage());
        }
    }

    /**
     * 恢复任务
     *
     * @param job 定时任务ID，与构造任务时的ID一致
     * @throws SchedulerException
     */
    public void resumeJob(JobEntity job) {
        String jobId = job.getId();
        // 恢复任务时会立即执行一次任务
        log.info("恢复任务：" + jobId);
        try {
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);

            if (exists) {
                scheduler.resumeJob(jobKey);
            } else {
                startJob(job, JobConstant.SYSTEM_RUN);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务恢复失败：" + e.getMessage());
        }
    }

    /**
     * 立即执行一次任务 : 没有达到任务的执行周期直接触发
     *
     * @param jobId 定时任务ID，与构造任务时的ID一致
     * @throws SchedulerException
     */
    public void runOnceJob(String jobId) {
        try {
            log.info("立即执行一次任务：" + jobId);
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            if (!exists) {
                throw new RuntimeException("任务未运行!");
            }
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务执行失败：" + e.getMessage());
        }
    }
}
```

# 5、接口测试
`该工程整合swagger进行接口测试实现，具体的接口测试通过swagger发起请求`
## 5.1、新增任务
http://localhost:9090/task/insert
新增一个任务，corn为：`0/5 * * * * ? `，每5S执行一次； 
```josn
{
  "cycle": "seconds",
  "invokeTarget": "sysServiceJob.test('test',10L,20.22D,false)",
  "name": "测试任务二",
  "policy": 2,
  "seconds": "0/5",
  "status": 0
}
```
新增一个任务，corn为：`0/10 * * * * ? `，每10S执行一次；
```json
{
  "cycle": "seconds",
  "day": "*",
  "hour": "*",
  "invokeTarget": "sysServiceJob.test('jobId','jobName')",
  "minute": "*",
  "month": "*",
  "name": "测试任务",
  "policy": 0,
  "seconds": "0/10",
  "status": 0,
  "week": "*"
}
```
 
## 5.2、更新任务
更新一个任务，corn为：`0/3 * * * * ? `，每3S执行一次； 
```josn
{
  "cycle": "seconds",
  "id": "35935f74098c4f33a86eff8eed82b97d",
  "invokeTarget": "sysServiceJob.test('test',10L,20.22D,false)",
  "name": "测试任务三   ",
  "policy": 1,
  "seconds": "0/3",
  "status": 0, 
}
## 5.3、任务详情
