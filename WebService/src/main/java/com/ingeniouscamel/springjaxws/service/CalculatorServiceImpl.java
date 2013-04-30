package com.ingeniouscamel.springjaxws.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public class CalculatorServiceImpl {
	public double addNumbers(double a, double b) {
		return a + b;
	}

	public double subtractNumbers(double a, double b) {
		return a - b;
	}
}
