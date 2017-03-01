package com.mycompany.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageCollector
{
    private ArrayList<Byte> messageBuffer;

    public MessageCollector()
    {
        messageBuffer = new ArrayList<Byte>();
    }

    public void addBytes(byte[] bytes) 
    {
        for (int i = 0; i < bytes.length; ++i)
        {
            messageBuffer.add(bytes[i]);
        }
    }

    // RX message 
    public byte[] getExtractedMessage()
    {
        byte[] message = null;

        //Check if there is somenthing in the internal buffer ...
        if (this.messageBuffer.size() != 0)
        {
            if (this.messageBuffer.get(0) == 0x02) {
                //Start of the message, look for ETX and LRC
                for (int i = 1; i < this.messageBuffer.size(); ++i) {
                    if (this.messageBuffer.get(i) == 0x03) //Imamo ETX
                    {
                        //check if there is LRC 'i + 2' because size is always bigger for +1 compared to elements in messageBuffer since elements start at index 0
                        if (i + 2 <= messageBuffer.size())
                        {
                            //popuni ... message
                            message = createByteArray(messageBuffer);
                            //remove copied contents from message buffer for next receive
                            messageBuffer.clear();
                        }
                        else
                        {
                            return null;
                        }  
                    }
                }  
            } 
            else 
            {
                message = new byte[1];
                message[0] = this.messageBuffer.get(0);
                this.messageBuffer.remove(0);
            }
        }

        return message;
    }
    
    public byte[] createByteArray(ArrayList<Byte> buffer) 
    {
        byte[] byteArray = new byte[buffer.size()];
        
        for (int i = 0; i < buffer.size(); i++)
        {
            byteArray[i] = buffer.get(i).byteValue();       
        }
        
        return byteArray;
  
    }
    
}
