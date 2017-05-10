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
    private final ArrayList<MessageData> messagesINFO = new ArrayList<>();
    private final ArrayList<MessageData> messagesDATA = new ArrayList<>();
    private final ArrayList<MessageData> messagesQUERY = new ArrayList<>();
    private final ArrayList<MessageData> messagesREQUEST = new ArrayList<>();
    private final ArrayList<MessageData> messagesONTOLOGY = new ArrayList<>();
    private final ArrayList<MessageData> messagesAUTHORIZATION = new ArrayList<>();
    
    private static volatile MessageList instance;
    
    private MessageList(){}

    public ArrayList<MessageData> getMessagesAUTHORIZATION() {
        return messagesAUTHORIZATION;
    }
    
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
