package com.probsjustin.IBMMQAAS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class controller_mq_send {

	List<String> expectedParameters = new ArrayList();  
	Map<String, String> realParametersFromUser = new HashMap<String, String>(); 
	HttpServletRequest realRequest = null; 
	HttpServletResponse realResponse = null; 
	String responseString = ""; 
	String missingParameters = ""; 
	
	
	controller_mq_send(HttpServletRequest func_request, HttpServletResponse func_response){
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
		this.expectedParameters.add("port"); 

	}
	
	Map<String, String> getExpectedParameters(HttpServletRequest func_request){
		Map<String, String> returnObject = new HashMap<String, String>(); 
		for (String temp_str_itr : this.expectedParameters){
			this.realParametersFromUser.put(temp_str_itr, func_request.getParameter(temp_str_itr)); 
		}
		return returnObject; 
	}
	
	ExecutorService create_instExecutorAndTask() {
		return Executors.newSingleThreadExecutor();
	}
	
	HttpServletResponse controller(identification_request_holder func_identification_request_holder_instance) throws IOException {
		this.realParametersFromUser = getExpectedParameters(this.realRequest);
		if(compareRequestParamsExpected_Real()) {
			this.realResponse.getWriter().append("The request has the required parameters.");
			runnable_mq_send instance_runnable_mq_send = new runnable_mq_send(this.realParametersFromUser); 
			ExecutorService executor_thrd_runnable_IBMMQ_send = create_instExecutorAndTask(); 
		
		}else {
			this.missingParameters = findMissingRequestParams(); 
		}
		
		return this.realResponse;
	}
}
