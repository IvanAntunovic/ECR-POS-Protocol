package com.mycompany.protocol;


import com.mycompany.protocol.TestReader;
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
public class TestController 
{
    private TestReader testReader;
    private Test[] tests;
    
    public TestController(TestReader newTestReader)
    {
        testReader = newTestReader;
    }
    
    public Test getTest(String id)
    {
        if (tests == null)
        {
            tests = getTests();
        }
            
        
        for (int i = 0; i < tests.length; i++) 
        {
            if ((Integer.toString( tests[i].getId())).equals(id))
            {
                return tests[i];
            }
        }
        return null;
    }
    
    public Test[] getTests()
    {
        if (tests == null)
        {
            tests = testReader.readAll();
        }
            
        return tests;
    }
    
}
