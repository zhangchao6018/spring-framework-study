package com.demo.dao.impl;

//@Repository
public class Company {
	private Staff staff;
//	@Autowired
	public Company(Staff staff){
		this.staff = staff;
	}
}
