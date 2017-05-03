/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.xmlparser.XmlMarshalDemarshal;
import core.xmlparser.XmlParser;
import dataAgent.AgentList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Result;



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
        System.out.println("Client CON!");
        try {
            ObjectInputStream inputAgentStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputAgentStream = new ObjectOutputStream(socket.getOutputStream());
            while(true){
                agent = (Agent) inputAgentStream.readObject();
                agent.setSocket(socket);
                System.out.println(agent.getUID_agent());
                addToList(agent);
                parser = new XmlMarshalDemarshal();
                outputAgentStream.writeObject(parser.marshallParser(agents));
                break;
            }
            
            System.out.println(agents.getAgentList().size());
            
        } catch (IOException | ClassNotFoundException | JAXBException ex) {
            
           ex.printStackTrace();
        } 
    }    
    
}
