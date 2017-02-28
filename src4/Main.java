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
    {   
        testPravi();
    }
    
    public static void testPravi()
    {
        File file = null;
        Ini ini = null;
        file = new File("C:\\Users\\a661517\\Documents\\POS\\testFile.ini");
        
        try {
            ini = new Ini(file);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ini.load(new FileReader(file));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        TestRunner testRunner = new TestRunner(new SerialCommHandler("COM1"));
        TestController controller = new TestController(new TestReader(ini));
        
        ConsoleGUI consoleGUI = new ConsoleGUI(testRunner, controller);
        try {
            consoleGUI.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void test()
    {
        /*
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
    }
}




