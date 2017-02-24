/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 *
 * @author A661517
 */
public class MessageDecoder implements Closeable {

    private Test test;
    private final LRCCalculator lrcCalculator = new LRCCalculator();
    private final SerialCommHandler serialHandler;
    private byte lrcByte;
    private String message;
    private byte[] completeMessage;

    public MessageDecoder(Test test, SerialCommHandler serialHandler) {
        this.test = test;
        this.serialHandler = serialHandler;
    }

    public String readSendMessage() {
        Message[] tempMessage;
        tempMessage = test.getMessages();

        for (int i = 0; i < tempMessage.length; i++) {
            if (tempMessage[i].getDirection().equals("send")) {
                return tempMessage[i].getContent();
            }
        }

        return null;
    }

    public void replace() {
        String tempText = null;
        if ((tempText = readSendMessage()) == null) {
            System.err.println("No message for sending found! ");
        }

        tempText = tempText.replace("<STX>", "\u0002");
        tempText = tempText.replace("<FS>", "\u001C");
        tempText = tempText.replace("<ETX>", "\u0003");

        message = tempText;
    }

    public void getLRCByte()
    {
        this.replace();
        completeMessage = message.getBytes();
        lrcByte = lrcCalculator.calculate(completeMessage, 1, message.getBytes().length - 1);
    }

    public void prepareMessage() 
    {
        this.getLRCByte();
        String test = new String(new byte[]{lrcByte});
        message = message.replace("<LRC>", test);
        completeMessage = message.getBytes();
    }

    public void sendMessageContent() {
        this.prepareMessage();
        try {
            serialHandler.establishConnection();
            try {
                serialHandler.write(completeMessage);
            } catch (IOException ex) {
                System.err.println("Error while writing bytes to buffer: " + ex);
            }
        } catch (SerialPortException ex) {
            Logger.getLogger(MessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] read() throws SerialPortTimeoutException
    {
        byte[] tempRead = null;
        try 
        {
            tempRead = serialHandler.read(1, 5000);
        } catch (IOException ex) 
        {
            Logger.getLogger(MessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tempRead;
    }

    public byte[] getCompleteMessage() {
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
