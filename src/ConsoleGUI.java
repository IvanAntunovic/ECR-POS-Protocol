/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

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

    public void start() 
    {
        while(true) {
            System.out.print("Enter ID: ");
            int id = input.nextInt();
            
            Test selectedTest = controller.getTest(Integer.toString(id));
            if(null == selectedTest) 
            {
                System.out.println(String.format("Invalid ID: %d, please enter another.", id));
            }
            else
            {
                int runTestResult = testRunner.runTest(selectedTest);
                System.out.println(String.format("Test run finished with result %d", runTestResult));
                //break;
            }
        }
    }
}
