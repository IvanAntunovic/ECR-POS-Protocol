/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 *
 * @author A661517
 */
public class TestRunner
{
    final public SerialCommHandler serialComm;

    public TestRunner(SerialCommHandler serialComm)
    {
        this.serialComm = serialComm;
    }

    public int[] runTest(Test test)
    {
        byte[] receivedMessage = null;
        String decodedMessage = null;
        boolean isReceivedMsgFault = false;
        int[] testResult = new int[2];
        Message[] messagesFromFile = null;
        
        // Read all messages from the Test
        messagesFromFile = test.getMessages();
        
        System.out.println(String.format("Running test with ID: %s", test.getId()));
        
        MessageCollector messageCollector = new MessageCollector();
        try {
            serialComm.establishConnection();
        } catch (SerialPortException ex) {
            Logger.getLogger(TestRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessageProcessor messageProcessor = new MessageProcessor(serialComm);
        
        try
        {     
            // Do while all messages from test are not SENT
            for (int i = 0; i < messagesFromFile.length && !isReceivedMsgFault; i++)
            {
                // Check message direction
                if (messagesFromFile[i].getDirection().equals("send"))
                {
                   // Send message to SERIAL PORT
                   messageProcessor.sendMessage(messagesFromFile[i], i); 
                }
                else if (messagesFromFile[i].getDirection().equals("receive"))
                {
                    try {
                        // Read bytes from SERIAL PORT
                        receivedMessage = messageProcessor.readMessage(i);

                    } catch (SerialPortTimeoutException ex)
                    {
                        Logger.getLogger(TestRunner.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // Get bytes that are read from Serial Port into decoded human readable message like the one in INI file
                    decodedMessage = messageProcessor.getDecodedMessage(receivedMessage);

                    // Compare received MESSAGE and MESSAGE read from File  
                    if (!decodedMessage.matches(messagesFromFile[i].getContent()))
                    {
                        // TEST IS NOT SUCCESSFUL
                        isReceivedMsgFault = true;
                        // FLAG TO GUI THAT TEST FAILED WITH ID AT MESSAGE[i]
                        testResult[0] = 0;
                        testResult[1] = i;
                        return testResult;
                    }
                }
            }
        } finally {
            // Close Serial communication PORT
            try {
                serialComm.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(TestRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        testResult[0] = 1;
        return testResult;
    }
    
    public void getTestFromPort(int receiveCounter)
    {
        byte[] bytesRead = null;
        try {
            bytesRead = serialComm.read(30000, receiveCounter);
        } catch (IOException ex) {
            Logger.getLogger(TestRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortTimeoutException ex) {
            Logger.getLogger(TestRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
