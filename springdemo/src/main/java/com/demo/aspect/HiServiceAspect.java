package com.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
public class HiServiceAspect {
	@Pointcut("execution(* com.demo.service.impl.HiServiceImpl.*(..))")
	public void plugin(){}
	@Before("plugin()")
	public void before(JoinPoint joinPoint){
		System.out.println("进行before拦截 " + joinPoint);
	}
	@After("plugin()")
	public void after(JoinPoint joinPoint){
		System.out.println("进行after拦截 " + joinPoint );
	}
	@AfterReturning(pointcut = "plugin()", returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue){
		System.out.println("进行return拦截  " + joinPoint + "，返回值[" + returnValue + "]");
	}

}
