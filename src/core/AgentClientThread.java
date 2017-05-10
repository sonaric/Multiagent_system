/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import aslcore.ACLMessage;
import aslcore.MessageData;
import aslcore.MessageList;
import core.xmlparser.XmlMarshalDemarshal;
import core.xmlparser.XmlParser;
import dataAgent.AgentList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.xml.bind.JAXBException;
import workagents.OrderAgent;
import workagents.Truck;



/**
 *
 * @author Stanislav
 */
public class AgentClientThread extends Thread{
    
    private final Socket socket;
    private AgentList agents;
    private Agent agent;

    public AgentClientThread(Socket socket) {        
        agents = AgentList.getInstance();
        this.socket = socket;  
        this.start();;
    }
    
    void addToList(Agent agent) throws NullPointerException{
        if(agent != null)
            agents.add(agent);
        else throw new NullPointerException();
    }
    
    @Override
    public void run(){
        MessageList messagesList = MessageList.getInstance();
        agents = AgentList.getInstance();
        try {
            ObjectOutputStream outputAgentStream;
            try (ObjectInputStream inputAgentStream = new ObjectInputStream(socket.getInputStream())) {
                outputAgentStream = new ObjectOutputStream(socket.getOutputStream());
                while(true){
                    MessageData md = (MessageData) inputAgentStream.readObject();
                    System.out.println(md.getContent());
                    XmlParser parser = new XmlMarshalDemarshal();
                    if(md.getType() == ACLMessage.AUTHORIZATION){
                        messagesList.getMessagesAUTHORIZATION().add(md);
                        if(md.getSender_type() == 1)
                        {
                            Truck temp_agent;
                            temp_agent = (Truck) parser.unmarhallParser(md.getContent(), Truck.class);
                            temp_agent.setSocket(socket);
                            agents.add(temp_agent);
                        }
                        if(md.getSender_type() == 2)
                        {
                            OrderAgent temp_agent;
                            temp_agent = (OrderAgent) parser.unmarhallParser(md.getContent(), OrderAgent.class);
                            temp_agent.setSocket(socket);
                            agents.add(temp_agent);
                        }
                        md.setType(ACLMessage.AGENT_LIST);
                        md.setContent(parser.marshallParser(agents));
                        outputAgentStream.writeObject(md);
                    }
                    if(md.getType() == ACLMessage.INFO){
                        messagesList.getMessagesINFO().add(md);
                        System.out.println(md.getContent());
                    }
                    if(md.getType() == ACLMessage.AGENT_LIST){
                        md.setContent(parser.marshallParser(agents));
                        outputAgentStream.writeObject(md);
                    }
                    break;
                }   
                //System.out.println(agents.getAgentList().size());
            } catch (JAXBException ex) {
                
            }
            //outputAgentStream.close();
        } catch (IOException | ClassNotFoundException ex) {
        } 
    }    
    
}
