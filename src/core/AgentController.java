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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try (ObjectOutputStream outputAgentStream = new ObjectOutputStream(agent.getSocket().getOutputStream())) {
            System.out.println("+");
            outputAgentStream.writeObject(agent);
            System.out.println("+");
            MessageData md = new MessageData();
            md.setContent("connected agent");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {                                 //<-------------ПЕРЕРОБИТЬ ПРИНЦИП

            }
            outputAgentStream.writeObject(md);
            System.out.println("+");
        }
        
    }
    
}
