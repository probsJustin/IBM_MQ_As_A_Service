package com.probsjustin.IBMMQAAS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class controller_mq_rec {
	List<String> expectedParameters = new ArrayList();  
	Map<String, String> realParametersFromUser = new HashMap<String, String>(); 
	HttpServletRequest realRequest = null; 
	HttpServletResponse realResponse = null; 
	String responseString = ""; 
	
	controller_mq_rec(HttpServletRequest func_request, HttpServletResponse func_response){
		this.realRequest = func_request; 
		this.realResponse = func_response; 
		setExpectedRequestParams(); 
		compareRequestParamsExpected_Real(); 
		this.responseString = findMissingRequestParams(); 
	}
	
	Boolean compareRequestParamsExpected_Real() {
		Boolean returnObject = true; 
		for(String temp_str_itr : this.expectedParameters) {
			if(!this.realParametersFromUser.containsKey(temp_str_itr)) {
				returnObject = false;
			}
		}
		return returnObject; 
	}
	
	String findMissingRequestParams() {
		String returnObject = "";
		for (String temp_str_itr : this.expectedParameters){
			if(!this.realParametersFromUser.containsKey(temp_str_itr)) {
				returnObject += temp_str_itr + ", ";
			}
		}
		returnObject = returnObject.toString().substring(0, returnObject.length()-2);
		return returnObject; 
	}
	
	void setExpectedRequestParams() {
		this.expectedParameters.add("host"); 
		this.expectedParameters.add("message"); 
		this.expectedParameters.add("channel"); 
		this.expectedParameters.add("queue"); 
	}
	
	HttpServletResponse controller(HttpServletRequest func_request, HttpServletResponse func_response, identification_request_holder func_identification_request_holder_instance) {
		
	}
}
