/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 *
 * @author A661517
 */

public class MessageProcessor implements Closeable 
{
    private Test test;
    private final LRCCalculator lrcCalculator = new LRCCalculator();
    private final SerialCommHandler serialHandler;
    private byte lrcByte;
    private String message;
    private byte[] completeMessage;

    public MessageProcessor(Test test, SerialCommHandler serialHandler) 
    {
        this.test = test;
        this.serialHandler = serialHandler;
    }
    
    public MessageProcessor(SerialCommHandler serialHandler)
    {
        this.serialHandler = serialHandler;
    }

    public String readSendMessage()
    {
        Message[] tempMessage;
        tempMessage = test.getMessages();

        for (int i = 0; i < tempMessage.length; i++)
        {
            if (tempMessage[i].getDirection().equals("send"))
            {
                return tempMessage[i].getContent();
            }
        }

        return null;
    }

    public void replace()
    {
        String tempText = null;
        if ((tempText = readSendMessage()) == null) 
        {
            System.err.println("No message for sending found! ");
        }

        tempText = tempText.replace("<STX>", "\u0002");
        tempText = tempText.replace("<FS>", "\u001C");
        tempText = tempText.replace("<ETX>", "\u0003");
        tempText = tempText.replace("<ACK>", "\u0006");
        tempText = tempText.replace("<NAK>", "\u0015");
        
        message = tempText;
    }

    public void getLRCByte()
    {
        this.replace();
        completeMessage = message.getBytes();
        lrcByte = lrcCalculator.calculate(completeMessage, 1, message.getBytes().length - 1);
    }

    public void encodeMessage() 
    {
        this.getLRCByte();
        String test = new String(new byte[]{lrcByte});
        message = message.replace("<LRC>", test);
        completeMessage = message.getBytes();
    }
    
    public String getDecodedMessage(byte[] bytes)
    {
        String messageReceived = null;
        try {
            messageReceived = new String(bytes, "ASCII");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        messageReceived = messageReceived.replace("\u0002", "<STX>");
        messageReceived = messageReceived.replace("\u0003", "<ETX>");
        messageReceived = messageReceived.replace("\u001c", "<FS>");
        messageReceived = messageReceived.replace("\u0006", "<ACK>");
        messageReceived = messageReceived.replace("\u0015", "<NAK>");
        
        return messageReceived;
    }

    public void sendEncodedMessage() 
    {
        this.encodeMessage();
        try {
            serialHandler.establishConnection();
            try {
                serialHandler.write(completeMessage);
            } catch (IOException ex) {
                System.err.println("Error while writing bytes to buffer: " + ex);
            }
        } catch (SerialPortException ex) {
            Logger.getLogger(MessageProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] readMessage() throws SerialPortTimeoutException
    {
        byte[] collectedMessage = null;
        try 
        {
            collectedMessage = serialHandler.read(5000);
        } catch (IOException ex) 
        {
            Logger.getLogger(MessageProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return collectedMessage;
    }
    
    public void sendMessage(Message message)
    {
        String messageContent = message.getContent();
        byte[] bytesForSending = null;
        byte lrcByte;
       
        // Replace
        messageContent = messageContent.replace("<STX>", "\u0002");
        messageContent = messageContent.replace("<FS>", "\u001C");
        messageContent = messageContent.replace("<ETX>", "\u0003");
        
        // Calculate LRC Byte
        bytesForSending = messageContent.getBytes();
        lrcByte = lrcCalculator.calculate(bytesForSending, 1, messageContent.getBytes().length - 1);
        
        // Encode message
        String test = new String(new byte[]{lrcByte});
        messageContent = messageContent.replace("<LRC>", test);
        bytesForSending = messageContent.getBytes();
        
        // Send bytes to Serial PORT
        try {
           serialHandler.establishConnection();
           try {
               serialHandler.write(bytesForSending);
           } catch (IOException ex) {
               System.err.println("Error while writing bytes to buffer: " + ex);
           }
       } catch (SerialPortException ex) {
           Logger.getLogger(MessageProcessor.class.getName()).log(Level.SEVERE, null, ex);
       } 
    }
    
    public byte[] getCompleteMessage() 
    {
        return completeMessage;
    }

    @Override
    public void close() throws IOException {
        try {
            serialHandler.disconnect();
        } catch (IOException ex) {
            System.err.println("Error while closing serial communication: " + ex);
        }
    }
}
