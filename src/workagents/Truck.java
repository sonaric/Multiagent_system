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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Stanislav
 */
@XmlRootElement(name = "truckagent")
@XmlAccessorType(XmlAccessType.FIELD)
public class Truck extends Agent{
    public transient final String Free = "free";
    public transient final String Busy = "busy";
    @XmlElementWrapper(name = "currentposition")
    @XmlElement(name = "pos")
    private long[] currentLocation;
    private long[] homeLocation;
    
    public Truck() throws CloneNotSupportedException{
       super();
    }
    void init(String name) throws UnknownHostException{
        this.setAgentName(name);
        this.setStatus(this.Free);
    }
    void init(){
        this.setStatus(this.Free);
    }

    public long[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(long[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    public long[] getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(long[] homeLocation) {
        this.homeLocation = homeLocation;
    }
    
    
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, JAXBException, CloneNotSupportedException{
        Truck tr = new Truck();
        tr.init("truck1");
        System.out.println(tr.getUID_agent());
        tr.setSocket(new Socket("localhost",1234));
        long[] l = new long[2];
        l[0]=12313121;
        l[1]=323124;
        tr.setCurrentLocation(l);
        AgentController ac = new AgentController(tr);
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
