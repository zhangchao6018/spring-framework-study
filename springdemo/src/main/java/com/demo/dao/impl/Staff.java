package com.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Staff {
	private Company company;
	@Autowired
	public Staff(Company company){
		this.company = company;
	}
}
