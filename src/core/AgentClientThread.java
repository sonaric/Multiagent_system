/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import aslcore.MessageData;
import core.xmlparser.XmlMarshalDemarshal;
import core.xmlparser.XmlParser;
import dataAgent.AgentList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.xml.bind.JAXBException;



/**
 *
 * @author Stanislav
 */
public class AgentClientThread extends Thread{
    
    private final Socket socket;
    private AgentList agents;
    private Agent agent;
    XmlParser parser;

    public AgentClientThread(Socket socket) {        
        agents = AgentList.getInstance();
        this.socket = socket;  
        this.start();
    }
    
    void addToList(Agent agent) throws NullPointerException{
        if(agent != null)
            agents.add(agent);
        else throw new NullPointerException();
    }
    
    @Override
    public void run(){
        agents = AgentList.getInstance();
        try {
            ObjectOutputStream outputAgentStream;
            try (ObjectInputStream inputAgentStream = new ObjectInputStream(socket.getInputStream())) {
                outputAgentStream = new ObjectOutputStream(socket.getOutputStream());
                while(true){
                    System.out.println("+");
                    agent = (Agent) inputAgentStream.readObject();
                    System.out.println(agent.getUID_agent());
                    agent.setSocket(socket);
                    addToList(agent);
                    MessageData md = (MessageData) inputAgentStream.readObject();
                    System.out.println(md.getContent());
                    break;
                }   
                //System.out.println(agents.getAgentList().size());
            }
            //outputAgentStream.close();
        } catch (IOException | ClassNotFoundException ex) {
        } 
    }    
    
}
