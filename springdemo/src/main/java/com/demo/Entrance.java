package com.demo;

import com.demo.aspect.OutSide;
import com.demo.entity.User;
import com.demo.entity.factory.UserFactoryBean;
import com.demo.service.HelloService;
import com.demo.service.HiService;
import com.demo.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @描述:
 * @author: zhangchao
 * @date: 5/22/20 12:49 下午
 **/
@Configuration
@ComponentScan("com.demo")
@EnableAspectJAutoProxy   //开启aspect
@Import(OutSide.class)
public class Entrance {
	public static void main1(String[] args) {
		System.out.println("Hello World!");
		//获取xml里配置的bean路径
		String xmlPath = "//Users/zhangchao/code/spring-source-code/spring-framework-5.2.0.RELEASE/springdemo/src/main/resources/spring/spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		//请注意 这里的beanName必须和xml配置的id一致
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("spring");



		//得到无参构造函数创建的对象:
		User user1a = (User) applicationContext.getBean("user1");
		User user1b = (User) applicationContext.getBean("user1");
		//得到静态工厂创建的对象：
		User user2a = (User) applicationContext.getBean("user2");
		User user2c = (User) applicationContext.getBean("user2");
		//得到实例工厂创建的对象：
		User user3a = (User) applicationContext.getBean("user3");
		User user3b = (User) applicationContext.getBean("user3");

		//得到beanFactory创建的对象：
		//  getBean  name=userFactoryBean 获取到的是User对象
		UserFactoryBean userFactoryBean4a = (UserFactoryBean) applicationContext.getBean("&userFactoryBean");
		User user4b = (User) applicationContext.getBean("userFactoryBean");

		System.out.println("无参构造函数创建的对象:" + user1a);
		System.out.println("无参构造函数创建的对象:" + user1b);
		System.out.println("静态工厂创建的对象：" + user2a);
		System.out.println("静态工厂创建的对象：" + user2c);
		System.out.println("实例工厂创建的对象：" + user3a);
		System.out.println("实例工厂创建的对象：" + user3b);
		System.out.println("factorybean对象：" + userFactoryBean4a);
		System.out.println("factorybean创建的对象：" + user4b);
	}


	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Entrance.class);
//		//默认不支持 prototype下的构造器循环依赖   以下会报错BeanCurrentlyInCreationException
////		Company company = (Company)applicationContext.getBean("company");
//
//		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//		for(String beanDefinitionName : beanDefinitionNames){
//			System.out.println(beanDefinitionName);
//		}
//		WelcomeController welcomeController = (WelcomeController) applicationContext.getBean("welcomeController");
//		welcomeController.handleRequest();
//		User user5 = (User)applicationContext.getBean("user5");
//		System.out.println("CustomizedBeanDefinitionRegistryPostProcessor创建的对象：" + user5);
//
//		System.out.println("-------------------------------------AOP登场----------------------------------------");
//
//		HelloController helloController = (HelloController)applicationContext.getBean("helloController");
//		//异常通知
////		helloController.handleRequest();
//		//一般情况不使用
//		//LittleUniverse littleUniverse= (LittleUniverse) helloController;
//		//littleUniverse.burningup();
//
//		System.out.println("-------------------------------------@Import(OutSide.class)获取指定Bean----------------------------------------");
//		OutSide bean = (OutSide) applicationContext.getBean("com.demo.aspect.OutSide");
//		bean.say();



		System.out.println("-------------------------------------AOP----------------------------------------");
		HelloService helloService = (HelloService) applicationContext.getBean("helloServiceImpl");
		HiService hiService = (HiService) applicationContext.getBean("hiServiceImpl");
		helloService.sayHello();
		System.out.println("-------------------------------------分割...----------------------------------------");
		hiService.sayHi();


	}


}
