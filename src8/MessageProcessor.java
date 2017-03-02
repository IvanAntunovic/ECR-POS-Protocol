/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 *
 * @author A661517
 */
public class MessageProcessor implements Closeable {

    private Test test;
    private final LRCCalculator lrcCalculator = new LRCCalculator();
    private final SerialCommHandler serialHandler;
    private byte lrcByte;
    private String message;

    public MessageProcessor(SerialCommHandler serialHandler)
    {
        this.serialHandler = serialHandler;
    }

    public String readSendMessage()
    {
        Message[] tempMessage;
        tempMessage = test.getMessages();

        for (int i = 0; i < tempMessage.length; i++) {
            if (tempMessage[i].getDirection().equals("send")) {
                return tempMessage[i].getContent();
            }
        }

        return null;
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

    public byte[] readMessage(int receiveCounter) throws SerialPortTimeoutException 
    {
        byte[] collectedMessage = null;
        try {
            collectedMessage = serialHandler.read(120000, receiveCounter);
        } catch (IOException ex) {
            Logger.getLogger(MessageProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return collectedMessage;
    }

    public void sendMessage(Message message, int sendCounter) {
        String messageContent = message.getContent();
        byte[] bytesForSending = null;
        byte lrcByte;

        // Replace
        messageContent = messageContent.replace("<STX>", "\u0002");
        messageContent = messageContent.replace("<FS>", "\u001C");
        messageContent = messageContent.replace("<ETX>", "\u0003");
        messageContent = messageContent.replace("<RS>", "\u001E");
        messageContent = messageContent.replace("<US>", "\u001F");
        messageContent = messageContent.replace("<ACK>", "\u0006");
        messageContent = messageContent.replace("<NAK>", "\u0015");
        
        int lrcCommandPosition = messageContent.indexOf("<LRC>");

        //Calculate LRC        
        bytesForSending = messageContent.getBytes();
        lrcByte = lrcCalculator.calculate(bytesForSending, 1, lrcCommandPosition - 1);

        // Encode message
        String lrcString = new String(new byte[]{lrcByte});
        messageContent = messageContent.replace("<LRC>", lrcString);
        bytesForSending = messageContent.getBytes();

        // Send bytes to Serial PORT
        try {
            serialHandler.write(bytesForSending, sendCounter);
        } catch (IOException ex) {
            System.err.println("Error while writing bytes to buffer: " + ex);
        }

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
