/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author A661517
 */



public class MessageCollector
{
    private int etxIndex; 
    private int stxIndex;
    
    public MessageCollector()
    {
        etxIndex = 0;
    }
    
    public ArrayList<Byte> appendBytes(byte[] buffer, ArrayList<Byte> bufferList)
    {
        for (int i = 0; i < buffer.length; i++) 
        {
            bufferList.add(buffer[i]);
        }
        return bufferList;
    }
    
    public ArrayList<Byte> removeBytes(int index, ArrayList<Byte> bufferList)
    {
        if (bufferList.get(index) == null)
        {
            return null;
        }
        
        for (int i = bufferList.size(); i > index; i--)
        {
            bufferList.remove(i);
        }
        
        return bufferList;
    }
    
    public int getEtxIndex()
    {
        return etxIndex;
    }
    
    public boolean isSTX(ArrayList<Byte> bufferList)
    {
        for (int i = 0; i < bufferList.size(); i++)
        {
            if (bufferList.get(i) == 0x02)
                stxIndex = i;
                return true;
        }
        
        return false;
    }

    public boolean isETX(ArrayList<Byte> bufferList)
    {
        for (int i = 0; i < bufferList.size(); i++)
        {
            if (bufferList.get(i) == 0x03)
            {
                etxIndex = i;
                return true;
            }      
        }

        return false;
    }
    
   

    public byte[] createByteArray(ArrayList<Byte> buffer)
    {
        byte[] byteArray = null;
        
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream objOstream = new ObjectOutputStream(baos);
            
            objOstream.writeObject(buffer);
            byteArray = baos.toByteArray();
        }
        catch (Exception ex)
        {
            System.err.println("Problem in createByteArray" + ex);
        }
        
        return byteArray;
    }
}
