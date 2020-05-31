package com.demo.service.impl;

import com.demo.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
	@Override
	public void sayHello() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hello everybody");
	}

	@Override
	public void JustWantToThrowException() {
		throw  new RuntimeException("hello exception");
	}
}
