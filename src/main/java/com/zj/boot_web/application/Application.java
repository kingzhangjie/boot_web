package com.zj.boot_web.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages={"com.zj.boot_web"}) // 指定spring管理的bean所在的包
@MapperScan("com.zj.boot_web.mapper") // 指定需要扫描的Mapper位置
@EnableScheduling
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
	

	//@Autowired
	// DataSource dataSource;
	 
	//@Autowired
	//private InterceptorConfig interceptorConfig;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}  
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(Application.class);
    }

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("DATASOURCE = " + dataSource);
	}
	
	/*@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		//System.out.println("DATASOURCE = " + dataSource);
		//registry.addInterceptor(interceptorConfig).addPathPatterns("/**");
	}*/

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(){
	       return new EmbeddedServletContainerCustomizer() {
	           @Override
	           public void customize(ConfigurableEmbeddedServletContainer container) {
	                container.setSessionTimeout(1800); // 单位为S
	          }
	    };
	}
}
