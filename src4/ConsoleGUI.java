/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author A661517
 */
public class ConsoleGUI
{
    private final TestRunner testRunner;
    private final TestController controller;
    private final Scanner input = new Scanner(System.in);

    public ConsoleGUI(TestRunner testRunner, TestController controller) 
    {
        this.testRunner = testRunner;
        this.controller = controller;
    }
    
    public void start() throws IOException
    {
        int count = 0;
        String press = null;
        Test[] tests = this.controller.getTests();
        
        for (Test test : tests)
        {
            System.out.print("ID: ");
            System.out.print(test.getId());
            System.out.print("  Description: ");
            System.out.println(test.getDescription());
            count++;
            
            if(count % 2 == 0)
            {
                System.out.println("Press ENTER for next tests to printed...");
                
                //press = input.nextLine();
                /*
                if(press.isEmpty())
                {
                    continue;
                } 
                */
                //Test selectedTest = controller.getTest(press);
                Test selectedTest = controller.getTest("43");

                int[] runTestResult = testRunner.runTest(selectedTest);
                
                if (runTestResult[0] == 1)
                {
                    System.out.println("Test run has finished successfully with result TRUE.");
                }
                else
                {
                    System.out.println(String.format("Test run has not finished successfully with ID: %d", test.getId()) );
                    System.out.println(String.format("Test with %d ID has stopped working ", test));
                    System.out.printf("at message[%d].", runTestResult[1]);
                }
                 
                break;
            }
        }
        System.out.println("No more tests.");
    }
} 
