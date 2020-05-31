package com.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Company {
	private Staff staff;
	@Autowired
	public Company(Staff staff){
		this.staff = staff;
	}
}
