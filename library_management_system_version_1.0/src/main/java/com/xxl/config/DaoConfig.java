package com.xxl.config;

import com.xxl.util.LogUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription:  mybatis-spring
 */
@Configuration
@EnableTransactionManagement
public class DaoConfig {
    /**
     * 数据连接
     */
    private static String  driver = null;
    private static String  password = null;
    private static String  username = null;
    private static String  url = null;
    private static final Logger log = LogUtil.getLogger(DaoConfig.class);
    /**
     *  数据库文件名
     */
    private static String dbProperFileName = "db.properties";
    /**
     * mybatis文件名
     */
    private  String mybatisConfigFileName = "mybatis-config.xml";
    /**
     *  读取配置文件
     */
    static {
        try(InputStream inputStream = DaoConfig.class.getClassLoader().getResourceAsStream(dbProperFileName)){
            Properties properties = new Properties();
            properties.load(inputStream);
            driver = properties.getProperty("driver");
            password = properties.getProperty("password");
            username = properties.getProperty("username");
            url = properties.getProperty("url");
            properties.clear();
        }catch (IOException e){
            log.error(e);
        }
    }
    /**
     * 连接数据库
     * @author xxl
     * @return DriverManagerDataSource
     */
    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        return dataSource;
    }
    /**
     * 生产SqlSessionFactory
     * @author xxl
     * @return SqlSessionFactoryBean
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置数据源
        sqlSessionFactoryBean.setDataSource(driverManagerDataSource());
        //设置mybatis.xml配置文件
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFileName));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     *  需要是实现动态代理的接口包路径,dao层的动态代理类
     */
    private String basePackage = "com.xxl.dao";
    /**
     * 之前的mybatis-spring整合需要一个实现类完成sqlSession的使用，现在只想要拍扫描包动态代理dao接口
     * 生成实现类(很重要)
     * @author xxl
     * @return MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        //设置sqlSessionFactoryBean
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        //扫描包生成代理类
        mapperScannerConfigurer.setBasePackage(basePackage);
        return mapperScannerConfigurer;
    }

    /**
     * 配置事务manager
     * @author xxl
     * @return DataSourceTransactionManager
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(driverManagerDataSource());
        return dataSourceTransactionManager;
    }

}
