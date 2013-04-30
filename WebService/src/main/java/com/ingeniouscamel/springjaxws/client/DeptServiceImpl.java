package com.ingeniouscamel.springjaxws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.ingeniouscamel.springjaxws.service.Dept;
import com.ingeniouscamel.springjaxws.service.Manager;

@WebService(name = "DeptServiceImpl", targetNamespace = "http://service.springjaxws.ingeniouscamel.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DeptServiceImpl {

	@WebMethod
	@WebResult(partName = "return")
	public Manager getDeptManager(
			@WebParam(name = "arg0", partName = "arg0") Dept arg0);

}
