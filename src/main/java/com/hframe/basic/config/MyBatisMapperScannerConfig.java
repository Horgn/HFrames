package com.hframe.basic.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * 通用mapper加载、扫描器
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-01-16 17:56:24
 */
@Configuration
public class MyBatisMapperScannerConfig {
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfig() {
		
		MapperScannerConfigurer mapperScannerConfig = new MapperScannerConfigurer();
		mapperScannerConfig.setSqlSessionFactoryBeanName("sqlSessionFactory");
		
		// 扫描所有 Mapper 接口
		mapperScannerConfig.setBasePackage("com.hframe.**.mapper");
		Properties properties = new Properties();
		
		// 配置通用 Mapper 路径
		properties.setProperty("mappers", "com.hframe.basic.base.BaseMapper");
		properties.setProperty("notEmpty", "false");
		
		// 数据库类型
		// properties.setProperty("IDENTITY", "ORACLE");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfig.setProperties(properties);

		return mapperScannerConfig;
	}
}