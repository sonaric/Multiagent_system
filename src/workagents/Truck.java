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
public class Truck{
    public final String Free = "free";
    public final String Busy = "busy";
    private Agent parent;
    private long[] currentLocation;
    private long[] homeLocation;
    
    public Truck() throws CloneNotSupportedException{
       //super();
       parent = new Agent();
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, JAXBException, CloneNotSupportedException{
        Truck tr = new Truck();
        tr.parent.setAgentName("truck2");
        tr.parent.setStatus(tr.Free);
        System.out.println(tr.parent.getUID_agent());
        tr.parent.setSocket(new Socket("localhost",1234));
        AgentController ac = new AgentController(tr.parent);
        ac.setup();
       /* tr.parent.setSocket(new Socket("localhost",1234));                      //<---------------------ПЕРЕРОБИТЬ
        ACLMessage am = new ACLMessage(tr.parent);
        MessageData md = new MessageData();
        md.setSender(tr.parent.getUID_agent());
        md.setType(ACLMessage.INFO);
        md.setTimes(new Date());
        md.setContent("Blablabla");
        am.send(md,tr.parent.getSocket());*/
       
    }
    
}
