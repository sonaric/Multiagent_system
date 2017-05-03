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
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Stanislav
 */
public class AgentController {
    
    private Agent agent;

    public AgentController(Agent agent) {
        this.agent = agent;
    }
    
    public void setup() throws IOException, ClassNotFoundException, JAXBException{
        agent.setSocket(new Socket("localhost",1234));
        try (ObjectOutputStream outputAgentStream = new ObjectOutputStream(agent.getSocket().getOutputStream())) {
            ObjectInputStream inputStream = new ObjectInputStream(agent.getSocket().getInputStream());
            while(true){
                outputAgentStream.writeObject(agent);
                String res = (String) inputStream.readObject();
                XmlParser parser = new XmlMarshalDemarshal();
                
                AgentList agents = AgentList.getInstance();
                agents = (AgentList) parser.unmarhallParser(res, AgentList.class);
                Iterator<Agent> it = agents.getAgentList().iterator();
                System.out.println("Enable agents:");
                while (it.hasNext()) {
                    Agent next = it.next();
                    if(!agent.getUID_agent().equals(next.getUID_agent()))
                        System.out.println(next.getUID_agent());
                    
                }
                System.out.println(agents.getAgentList().size());
                break;
            }
        }
        
    }
    
}
