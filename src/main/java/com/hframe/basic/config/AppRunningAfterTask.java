package com.hframe.basic.config;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hframe.basic.util.StringUtils;


/**
 * 系统启动后执行 run 方法中的操作
 * @author Horgn黄小锤
 * @date 2019年2月19日 下午4:19:47
 * @version V1.0
 */
@Component
public class AppRunningAfterTask implements ApplicationRunner {
	
	@Value("${server.port}")
	private String port;
	
	private Logger logger = LoggerFactory.getLogger(AppRunningAfterTask.class);

	/*打印启动完成信息*/
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		String host = InetAddress.getLocalHost().getHostAddress();
		if(StringUtils.isEmpty(host)){
			host = "localhost";
		}
		
		logger.info(" === 启动完成，可以奔跑啦！（ 端口：" + port + "，ip地址：" + host + " ）");
		System.out.println(" \n ==== 启动完成，可以奔跑啦");
		System.out.println(" ==== 启动端口：" + port );
		System.out.println(" ==== 访问地址：" + host + ":" + port + "/\n");
	
	}

}
