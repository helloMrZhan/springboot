package com.zjq.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
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
 * @date 2021/9/10 22:45
 * <p>title:</p>
 * <p>description:从数据库配置</p>
 */
@Configuration
@MapperScan(basePackages = "com.zjq.mybatis.mapper.slave",sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveDatasourceConfig {

    private static String MAPPER_LOCATION = "classpath:mapper/slave/*Mapper.xml";

    @Bean(name = "slaveDatasource")
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager slaveTransactionManager() {
        return new DataSourceTransactionManager(slaveDataSource());
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDatasource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SlaveDatasourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
