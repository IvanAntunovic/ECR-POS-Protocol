/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author A661517
 */
public class ConsoleGUI {

    private final TestRunner testRunner;
    private final TestController controller;
    private final Scanner input = new Scanner(System.in);

    public ConsoleGUI(TestRunner testRunner, TestController controller) {
        this.testRunner = testRunner;
        this.controller = controller;
    }

    public void start() throws IOException {
        int testCounter = 0;
        int currentPageNum = 0;
        final int pageSize = 24;
        String keyboardInput = "";
        Test[] tests = this.controller.getTests();

        do {
            
            for (int i = currentPageNum * pageSize; i < (currentPageNum + 1) * pageSize; ++i) {
                if (tests.length > i) {
                    System.out.println(String.format("#Test %d", i + 1));
                    System.out.print("ID: ");
                    System.out.print(tests[i].getId());
                    System.out.print("  Description: ");
                    System.out.println(tests[i].getDescription());
                }
            }
               
            System.out.println("");
            System.out.println("To exit application enter the word 'exit'.");
            System.out.println("Enter test ID to run a wanted test.");
            System.out.println("Press ENTER for next tests to be printed.");

            keyboardInput = input.nextLine();
            if (keyboardInput.isEmpty()) {
                ++currentPageNum;
                if (tests.length <= currentPageNum * pageSize) {
                    currentPageNum = 0;
                }
            }else if (keyboardInput.equals("exit")){
            
                break;
            }else
            {
                
                Test selectedTest = controller.getTest(keyboardInput);
                if (selectedTest != null)
                {
                    this.clearScreen();
                    int[] runTestResult = testRunner.runTest(selectedTest);

                    if (runTestResult[0] == 1)
                    {
                        System.out.println("Test run has finished successfully with result TRUE.");
                    } 
                    else 
                    {
                        System.out.print(String.format("Test run has not finished successfully with ID: %s", selectedTest.getId()));
                        System.out.printf(" at message[%d].\n", runTestResult[1]);
                    }
                }
                else
                {
                    System.out.println("Selected test does not exist!");
                    System.out.println("Press ENTER to continue...");

                    keyboardInput = input.nextLine();
                   
                }  
            }
            
        } while (!keyboardInput.equals("exit"));

        System.out.println("No more tests.");

    }

    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println("\n");
        }
    }
}
