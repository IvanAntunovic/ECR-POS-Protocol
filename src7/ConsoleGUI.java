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
public class ConsoleGUI {

    private final TestRunner testRunner;
    private final TestController controller;
    private final Scanner input = new Scanner(System.in);

    public ConsoleGUI(TestRunner testRunner, TestController controller) {
        this.testRunner = testRunner;
        this.controller = controller;
    }

    public void start() throws IOException 
    {
        int testCounter = 0;
        String keyboardInput = "";
        Test[] tests = this.controller.getTests();

        do {
            for (Test test : tests) {
                System.out.println(String.format("#Test %d", ++testCounter));
                System.out.print("ID: ");
                System.out.print(test.getId());
                System.out.print("  Description: ");
                System.out.println(test.getDescription());

                if (testCounter % 24 == 0) 
                {
                    System.out.println("Press ENTER for next tests to be printed...");
                    keyboardInput = input.nextLine();
                    //keyboardInput = "43";
                    if (keyboardInput.isEmpty()) 
                    {
                        continue;
                    }
                    this.clearConsoleScreen();
                    Test selectedTest = controller.getTest(keyboardInput);
                    int[] runTestResult = testRunner.runTest(selectedTest);

                    if (runTestResult[0] == 1)
                    {
                        System.out.println("Test run has finished successfully with result TRUE.");
                    } else 
                    {
                        System.out.print(String.format("Test run has not finished successfully with ID: %s", selectedTest.getId()));
                        System.out.printf(" at message[%d].\n", runTestResult[1]);
                    }
                    System.out.println("Press ENTER for next tests to be printed...");
                    keyboardInput = input.nextLine();
                    //keyboardInput = "1";
                    if (keyboardInput.isEmpty()) 
                    {
                            break;
                    }
                    
                }
            }
//            keyboardInput = input.nextLine();
//            if (keyboardInput.isEmpty()) 
//            {
//                continue;
//            }

        } while (!keyboardInput.equals("exit"));

        System.out.println("No more tests.");

    }

    public void clearConsoleScreen()
    {
        for (int i = 0; i < 50; i++)
            System.out.println("\n");
    }
}
