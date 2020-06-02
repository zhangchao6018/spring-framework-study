package com.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

//@Repository
public class Staff {
	private Company company;
	@Autowired
	public Staff(Company company){
		this.company = company;
	}
}
