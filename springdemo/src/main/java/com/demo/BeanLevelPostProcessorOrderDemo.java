package com.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;

import java.lang.reflect.Constructor;

/**
 * BeanLevelPostProcessor执行顺序
 */
@Configuration
public class BeanLevelPostProcessorOrderDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLevelPostProcessorOrderDemo.class);
		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(DemoBean.class);
		rootBeanDefinition.setLazyInit(true);
		context.registerBeanDefinition("demoBean", rootBeanDefinition);
		context.getBean("demoBean");
		context.start();
		context.close();
	}
	public  static  class DemoBean{

	}
	@Bean
	public BeanPostProcessor fullyBeanPostProcessor(){
		return  new FullyBeanPostProcessor();
	}
	@Order
	public  class  FullyBeanPostProcessor implements BeanPostProcessor,
			InstantiationAwareBeanPostProcessor,
			SmartInstantiationAwareBeanPostProcessor,
			MergedBeanDefinitionPostProcessor,
			DestructionAwareBeanPostProcessor {
		@Nullable
		@Override
		public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
			if(beanClass == DemoBean.class){
				System.out.println("1-------->InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
			}
			return null;
		}

		@Nullable
		@Override
		public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
			if(beanClass == DemoBean.class) {
				System.out.println("2-------->SmartInstantiationAwareBeanPostProcessor.determineCandidateConstructors");
			}
			return null;
		}

		@Override
		public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
			if(beanType == DemoBean.class){
				System.out.println("3-------->MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition");
			}
		}

		@Override
		public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
			if(bean instanceof  DemoBean){
				System.out.println("4-------->InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
			}
			return true;
		}

		@Nullable
		@Override
		public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName){
			if(bean instanceof  DemoBean){
				System.out.println("5-------->InstantiationAwareBeanPostProcessor.postProcessProperties");
			}
			return null;
		}
		@Nullable
		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
			if(bean instanceof  DemoBean){
				System.out.println("6-------->BeanPostProcessor.postProcessBeforeInitialization");
			}
			return null;
		}

		@Nullable
		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			if(bean instanceof  DemoBean){
				System.out.println("7-------->BeanPostProcessor.postProcessAfterInitialization");
			}
			return null;
		}

		@Override
		public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
			if(bean instanceof  DemoBean){
				System.out.println("8-------->DestructionAwareBeanPostProcessor.postProcessBeforeDestruction");
			}
		}
	}
}
