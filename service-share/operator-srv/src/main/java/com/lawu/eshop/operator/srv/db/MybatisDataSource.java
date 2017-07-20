package com.lawu.eshop.operator.srv.db;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Configuration
@MapperScan("com.lawu.eshop.operator.srv.mapper")
@EnableTransactionManagement
public class MybatisDataSource {

    /** mybaits mapper xml搜索路径 **/
    private static final String MAPPER_LOCATIONS = "classpath*:sqlmap/**/*.xml";

    private static final String CONFIG_LOCATION = "classpath:mapperConfig.xml";

    private DruidDataSource datasource = null;

    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {
        datasource = new DruidDataSource();
        // 统计过滤配置
        StatFilter statFilter = new StatFilter();
        // 合并sql语句
        statFilter.setMergeSql(true);
        datasource.getProxyFilters().add(statFilter);
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
