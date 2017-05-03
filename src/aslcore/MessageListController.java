/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aslcore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 *
 * @author Stanislav
 */
public class MessageListController {
    private MessageList messages = MessageList.getInstance();
    
    void save() throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("message_data.dat"));
        objectOutputStream.writeObject(messages);
    }
    void load() throws IOException, ClassNotFoundException{
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("message_data.dat"));
        messages = (MessageList) objectInputStream.readObject();
    }
}
