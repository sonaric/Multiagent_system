/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aslcore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Stanislav
 */
public class MessageList implements Serializable{
    private ArrayList<MessageData> messagesINFO;
    private ArrayList<MessageData> messagesDATA;
    private ArrayList<MessageData> messagesQUERY;
    private ArrayList<MessageData> messagesREQUEST;
    private ArrayList<MessageData> messagesONTOLOGY;
    
    private static volatile MessageList instance;
    
    private MessageList(){}
    
    public static MessageList getInstance(){
        if(instance == null)
            synchronized (MessageList.class) {
                if(instance == null)
                    instance = new MessageList();
            }
        return instance;
    }

    public ArrayList<MessageData> getMessagesINFO() {
        return messagesINFO;
    }

    public ArrayList<MessageData> getMessagesDATA() {
        return messagesDATA;
    }

    public ArrayList<MessageData> getMessagesQUERY() {
        return messagesQUERY;
    }

    public ArrayList<MessageData> getMessagesREQUEST() {
        return messagesREQUEST;
    }

    public ArrayList<MessageData> getMessagesONTOLOGY() {
        return messagesONTOLOGY;
    }
    
    
}
