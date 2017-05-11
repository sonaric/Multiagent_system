/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aslcore;

import core.Agent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Stanislav
 */
public class ACLMessage {
    public static final int INFO = 1;
    public static final int DATA = 2;
    public static final int REQUEST = 3;
    public static final int RESPONSE = 4;
    public static final int ONTOLOGY = 5;
    public static final int AUTHORIZATION = 6;
    public static final int AGENT_LIST = 7;
    public static final int TRUCK_LIST = 8;
    
    private String messageContent;
    private String receiver;
    private String sender;
    private MessageList messages;
    private MessageData msg;
    private Agent agent;

    public ACLMessage(Agent agent) {
        msg = new MessageData();
        this.agent = agent;
    }
    
    /*
        !!! Message Shema: agent -> [server], message -> [server], [server] -> data message; !!!
        
    */
    
    public boolean  send(MessageData mesg,Socket sc) throws IOException, ClassNotFoundException{
        MessageData msd;
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(sc.getOutputStream())) {
            objectOutputStream.writeObject(agent);
            objectOutputStream.writeObject(mesg);
            if(mesg.getType() != ACLMessage.INFO)
            try (ObjectInputStream objectInputStream = new ObjectInputStream(sc.getInputStream())) {
                mesg = (MessageData)objectInputStream.readObject();
            }
        }
        if(mesg != null)
          return true;
        else
            return false;
    }
    
    public MessageData  receive() throws IOException, NullPointerException, ClassNotFoundException{
        MessageData msd;
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(agent.getSocket().getOutputStream())) {
            MessageData msgdata = new MessageData();
            msgdata.setType(ACLMessage.AUTHORIZATION);
            objectOutputStream.writeObject(agent);
            objectOutputStream.writeObject(msgdata);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(agent.getSocket().getInputStream())) {
                msd = (MessageData)objectInputStream.readObject();
            }
        }
        if(msd != null)
        {
            return msd;
        }else{
            return null;
        }
    }
    
    
}
