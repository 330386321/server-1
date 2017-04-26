package com.lawu.eshop.property.srv.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.lawu.eshop.property.srv.PropertySrvConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Configuration
@MapperScan("com.lawu.eshop.property.srv.mapper")
@EnableTransactionManagement
public class MybatisDataSource {

    /** mybaits mapper xml搜索路径 **/
    private final static String MAPPER_LOCATIONS = "classpath*:sqlmap/**/*.xml";

    private final static String CONFIG_LOCATION = "classpath:mapperConfig.xml";

    @Autowired
    private PropertySrvConfig propertySrvConfig;
    private DruidDataSource datasource = null;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        datasource = new DruidDataSource();
        datasource.setUrl(propertySrvConfig.getUrl());
        datasource.setDbType(propertySrvConfig.getType());
        datasource.setDriverClassName(propertySrvConfig.getDriver());
        datasource.setUsername(propertySrvConfig.getUsername());
        datasource.setPassword(propertySrvConfig.getPassword());
        return datasource;
    }

    @PreDestroy
    public void close() {
        if (datasource != null) {
            datasource.close();
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATIONS));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource(CONFIG_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}
