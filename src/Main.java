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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
        TestReader testReader = null;
        try {
            testReader = createReader();
        } catch(IOException ex) {
            System.out.println("Failed to start system!");
            return;
        }
        
        ConsoleGUI console = new ConsoleGUI(
                                new TestRunner(),
                                new TestController(testReader));
        
        // run
        console.start();*/
        test();
        
        
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
    
    
    public static String replace()
    {
        String tempText = null;
        tempText = "<STX><FS>21156<FS>1006<FS>206<FS>70565160<FS>10701<FS>01092018<ETX><LRC>";
        
        tempText = tempText.replaceAll("<STX>", "2");
        tempText = tempText.replaceAll("<FS>", "1C");
        tempText = tempText.replaceAll("<ETX>", "3");
        tempText = tempText.replaceAll("<LRC>", "X");
        
        System.out.println(tempText);
    }
    
    public static byte getLRCByte()
    {
        LRCCalculator lrcCalculator = new LRCCalculator();
        String messageContent = replace();
        byte[] completeMessage = messageContent.getBytes();
        return lrcCalculator.calculate(completeMessage, 1, messageContent.getBytes().length - 1);
    }
    
    public static void prepareMessage()
    {
        getLRCByte();
       
        if (completeMessage[completeMessage.length - 1] == 'X')
        {
            completeMessage[completeMessage.length - 1] = lrcByte;
        }

    }
    
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


