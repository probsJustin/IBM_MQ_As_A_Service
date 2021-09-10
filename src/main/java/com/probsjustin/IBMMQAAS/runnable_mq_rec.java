package com.probsjustin.IBMMQAAS;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSession;
import com.ibm.msg.client.wmq.WMQConstants;

public class runnable_mq_rec {

    logger_internal instance_logger_internal = new logger_internal();
    String targetHostName = ""; 
    String targetPort = ""; 
    String targetQueueManager = ""; 
    String targetChannel = ""; 
    String targetQueue = ""; 
    String targetUsername = null; 
    String targetPassword = null; 

    runnable_mq_rec(String func_targetHostName, String func_targetPort, String func_targetQueueManager, String func_targetChannel ) {
    	this.targetHostName = func_targetHostName; 
    	this.targetPort = func_targetPort; 
    	this.targetQueueManager = func_targetQueueManager; 
    	this.targetChannel = func_targetChannel; 
    }
    
    runnable_mq_rec(String func_targetHostName, String func_targetPort, String func_targetQueueManager, String func_targetChannel, String func_targetUsername, String func_targetPassword) {
    	this.targetHostName = func_targetHostName; 
    	this.targetPort = func_targetPort; 
    	this.targetQueueManager = func_targetQueueManager; 
    	this.targetChannel = func_targetChannel; 
    	this.targetUsername = func_targetUsername;
    	this.targetPassword = func_targetPassword; 
    }

    MQQueueConnectionFactory addInstanceVariablesToMQCONN_Factory(MQQueueConnectionFactory func_instanceConnectionFactory) throws NumberFormatException, JMSException {
    	func_instanceConnectionFactory.setHostName(this.targetHostName);
    	func_instanceConnectionFactory.setPort(Integer.parseInt(this.targetPort));
    	func_instanceConnectionFactory.setQueueManager(this.targetQueueManager);
    	func_instanceConnectionFactory.setChannel(this.targetChannel);
		return func_instanceConnectionFactory;
    }
    
    void run() {

        try {
            MQQueueConnectionFactory instanceConnectionFactory = new MQQueueConnectionFactory();
            
            instanceConnectionFactory = addInstanceVariablesToMQCONN_Factory(instanceConnectionFactory);
            instanceConnectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            MQQueueConnection instnace_connection = null; 
            
            if(this.targetUsername != null && this.targetPassword != null) {
            	instnace_connection = (MQQueueConnection) instanceConnectionFactory.createQueueConnection(this.targetUsername, this.targetPassword);
            }else {
            	instnace_connection = (MQQueueConnection) instanceConnectionFactory.createQueueConnection();
            }
            
            MQQueueSession instance_session = (MQQueueSession) instnace_connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            MQQueue instnace_queue = (MQQueue) instance_session.createQueue(this.targetQueue);

            MQQueueReceiver instance_receiver = (MQQueueReceiver) instance_session.createReceiver(instnace_queue);

            instnace_connection.start();

            TextMessage receivedMessage = (TextMessage) instance_receiver.receive();
            System.out.println("Received message from Queue MyTestQueue: " + receivedMessage.getText());

            instance_receiver.close();
            instance_session.close();
            instnace_connection.close();
            System.out.println("Message Received OK.\n");
        } catch (JMSException jmsex) {
            System.out.println(jmsex);
            System.out.println("Message Receive Failure\n");
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Message Receive Failure\n");
        }
    }


}