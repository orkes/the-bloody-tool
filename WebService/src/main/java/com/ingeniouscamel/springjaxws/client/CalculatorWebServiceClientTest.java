package com.ingeniouscamel.springjaxws.client;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class CalculatorWebServiceClientTest {
	private CalculatorServiceImpl calculatorWebService;

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationClientContext.xml");
		calculatorWebService = (CalculatorServiceImpl) context
				.getBean("calculatorWebService");
	}

	@Test
	public void testAddNumbers() {
		Assert.isTrue(calculatorWebService.addNumbers(3, 5) == 8);
	}

	@Test
	public void testSubtractNumbers() {
		Assert.isTrue(calculatorWebService.subtractNumbers(7, 4) == 3);
	}
}
