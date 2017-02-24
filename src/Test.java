package com.mycompany.protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author A661517
 */
public class Test
{
    private int id;
    private String description;
    private Message[] messages;
    private int messageCount;
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        
        Test newTest = (Test)obj;
        
        if (this.id != newTest.id && !Objects.equals(this.description, newTest.description) && 
                !Arrays.deepEquals(this.messages, newTest.messages) && this.messageCount != newTest.messageCount)
        {
            return false;
        }
        return true;
    }
    
    public boolean deepEquals(Object[] obj, Test[] test)
    {
        Test[] newTests = (Test[])obj;
        for (int i = 0; i < newTests.length; i++) 
        {
            if(!newTests[i].equals(test[i]))
                return false;
        }
        return true;
    }
    
    public int getId() 
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getMessageCount() 
    {
        return messageCount;
    }

    public void setMessageCount(int messageCount) 
    {
        this.messageCount = messageCount;
    }

    public Message[] getMessages()
    {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }
    
    
}
