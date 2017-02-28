package com.mycompany.protocol;

import org.ini4j.Ini;

public class TestReader 
{
    private Ini ini;
    
    public TestReader(Ini newIni)
    {
        this.ini = newIni;
    }
    
    public Test read(int index)
    {
        Test test = new Test();
        String testIndex = "Test" + Integer.toString(index);
        
        test.setId(ini.get(testIndex, "id", int.class));
        test.setDescription(ini.get(testIndex, "desc", String.class));
        test.setMessageCount(ini.get(testIndex, "Message.count", int.class));
        
        Message[] messages = new Message[test.getMessageCount()];
        
        for (int i = 0; i < test.getMessageCount(); i++)   
        {
            String directionMessageKey;
            String contentMessageKey;
            directionMessageKey = "Message[" + Integer.toString(i) + "].direction";
            contentMessageKey = "Message[" + Integer.toString(i) + "].content";
            messages[i] = new Message();
            messages[i].setDirection(ini.get(testIndex, directionMessageKey, String.class));
            messages[i].setContent(ini.get(testIndex, contentMessageKey, String.class));
        }
        
        test.setMessages(messages);
        
        return test;
    }
      
    public Test[] readAll()
    {
        int numberOfTests = ini.get("NumberOfTests", "Test.number", int.class);
        Test[] tests = new Test[numberOfTests];
        
        for (int i = 1; i < numberOfTests + 1; i++) 
        {
            tests[i - 1] = this.read(i);
        }

        return tests;
    }
    
}
