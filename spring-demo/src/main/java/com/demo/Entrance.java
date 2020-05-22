package com.demo;

import com.demo.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @描述:
 * @author: zhangchao
 * @date: 5/22/20 12:49 下午
 **/
public class Entrance {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		//获取xml里配置的bean路径
		String xmlPath = "//Users/zhangchao/code/spring-source-code/spring-framework-5.2.0.RELEASE/spring-demo/src/main/resources/spring/spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		//请注意 这里的beanName必须和xml配置的id一致
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("spring");
	}
}
