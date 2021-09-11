package com.zjq.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author zjq
 * @date 2021/9/10 22:44
 * <p>title:</p>
 * <p>description:主数据库配置</p>
 */

@Configuration
@MapperScan(basePackages = "com.zjq.mybatis.mapper.master",sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDatasourceConfig {

    private static String MAPPER_LOCATION = "classpath:mapper/master/*Mapper.xml";

    @Bean(name = "masterDatasource")
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDatasource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDatasourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
