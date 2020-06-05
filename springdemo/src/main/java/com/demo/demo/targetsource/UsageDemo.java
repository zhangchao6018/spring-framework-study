package com.demo.demo.targetsource;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * targetSource的用法
 */
public class UsageDemo {
	public static void main(String[] args) throws Exception {
		Target target = new Target();
		TargetSource targetSource = new SingletonTargetSource(target);
		// 使用SpringAOP框架的代理工厂直接创建代理对象
		Target proxy = (Target) ProxyFactory.getProxy(targetSource);
		System.out.println("getName: " + proxy.getClass().getName());
		System.out.println("getTargetClass: " + targetSource.getTargetClass());
		System.out.println("getTarget: " + targetSource.getTarget());
		System.out.println("isStatic: " + targetSource.isStatic());
	}
}
