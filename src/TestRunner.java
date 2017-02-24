/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

/**
 *
 * @author A661517
 */
public class TestRunner
{
    final public MessageDecoder messageDecoer;
    final public SerialCommHandler serialComm;

    public TestRunner(MessageDecoder messageDecoer, SerialCommHandler serialComm)
    {
        this.messageDecoer = messageDecoer;
        this.serialComm = serialComm;
    }
    
    
    public int runTest(Test test)
    {
        System.out.println(String.format("Running test with ID: %d", test.getId()));
        return 0;
    }
}
