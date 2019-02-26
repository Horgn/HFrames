package com.hframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 系统启动接口
 * @author Horgn黄小锤
 * @date 2019年1月30日 下午5:18:56
 * @version V1.0
 */
@EnableAsync//启动异步线程任务
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}

