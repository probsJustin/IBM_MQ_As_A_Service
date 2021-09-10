package com.probsjustin.IBMMQAAS;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class servlet_mq_send
 */
@WebServlet("/servlet_mq_send")
public class servlet_mq_send extends HttpServlet {
	private static final long serialVersionUID = 1L;
	logger_internal instance_logger_internal = new logger_internal(); 

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_mq_send() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		identification_request_holder temp_identification_request_holder = new identification_request_holder(request.getRequestURI(), new Date() , request.getRemoteAddr()); 
		instance_logger_internal.debug(temp_identification_request_holder.getRequest_ID_String() + " Servlet Recieved Incoming Request");
		controller_mq_send inst_controller_mq_send = new controller_mq_send(request, response);
		response = inst_controller_mq_send.controller(temp_identification_request_holder); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
