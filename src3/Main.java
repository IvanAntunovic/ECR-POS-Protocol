/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.plexus.digest.Hex;
import org.ini4j.Ini;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author A661517
 */
public class Main 
{
    public static void main(String[] args)
    {   /*
        // bootstrap
        Test test = new Test();
        SerialCommHandler serialComm = new SerialCommHandler("COM1");
        MessageProcessor messageDecoder = new MessageProcessor(test, serialComm);
        TestReader testReader = null;
        
        try {
            testReader = createReader();
        } catch(IOException ex) {
            System.out.println("Failed to start system!");
            return;
        }
        
        ConsoleGUI console = new ConsoleGUI(
                                new TestRunner(serialComm, messageDecoder),
                                new TestController(testReader));
        
        try {
            // run
            console.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        */
        //testTwo();
        testThree();
    }
    
    public static void test()
    {
        String tempText = null;
        tempText = "<STX><FS>21156<FS>1006<FS>206<FS>70565160<FS>10701<FS>01092018<ETX><LRC>";
        
        tempText = tempText.replaceAll("<STX>", "2");
        tempText = tempText.replaceAll("<FS>", "1C");
        tempText = tempText.replaceAll("<ETX>", "3");
        tempText = tempText.replaceAll("<LRC>", "X");
        System.out.println(tempText);
        tempText = tempText.replaceAll("X", "2");
        System.out.println(tempText);
    }
    
    public static void testTwo() 
    {
        /*
        String tempText = Hex.encode(bytes);
        System.out.println(tempText);
        
        tempText = tempText.replaceAll("1c", "<FS>");
        tempText = tempText.replaceAll("2", "<STX>");
        tempText = tempText.replaceAll("3", "<ETX>");
        */
        
        byte[] bytes = {0x02, 0x1C, 0x30, 0x1C, 0x31, 0x1C, 0x32, 0x1C, 0x33, 0x03, 0x34};
        

        StringBuilder sb = new StringBuilder(bytes.length);
        for (int i = 0; i < bytes.length; ++i)
        {
            if (bytes[i] < 0) throw new IllegalArgumentException();
            {
                if (bytes[i] == 0x02)
                    sb.append("<STX>");
                if (bytes[i] == 0x1C)
                    sb.append("<FS>");
                if (bytes[i] == 0x03)
                    sb.append("<ETX>");
                if (bytes[i] >= '0' && bytes[i] <= '9')
                {
                    if (bytes[i] == '0')
                        sb.append("0");
                    if (bytes[i] == '1')
                        sb.append("1");
                    if (bytes[i] == '2')
                        sb.append("2");
                    if (bytes[i] == '3')
                        sb.append("3");
                    if (bytes[i] == '4')
                        sb.append("4");
                    if (bytes[i] == '5')
                        sb.append("6");
                    if (bytes[i] == '6')
                        sb.append("7");
                    if (bytes[i] == '8')
                        sb.append("8");
                    if (bytes[i] == '9')
                        sb.append("9");
                }
            }
        }
        String receivedMessage = sb.toString();
        System.out.println(receivedMessage);
        
        
    }
    
    public static void testThree()
    {
        byte[] bytes = {0x02, 0x1C, 0x30, 0x1C, 0x31, 0x1C, 0x32, 0x1C, 0x33, 0x03, 0x34};
        String messageReceived = null;
        try {
            messageReceived = new String(bytes, "ASCII");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(messageReceived);
        messageReceived = messageReceived.replace("\u0002", "<STX>");
        messageReceived = messageReceived.replace("\u0003", "<ETX>");
        messageReceived = messageReceived.replace("\u001c", "<FS>");
        System.out.println(messageReceived);
    }
    
    public static String replace()
    {
        String tempText = null;
        tempText = "<STX><FS>21156<FS>1006<FS>206<FS>70565160<FS>10701<FS>01092018<ETX><LRC>";
        
        tempText = tempText.replaceAll("<STX>", "2");
        tempText = tempText.replaceAll("<FS>", "1C");
        tempText = tempText.replaceAll("<ETX>", "3");
        tempText = tempText.replaceAll("<LRC>", "X");
        
        System.out.println(tempText);
        return null;
    }
    
    public static byte getLRCByte()
    {
        LRCCalculator lrcCalculator = new LRCCalculator();
        String messageContent = replace();
        byte[] completeMessage = messageContent.getBytes();
        return lrcCalculator.calculate(completeMessage, 1, messageContent.getBytes().length - 1);
    }
    /*
    public static void prepareMessage()
    {
        getLRCByte();
       
        if (completeMessage[completeMessage.length - 1] == 'X')
        {
            completeMessage[completeMessage.length - 1] = lrcByte;
        }

    }
    */
    private static TestReader createReader() throws IOException {
        File file = null;
        try {
            file = new File("C:\\Users\\a661517\\Documents\\POS\\testFile.ini");
        } catch (Exception e) 
        {
            System.err.println("ERROR opening file: " + e);
        }
        
        Ini ini = null;
        
        try {
            ini = new Ini(file);
        } catch (IOException ex)
        {
            System.err.println("ERROR: " + ex);
        }
        
        try {
            ini.load(new FileReader(file));
        } catch (FileNotFoundException ex) 
        {
           System.err.println("ERROR: " + ex);
        }
        
        return new TestReader(ini);
    }
}


