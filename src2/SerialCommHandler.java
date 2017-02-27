/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 *
 * @author A661517
 */
public class SerialCommHandler
{
    private SerialPort serialPort;
    private String comPort;

    public SerialCommHandler(String comPort)
    {
        this.comPort = comPort;
    }

    public void establishConnection() throws SerialPortException
    {
        try {
            serialPort = new SerialPort(comPort);
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
        } catch (SerialPortException ex) {
            System.err.println(ex);
        }
    }

    public void write(byte[] data) throws IOException 
    {
        try {
            serialPort.writeBytes(data);
        } catch (SerialPortException ex) {
            throw new IOException("Error writing data to serial port.", ex);
        }
    }

    public byte[] read(int timeOut) throws IOException, SerialPortTimeoutException 
    {
        MessageCollector messageCollector = new MessageCollector();
        byte[] collectedMessage = null;

        do {
            try {
                byte[] tempBuffer;
                tempBuffer = serialPort.readBytes(1, timeOut);

                messageCollector.addBytes(tempBuffer);
                collectedMessage = messageCollector.getExtractedMessage();
                
            } catch (SerialPortException ex) {
                System.err.println("ERROR reading bytes from COM port: " + ex);
                return null;
             }

         } while (collectedMessage == null);

        return collectedMessage;
    }
    /*
    public String read() throws IOException {
        String tempBuffer = null;
        try {
            //buffer = serialPortTwo.readBytes(10);
            tempBuffer = serialPort.readString();
        } catch (SerialPortException e) {
            System.err.println("ERROR reading bytes from COM port: ");
        }
        if (tempBuffer != null) 
        {
            return tempBuffer;
        }

        return null;

    }
    */
    public void disconnect() throws IOException {
        try {
            if (serialPort != null && serialPort.isOpened()) {
                serialPort.closePort();
            }
        } catch (SerialPortException ex) {
            throw new IOException("Error closing serial port connection.", ex);
        }
    }

}
