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
import jssc.SerialPort;
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
        String filePath = null;
        String portName = null;
        
        if (args.length < 2)
        {
            Scanner input = new Scanner(System.in);
            System.out.print("Please enter path to your ini file: ");
            filePath = input.nextLine();
            System.out.print("Running with default communications port COM1.\n");
            portName = "COM1";
        }
        else
        {
            filePath = args[0];
            portName = args[1];
        }
        
        File file = null;
        Ini ini = null;
        
        file = new File(filePath);
        // C:\\Users\\a661517\\Documents\\POS\\testFile.ini
        //file = new File("C:\\Users\\a661517\\Documents\\POS\\testFile\\testFile.ini");
        
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
        
        SerialPort serial = new SerialPort(portName);
        TestRunner testRunner = new TestRunner(new SerialCommHandler(serial));
        TestController controller = new TestController(new TestReader(ini));
        
        ConsoleGUI consoleGUI = new ConsoleGUI(testRunner, controller);
        try {
            consoleGUI.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
}
    
  


