package com.hframe.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 异步线程任务配置<br>
 * 注意：要使用异步线程任务，需要在启动类上添加注解 @EnableAsync
 * @author Horgn黄小锤
 * @date 2019年2月22日 上午11:01:03
 * @version V1.0
 */
@Component
public class ThreadConfig {
	
	@Bean
	public TaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(20);//最大线程数
		taskExecutor.setCorePoolSize(10);//线程池的基本大小
		taskExecutor.setQueueCapacity(2000);//最大队列长度
		return taskExecutor;
	}
}
