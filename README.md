## 准备环境

   需要gradle环境支持
    官网下载spring源码,gradle编译源码:./gradlew :spring-oxm:compileTestJava
    ide导入编译好的工程,排除spring-aspects工程(该工程的编译环境非gradle,具体查看官网描述:https://github.com/spring-projects/spring-framework/tree/v5.2.0.RELEASE)

## hello

   xml形式读取bean:
   
        1.新建spring-demo工程,build.gradle添加依赖引用本地spring-context    
        2.编写xml,配置bean:resources/spring/spring-config.xml   
        3.编写测试类,测试spring容器:com.demo.Entrance  --run     
   
   注解形式读取bean
        加入口类加上注解:@Configuration ,@ComponentScan("com.demo")

## 全局掌握核心接口和类
   
   解决了关键的问题:将对象之间的关系转为配置(xml/注解)来管理
        
        1.依赖注入--依赖关系在Spring的IOC容器中管理
        2.通过把对象包装在Bean中以达到管理对象和进行额外操作的目的
        
## Bean与BeanDefinition

   Bean的本质就是java对象,只是这个对象的声明周期由容器来管理
    ->不需要为了创建Bean而在原来的java来上添加任何额外的限制--低侵入
    
   一.Bean的定义:
        根据配置,生成用来描述Bean的BeanDefinition,常用属性:
            
            1.1.作用范围scope(@Scope)  singleton prototype(多个) request session globalsession
            
            1.2.懒加载lazy-init(@Lazy):决定Bean实例是否延迟加载
            
            1.3.首选primary(@Primary) :设置为true的bean会是优先的实现类
            
            1.4.factory-bean和factory-method(@Configuration和@Bean)
            
   二.容器初始化做的主要事情
        读取配置文件->读取成Resource对象->解析BeanDefinition->注册进容器
            
            解析配置
            
            定位与注册对象
        
        组件扫描:自动发现应用容器中需要创建的Bean,例如:@Controller @Service ...
        
        自动装配:自动满足Bean之间的依赖,如:@Autowired
            源码:org.springframework.beans.factory.config.AutowireCapableBeanFactory
            
                org.springframework.beans.factory.support.DefaultListableBeanFactory.beanDefinitionMap -->bean实例容器
                
   三.BeanFactory  & ApplicationContext       
   
        BeanFactory 面向spring框架自身
        
        ApplicationContext 面向开发者
        
            public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
                    MessageSource, ApplicationEventPublisher, ResourcePatternResolver 
            EnvironmentCapable :主要可以获取启动参数,配置文件等
            
            ListableBeanFactory:通过列表方式管理Bean
            
            HierarchicalBeanFactory:多层级容器,对每个层级Bean的管理
            
            ResourcePatternResolver:加载资源文件
            
            MessageSource:国际化
            
            ApplicationEventPublisher:事件发布能力(监听机制)
            
        ApplicationContext常用容器(实现)
        
            传统的基于Xml配置的经典容器
            
                FileSystemXmlApplicationContext:从文件系统加载配置
                
                ClassPathXmlApplicationContext:从classpath加载配置
                
                XmlWenApplicationContext:用于web应用容器
                
            目前比较流行的容器:
            
                AnnotationConfigServletWebServerApplicationContext  spring-boot
                
                AnnotationConfigReactiveWebServerApplicationContext  spring-boot
                
                AnnotationConfigApplicationContext  --非web应用
             
   容器的共性:
   
            refresh()大致功能:
            
                容器初始化,配置解析
                
                BeanFactoryPostProcessor和BeanPostProcessor的注册和激活
                
                国际化配置
                
                ...
        模板方法模式
        
            1.围绕抽象类,实现通用逻辑,定义模板结构,部分逻辑由子类实现
            
                复用代码  相同代码放在父类,具体不同业务代码下沉到子类
                
                反向控制  通过子类的不同实现,扩展出不同行为,符合开闭原则
                
            2.模式设计的方法类
            
                模板方法
                
                具体方法(确定不变的,一般直接在父类写好)
                
                钩子方法 供子类变更的钥匙
                
                抽象方法 增强父类给子类的灵活性
                
            3.demo示例:
            
                https://github.com/zhangchao6018/simple-framework
                
                    demo.pattern.template
                    
            4.spring典型实践:
            
                org.springframework.context.support.AbstractApplicationContext.refresh
   四.Resource
    
        强大的加载资源方式
        
            自动识别"classpath:"   "file:"等资源的地址前缀
            
            支持自动解析Ant风格带通配符的资源地址
            
                ?  匹配任何单字符
                
                *  匹配o或者任意数量字符
                
                ** 匹配0或者更多目录
                
   五.BeanDefinitionReader
    
        读取BeanDefinition
        
        BeanDefinitionRegistry
        
        源码解析:   
        
            断点:org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(org.springframework.core.io.Resource)
            
            可以看到resource参数即为我们配置的xml路径
            
            核心api:org.springframework.beans.factory.support.DefaultListableBeanFactory.registerBeanDefinition  
            
   六. PostProcessor   影响容器里面的行为
    
        BeanDefinitionRegistryPostProcessor
        
        BeanFactoryPostProcessor
        
        BeanPostProcessor
        
   七.Aware
   
        从Bean里获取到容器实例并对其进行操作
        
   八.事件监听器模式(观察主模式的一种模式)  --解耦,各种监听器实现互不影响
   
        组件:回调函数
        
        监听器将监听感兴趣的事件,一旦事件发生,便作出响应
        
            事件源   ApplicationEvent
            
            事件监听器  ApplicationListener
            
            事件对象  Pubiesher以及Multicaster
            
        @see https://github.com/zhangchao6018/simple-framework:demo.pattern.eventmode
        
## refresh

    prepareRefresh
    
## pring依赖注入


   1.核心点:单例的三级缓存解决单例Bean循环依赖问题
   
   2.循环依赖问题:  @see /t-img/spring循环依赖图解.jpg
   
        使用三级缓存解决:大体思路->Bean_A依赖Bean_B,同时,Bean_B依赖Bean_A,初始化A时会先初始化A的空壳(此时成员变量暂未填充),放入三级缓存,然后初始化成员B(递归)调用初始化,初始化B时从三级缓存获取到了A,直接取出填充到成员变量,放入二级缓存,清除三级缓存....
        
        org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean
        
        源码debug验证:
        
            org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.addSingletonFactory
            
            debug设置条件:beanName.equals("boyFriend")||beanName.equals("girlFriend")
   3.question:
   
        Spring是否支持所有循环依赖的情况
        
            循环依赖的情况如下:
            
                构造器循环依赖(singleton--不支持\prototype--不支持)
                
                Setter注入循环依赖(singleton--默认支持\prototype--不支持)
                
                原因:没有三级缓存的支持(因为无法支持,prototype情况spring无法知道谁依赖谁),因此仅仅依靠beanName校验会陷入无限的鸡生蛋蛋生鸡死循环
    
   4.populateBean源码:org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean
   
    populateBean功能(反射创建了原始的Bean对象后,调用populateBean填充属性)
    
        调用Bean的Setter方法实例去给Bean设置上属性值
        
        变量类型的转换,同时还要考虑处理集合类型的情况
        
        处理显式自动装配的逻辑(autowired=byName/byType)
        
      
## AOP

   为什么在入口类上加上@EnableAspectJAutoProxy 就能支持AOP
   
    方式:
    
        通过注解将类当做Bean管理起来的方式 @Controller @Service @Component 
        
        @Bean
        
        @Import标签
        
    SpringAOP总体流程:
    
        注册解析AOP的服务
        
        解析和加载横切逻辑
        
            org/springframework/aop/framework/autoproxy/AbstractAutoProxyCreator.java:259
            
        将横切逻辑织入目标Bean中
        
            org/springframework/aop/framework/autoproxy/AbstractAutoProxyCreator.java:387
            
            org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
    
## MVC环境搭建

   新建gradle-module:springmvcdemo 
   
    配置tomcat:JKD11+无视war包
    
    配置步骤:
    
        添加spring-mvc依赖以及servlet,jsp依赖
        
    SpringMVC是核心流程        
    
        简建立请求和Controller方法的映射集合的流程
        
        根据请求查找对应的Controller方法的流程
        
        请求参数绑定到方法形参,执行方法处理请求,渲染视图的流程
        
    @see org.springframework.web.servlet.DispatcherServlet



# <img src="src/docs/asciidoc/images/spring-framework.png" width="80" height="80"> Spring Framework [![Build Status](https://build.spring.io/plugins/servlet/wittified/build-status/SPR-PUBM)](https://build.spring.io/browse/SPR)

This is the home of the Spring Framework: the foundation for all [Spring projects](https://spring.io/projects). Collectively the Spring Framework and the family of Spring projects is often referred to simply as "Spring". 

Spring provides everything required beyond the Java programming language for creating enterprise applications for a wide range of scenarios and architectures. Please read the [Overview](https://docs.spring.io/spring/docs/current/spring-framework-reference/overview.html#spring-introduction) section as reference for a more complete introduction.

## Code of Conduct

This project is governed by the [Spring Code of Conduct](CODE_OF_CONDUCT.adoc). By participating, you are expected to uphold this code of conduct. Please report unacceptable behavior to spring-code-of-conduct@pivotal.io.

## Access to Binaries

For access to artifacts or a distribution zip, see the [Spring Framework Artifacts](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Artifacts) wiki page.

## Documentation

The Spring Framework maintains reference documentation ([published](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/) and [source](src/docs/asciidoc)), Github [wiki pages](https://github.com/spring-projects/spring-framework/wiki), and an
[API reference](https://docs.spring.io/spring-framework/docs/current/javadoc-api/). There are also [guides and tutorials](https://spring.io/guides) across Spring projects.

## Build from Source

See the [Build from Source](https://github.com/spring-projects/spring-framework/wiki/Build-from-Source) Wiki page and the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## Stay in Touch

Follow [@SpringCentral](https://twitter.com/springcentral), [@SpringFramework](https://twitter.com/springframework), and its [team members](https://twitter.com/springframework/lists/team/members) on Twitter. In-depth articles can be found at [The Spring Blog](https://spring.io/blog/), and releases are announced via our [news feed](https://spring.io/blog/category/news).

## License

The Spring Framework is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
