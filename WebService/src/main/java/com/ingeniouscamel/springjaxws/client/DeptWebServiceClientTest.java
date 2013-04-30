package com.ingeniouscamel.springjaxws.client;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.ingeniouscamel.springjaxws.service.Dept;
import com.ingeniouscamel.springjaxws.service.Manager;

public class DeptWebServiceClientTest {
	private DeptServiceImpl deptWebService;

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationClientContext.xml");
		deptWebService = (DeptServiceImpl) context.getBean("deptWebService");
	}

	@Test
	public void testAddNumbers() {
		Dept dept = new Dept();
		dept.setDeptId(1);
		dept.setLocation("Boston");
		Manager manager = deptWebService.getDeptManager(dept);
		Assert.notNull(manager);
		Assert.isTrue(dept.getDeptId() == manager.getDeptId());
		Assert.isTrue(dept.getLocation().equals(manager.getLocation()));
		System.out.println(manager.getName());
	}

}
