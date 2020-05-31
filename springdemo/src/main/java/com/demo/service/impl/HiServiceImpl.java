package com.demo.service.impl;

import com.demo.service.HiService;
import org.springframework.stereotype.Service;

@Service
public class HiServiceImpl implements HiService {
	@Override
	public void sayHi() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hi everyone");
	}

	@Override
	public String justWantToSayHi() {
		return "Just want to say hi";
	}
}
