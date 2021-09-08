package com.probsjustin.IBMMQAAS;

import java.util.Date;

public class identification_request_holder {
	String requestURI = ""; 
	String requestTimeStamp = ""; 
	String requestClientAddress = ""; 
	
	identification_request_holder(String func_request_uri, Date func_date, String func_requestClientAddress){
		this.setRequestURI(func_request_uri); 
		this.setRequestTimeStamp(func_date.toString()); 
		this.setRequestClientAddress(func_requestClientAddress); 
	}
	
	Boolean setRequestURI(String func_request_uri) {
		returnObject temp_returnObject = new returnObject();  
		if(temp_returnObject.setObject(func_request_uri)) {
			this.requestURI = func_request_uri; 
			return true; 
		}else {
			return false; 
		}
		
	}
	Boolean setRequestTimeStamp(String func_requestTimeStamp) {
		returnObject temp_returnObject = new returnObject(); 
		if(temp_returnObject.setObject(func_requestTimeStamp)) {
			this.requestTimeStamp = func_requestTimeStamp;
			return true; 
		}else {
			return false; 
		}
	}
	Boolean setRequestClientAddress(String func_requestClientAddress) {
		returnObject temp_returnObject = new returnObject(); 
		if(temp_returnObject.setObject(func_requestClientAddress)) {
			this.requestClientAddress = func_requestClientAddress; 
			return true; 
		}else {
			return false; 
		}
	}
	
	String getRequest_ID_String() {
		String returnObject;
		returnObject = "[" + this.requestTimeStamp + "] - requestClientAddress -> [" + this.requestClientAddress + "] - requestURI -> [" + this.requestURI + "]"; 
		return returnObject; 
	}
}