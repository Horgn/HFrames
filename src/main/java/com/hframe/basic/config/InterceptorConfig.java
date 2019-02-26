package com.hframe.basic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.hframe.basic.interceptor.LoginInterceptor;

/**
 * 拦截器配置
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-01-24 14:20:01
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
	
	@Value("${hframe.web.root-path}")
	private String uploadPath;

	// 以下WebMvcConfigurerAdapter 比较常用的重写接口
	// /** 解决跨域问题 **/
	// public void addCorsMappings(CorsRegistry registry) ;
	// /** 添加拦截器 **/
	// void addInterceptors(InterceptorRegistry registry);
	// /** 这里配置视图解析器 **/
	// void configureViewResolvers(ViewResolverRegistry registry);
	// /** 配置内容裁决的一些选项 **/
	// void configureContentNegotiation(ContentNegotiationConfigurer
	// configurer);
	// /** 视图跳转控制器 **/
	// void addViewControllers(ViewControllerRegistry registry);
	// /** 静态资源处理 **/
	// void addResourceHandlers(ResourceHandlerRegistry registry);
	// /** 默认静态资源处理器 **/
	// void configureDefaultServletHandling(DefaultServletHandlerConfigurer
	// configurer);

	/** 配置静态资源,避免静态资源请求被拦截 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/");//框架图标
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/templates/static/");//前端静态文件获取路径
		registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/templates/upload/", "file:"+uploadPath);//图片上传获取路径
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");//页面获取路径
		registry.addResourceHandler("/themes/**").addResourceLocations("classpath:/templates/static/BJUI/plugins/kindeditor_4.1.10/themes/");//kindeditor 编辑器样式路径
		registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/templates/static/BJUI/plugins/kindeditor_4.1.10/plugins/");//kindeditor 编辑器插件路径
		super.addResourceHandlers(registry);
	}

	/** 配置拦截器 */
	public void addInterceptors(InterceptorRegistry registry) {
		// 登录拦截器
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns( "/note/*", "/login", "/static/**",  "/code", "/upload/**");
		super.addInterceptors(registry);
	}

}
