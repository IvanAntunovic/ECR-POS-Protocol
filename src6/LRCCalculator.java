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
public class LRCCalculator 
{

    public byte calculate(String message) 
    {
        return calculate(message.getBytes(), 0, message.length());
    }
    
    public byte calculate(byte[] message) 
    {
        return calculate(message, 0, message.length);
    }

    public byte calculate(byte[] message, int offset, int length)
    {
        byte LRC = 0;
        for (int i = offset; i < offset + length; i++) 
        {
            LRC ^= message[i];
        }

        return LRC;
    }
}
