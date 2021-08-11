package org.heyu.homework0709.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author heyu
 * @date 2021/8/10
 */
@Configuration
@MapperScan(basePackages = "org.heyu.homework0709.dao.db1", sqlSessionFactoryRef = "db1SqlSessionFactory")
public class DataSourceConfig1 {

	@Primary
	@Bean("db1DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.db1")
	public DataSource getDb1DataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean("db1SqlSessionFactory")
	public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		// mapper的xml形式文件位置必须要配置，不然将报错：no statement
		// （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db1/*.xml"));
		return bean.getObject();
	}

	@Primary
	@Bean("db1SqlSessionTemplate")
	public SqlSessionTemplate db1SqlSessionTemplate(
			@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
