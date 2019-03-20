package com.wonders.core.db.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * 多数据源配置-卫监业务数据源
 * 
 * @author silent
 */
@Configuration
@MapperScan(basePackages = "com.wonders.hlthspv.**.dao", sqlSessionTemplateRef = "hlthspvSqlSessionTemplate")
public class HlthspvDataSourceConfiguration {
	private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.wonders.hlthspv.**.manager.*Manager.*(..))";

	/**
	 * 数据源
	 * @return
	 */
	@Bean(name = "hlthspvDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hlthspv")
	public DataSource hlthspvDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	/**
	 * 连接会话工厂
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "hlthspvSqlSessionFactory")
	public SqlSessionFactory hlthspvSqlSessionFactory(@Qualifier("hlthspvDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis.xml"));
		bean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:mapper/com/wonders/hlthspv/**/*.xml"));
		return bean.getObject();
	}

	/**
	 * 事务管理
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "hlthspvTransactionManager")
	public DataSourceTransactionManager hlthspvTransactionManager(
			@Qualifier("hlthspvDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * 执行会话
	 * @param sqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "hlthspvSqlSessionTemplate")
	public SqlSessionTemplate hlthspvSqlSessionTemplate(
			@Qualifier("hlthspvSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * 事务切面方法
	 * @param transactionManager
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "hlthspvTxAdvice")
	public TransactionInterceptor hlthspvTxAdvice(
			@Qualifier("hlthspvTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("get*", "readOnly");
		properties.setProperty("find*", "readOnly");
		properties.setProperty("select*", "readOnly");
		properties.setProperty("pageSelect*", "readOnly");
		properties.setProperty("count*", "readOnly");
		properties.setProperty("if*", "readOnly");
		properties.setProperty("*", "PROPAGATION_REQUIRED,-Exception");
		TransactionInterceptor tsi = new TransactionInterceptor(transactionManager, properties);
		return tsi;
	}

	/**
	 * 切点定位
	 * @param txAdvice
	 * @return
	 */
	@Bean
	public Advisor txAdviceAdvisor(@Qualifier("hlthspvTxAdvice") TransactionInterceptor txAdvice) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, txAdvice);
	}
}