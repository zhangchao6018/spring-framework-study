package com.demo.service.impl;

import com.demo.service.WelcomeService;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {

	@Override
	public String sayHello(String name) {
		System.out.println("欢迎你：" + name);
		return "success";
	}
}
