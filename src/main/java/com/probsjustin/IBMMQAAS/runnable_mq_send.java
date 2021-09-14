package com.probsjustin.IBMMQAAS;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.ibm.mq.MQAsyncStatus;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class runnable_mq_send implements Runnable {

	
	logger_internal instance_logger_internal = new logger_internal(); 
    String targetHostName = ""; 
    String targetPort = ""; 
    String targetQueueManager = ""; 
    String targetChannel = ""; 
    String targetQueue = ""; 
    String targetUsername = null; 
    String targetPassword = null; 
    String targetMessage = ""; 
	
	runnable_mq_send(String func_targetHostName, String func_targetPort, String func_targetQueueManager, String func_targetChannel, String func_targetQueue, String func_targetUsername, String func_targetPassword, String func_targetMessage){
    	this.targetHostName = 		func_targetHostName; 
    	this.targetPort = 			func_targetPort; 
    	this.targetQueueManager = 	func_targetQueueManager; 
    	this.targetChannel = 		func_targetChannel; 
    	this.targetUsername = 		func_targetUsername;
    	this.targetPassword = 		func_targetPassword; 
    	this.targetMessage = 		func_targetMessage;
	}
	runnable_mq_send(String func_targetHostName, String func_targetPort, String func_targetQueueManager, String func_targetChannel, String func_targetQueue, String func_targetMessage){
    	this.targetHostName = 		func_targetHostName; 
    	this.targetPort = 			func_targetPort; 
    	this.targetQueueManager = 	func_targetQueueManager; 
    	this.targetChannel = 		func_targetChannel; 
    	this.targetMessage = 		func_targetMessage;
	}
	runnable_mq_send(Map<String, String> func_realParametersFromUser){
    	this.targetHostName = 		func_realParametersFromUser.get("hostName"); 
    	this.targetPort = 			func_realParametersFromUser.get("port"); 
    	this.targetQueueManager = 	func_realParametersFromUser.get("queuemanager"); 
    	this.targetChannel = 		func_realParametersFromUser.get("channel"); 
    	this.targetMessage = 		func_realParametersFromUser.get("message");
	}
	
	Hashtable<String, Object> setProperties(Hashtable<String, Object> func_hashTableNewInstance){
		func_hashTableNewInstance.put(MQConstants.CHANNEL_PROPERTY, this.targetChannel);
		func_hashTableNewInstance.put(MQConstants.PORT_PROPERTY, Integer.parseInt(targetPort));
		func_hashTableNewInstance.put(MQConstants.HOST_NAME_PROPERTY, this.targetHostName);
		return func_hashTableNewInstance;
	}
	
	public void run() {
		 // Create a connection to the queue manager
        Hashtable<String, Object> properties = setProperties(new Hashtable<String, Object>());

        MQQueueManager instance_queueManager = null;
        try {
        	instance_queueManager = new MQQueueManager(this.targetQueueManager, properties);

            // MQOO_OUTPUT = Open the queue to put messages. The queue is opened for use with subsequent MQPUT calls.
            // MQOO_INPUT_AS_Q_DEF = Open the queue to get messages using the queue-defined default.
            // The queue is opened for use with subsequent MQGET calls. The type of access is either
            // shared or exclusive, depending on the value of the DefInputOpenOption queue attribute.
            int instnace_openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INPUT_AS_Q_DEF;

            // creating destination
            MQQueue instance_MQQueue = instance_queueManager.accessQueue(this.targetQueueManager, instnace_openOptions);

            // specify the message options...
            MQPutMessageOptions instance_MQPutMessageOptions = new MQPutMessageOptions(); // default
            // MQPMO_ASYNC_RESPONSE = The MQPMO_ASYNC_RESPONSE option requests that an MQPUT or MQPUT1 operation
            // is completed without the application waiting for the queue manager to complete the call.
            // Using this option can improve messaging performance, particularly for applications using client bindings.
            instance_MQPutMessageOptions.options = MQConstants.MQPMO_ASYNC_RESPONSE;

            // create message
            MQMessage instance_MQMessage = new MQMessage();
            // MQFMT_STRING = The application message data can be either an SBCS string (single-byte character set),
            // or a DBCS string (double-byte character set). Messages of this format can be converted
            // if the MQGMO_CONVERT option is specified on the MQGET call.
            instance_MQMessage.format = MQConstants.MQFMT_STRING;
            instance_MQMessage.writeString(this.targetMessage);
            instance_MQQueue.put(instance_MQMessage, instance_MQPutMessageOptions);
            instance_MQQueue.close();

            MQAsyncStatus asyncStatus = instance_queueManager.getAsyncStatus();
            assertEquals(1, asyncStatus.putSuccessCount);
        } catch (MQException e) {
        	instance_logger_internal.error("Die Verbindung zum Message Broker mit den "
                    + "Eigenschaften {} und dem QueueManager {} konnte nicht hergestellt werden." + properties.toString() + this.targetQueueManager.toString() + e.toString());
        } catch (IOException e) {
        	instance_logger_internal.error("Fehler beim Schreiben der Message." + e.toString());
        } finally {
            try {
            	instance_queueManager.disconnect();
            } catch (MQException e) {
            	instance_logger_internal.error("Die Verbindung konnte nicht geschlossen werden." + e.toString());
            }
        }
	}
	
}
