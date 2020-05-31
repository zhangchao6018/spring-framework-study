package com.demo.entity.factory;

import com.demo.entity.User;

//静态工厂调用
public class StaticFactory {
	//静态的方法，返回User对象
	public static User getUser(){
		return new User();
	}

}
