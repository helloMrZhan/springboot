package com.zjq.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zjq
 * @date 2021/9/10 22:45
 * <p>title:</p>
 * <p>description:从数据库配置</p>
 */
@Configuration
public class SlaveDatasourceConfig {

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource slaveDataSource(){
        return new DruidDataSource();
    }

    @Bean(name = "slaveJdbcTemplate")
    public JdbcTemplate slaveJdbcTemplate(
            @Qualifier("slaveDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
