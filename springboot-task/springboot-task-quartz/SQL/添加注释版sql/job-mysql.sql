/*
MySQL - 8.0.29 : Database - job-mysql
*********************************************************************
*/
CREATE DATABASE `job-mysql`;

USE `job-mysql`;
-- 1. `qrtz_calendars`
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日历信息表';

-- 2. `qrtz_job_details`
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`job_name` varchar(200) NOT NULL COMMENT '任务名称',
`job_group` varchar(200) NOT NULL COMMENT '任务组名',
`description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
`job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
`is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
`is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
`is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
`requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执`job-mysql`行',
`job_data` blob COMMENT '存放持久化job对象',
PRIMARY KEY (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务详细信息表';

-- 3. `qrtz_triggers`
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
`trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
`job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
`job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
`description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
`next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
`prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
`priority` int DEFAULT NULL COMMENT '优先级',
`trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
`trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
`start_time` bigint NOT NULL COMMENT '开始时间',
`end_time` bigint DEFAULT NULL COMMENT '结束时间',
`calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
`misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
`job_data` blob COMMENT '存放持久化job对象',
PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
KEY `sched_name` (`sched_name`, `job_name`, `job_group`),
CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='触发器详细信息表';

-- 4. `qrtz_cron_triggers`
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
`trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
`cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
`time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Cron类型的触发器表';

-- 5. `qrtz_blob_triggers`
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
`trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
`blob_data` blob COMMENT '存放持久化Trigger对象',
PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Blob类型的触发器表';

-- 6. `qrtz_simple_triggers`
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
`trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
`repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
`repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
`times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`)
REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简单触发器的信息表';

-- 7. `qrtz_simprop_triggers`
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE qrtz_simprop_triggers
(
`sched_name`    VARCHAR(120)   NOT NULL COMMENT '调度名称',
`trigger_name`  VARCHAR(200)   NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
`trigger_group` VARCHAR(200)   NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
STR_PROP_1    VARCHAR(512)   NULL COMMENT 'String类型的trigger的第一个参数',
STR_PROP_2    VARCHAR(512)   NULL COMMENT 'String类型的trigger的第二个参数',
STR_PROP_3    VARCHAR(512)   NULL COMMENT 'String类型的trigger的第三个参数',
INT_PROP_1    INT            NULL COMMENT 'int类型的trigger的第一个参数',
INT_PROP_2    INT            NULL COMMENT 'int类型的trigger的第二个参数',
LONG_PROP_1   BIGINT         NULL COMMENT 'long类型的trigger的第一个参数',
LONG_PROP_2   BIGINT         NULL COMMENT 'long类型的trigger的第二个参数',
DEC_PROP_1    NUMERIC(13, 4) NULL COMMENT 'decimal类型的trigger的第一个参数',
DEC_PROP_2    NUMERIC(13, 4) NULL COMMENT 'decimal类型的trigger的第二个参数',
BOOL_PROP_1   VARCHAR(1)           NULL COMMENT 'Boolean类型的trigger的第一个参数',
BOOL_PROP_2   VARCHAR(1)           NULL COMMENT 'Boolean类型的trigger的第二个参数',
PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='CalendarIntervalTrigger和DailyTimeIntervalTrigger类型触发器';

-- 8. `qrtz_locks`
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储的悲观锁信息表';

-- 9. `qrtz_paused_trigger_grps`
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
PRIMARY KEY (`sched_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='暂停的触发器表';

-- 10. `qrtz_fired_triggers`
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
`trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
`trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
`instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
`fired_time` bigint NOT NULL COMMENT '触发的时间',
`sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
`priority` int NOT NULL COMMENT '优先级',
`state` varchar(16) NOT NULL COMMENT '状态',
`job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
`job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
`is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
`requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
PRIMARY KEY (`sched_name`, `entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='已触发的触发器表';


-- 11. `qrtz_scheduler_state`

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
`sched_name` varchar(120) NOT NULL COMMENT '调度名称',
`instance_name` varchar(200) NOT NULL COMMENT '实例名称',
`last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
`checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='调度器状态表';

-- 12. `sys_job` (自定义表，可能依赖于Quartz的某些表，但这里放在最后)
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
id VARCHAR(255) NOT NULL PRIMARY KEY, -- 假设VARCHAR长度是255，您可能需要根据实际情况调整
name VARCHAR(255) NOT NULL, -- 同样，这里假设VARCHAR长度是255
cycle VARCHAR(255) NOT NULL,
invoke_target VARCHAR(255) NOT NULL,
cron_expression VARCHAR(255) NOT NULL,
policy INT NOT NULL,
situation INT NOT NULL,
version INT NOT NULL,
last_run_time TIMESTAMP NULL DEFAULT NULL,
next_run_time TIMESTAMP NULL DEFAULT NULL,
status INT NOT NULL,
del_flag INT NOT NULL,
remark VARCHAR(255) NULL DEFAULT NULL, -- 假设VARCHAR长度是255
create_by VARCHAR(255) NOT NULL, -- 假设VARCHAR长度是255
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 假设创建时间默认为当前时间戳
update_by VARCHAR(255) NULL DEFAULT NULL, -- 假设VARCHAR长度是255
update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP -- 假设更新时间在更新记录时自动更新
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度表';

-- 13. `sys_job_log` (自定义表，可能依赖于Quartz的某些表，但这里放在最后)
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (  
`id` VARCHAR(255) NOT NULL PRIMARY KEY,  -- 假设id是唯一的字符串  
`task_id` VARCHAR(255) NOT NULL,         -- 假设taskId是字符串类型  
`time` bigint(20) DEFAULT NULL,            -- time字段可以改为DATETIME以存储具体的时间点
`status` TINYINT(1) NOT NULL DEFAULT 0,  -- 执行状态，使用TINYINT(1)来存储0或1  
`exception_info` TEXT DEFAULT NULL,       -- 异常信息，使用TEXT类型来存储可能较长的文本  
`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 创建时间，默认为当前时间戳  
INDEX `idx_task_id` (`task_id`)           -- 可选：为taskId创建索引，以提高查询效率  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志表';  -- 使用InnoDB引擎和utf8mb4字符集