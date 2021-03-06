/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import aslcore.ACLMessage;
import aslcore.MessageData;
import core.xmlparser.XmlMarshalDemarshal;
import core.xmlparser.XmlParser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.xml.bind.JAXBException;
import dataAgent.order.Order;
import workagents.OrderAgent;
import workagents.Truck;

/**
 *
 * @author Stanislav
 */
public class AgentController {
    
    private Agent agent;

    public AgentController(Agent agent) {
        this.agent = agent;
    }
    public AgentController(Truck agent) {
        this.agent = agent;
    }
    
    public AgentController(OrderAgent agent) {
        this.agent = agent;
    }
    
    public void setup() throws IOException, ClassNotFoundException, JAXBException{
        try (ObjectOutputStream outputAgentStream = new ObjectOutputStream(agent.getSocket().getOutputStream())) {
            ObjectInputStream inputAgentStream;
            inputAgentStream = new ObjectInputStream(agent.getSocket().getInputStream());
            MessageData md = new MessageData();
            XmlParser parser = new XmlMarshalDemarshal();
            String content = parser.marshallParser(agent);
            md.setType(ACLMessage.AUTHORIZATION);
            md.setContent(content);
            if(agent.getClass().getName() == Truck.class.getName())
            {
                md.setSender_type(1);
            }
            if(agent.getClass().getName() == OrderAgent.class.getName())
            {
                md.setSender_type(2);
            }
            outputAgentStream.writeObject(md);
            
            md = (MessageData)inputAgentStream.readObject();
            System.out.println(md.getContent());
            
        }
        
    }
    
}
