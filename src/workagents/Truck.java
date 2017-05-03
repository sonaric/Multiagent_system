/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workagents;

import aslcore.ACLMessage;
import aslcore.MessageData;
import core.Agent;
import core.AgentController;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Stanislav
 */
public class Truck extends Agent{
    public final String Free = "free";
    public final String Busy = "busy";
    
    public Truck(){
       super();
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, JAXBException{
        Truck tr = new Truck();
        tr.setAgentName("truck1");
        tr.setStatus(tr.Free);
        System.out.println(tr.getUID_agent());
        tr.setSocket(new Socket("localhost",1234));
        AgentController ac = new AgentController(tr);
        ac.setup();
        tr.setSocket(new Socket("localhost",1234));                      //<---------------------ПЕРЕРОБИТЬ
        ACLMessage am = new ACLMessage(tr);
        MessageData md = new MessageData();
        md.setSender(tr.getUID_agent());
        md.setType(ACLMessage.INFO);
        md.setTimes(new Date());
        md.setContent("First message send!");
        am.send(md,tr.getSocket());
       
    }
    
}
