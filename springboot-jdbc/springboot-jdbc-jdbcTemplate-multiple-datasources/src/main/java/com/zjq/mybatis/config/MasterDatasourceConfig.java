package com.zjq.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * @author zjq
 * @date 2021/9/10 22:44
 * <p>title:</p>
 * <p>description:主数据库配置</p>
 */

@Configuration
public class MasterDatasourceConfig {

    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(){
        return new DruidDataSource();
    }

    @Bean(name = "masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate(
            @Qualifier("masterDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
