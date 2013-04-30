package com.ingeniouscamel.springjaxws.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public class DeptServiceImpl {
	public Manager getDeptManager(Dept dept) {
		// perform some logic to retrieve manager using dept
		Manager manager = new Manager();
		manager.setEmpId(1);
		manager.setName("Joe Smith");
		manager.setDeptId(dept.getDeptId());
		manager.setLocation(dept.getLocation());
		return manager;
	}
}
