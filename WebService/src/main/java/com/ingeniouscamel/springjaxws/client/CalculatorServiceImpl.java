package com.ingeniouscamel.springjaxws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "CalculatorServiceImpl", targetNamespace = "http://service.springjaxws.ingeniouscamel.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface CalculatorServiceImpl {

	@WebMethod
	@WebResult(partName = "return")
	public double addNumbers(
			@WebParam(name = "arg0", partName = "arg0") double arg0,
			@WebParam(name = "arg1", partName = "arg1") double arg1);

	@WebMethod
	@WebResult(partName = "return")
	public double subtractNumbers(
			@WebParam(name = "arg0", partName = "arg0") double arg0,
			@WebParam(name = "arg1", partName = "arg1") double arg1);
}
