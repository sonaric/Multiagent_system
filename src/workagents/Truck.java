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
import java.net.InetAddress;
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
    private String[] currentLocation;
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

    public String[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    public long[] getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(long[] homeLocation) {
        this.homeLocation = homeLocation;
    }
    
    @Override
    protected void createUniqAgentName() throws UnknownHostException{
        this.setUID_agent(this.getAgentName()+"#Truck"+":"+System.getProperty("user.name")+"@"+InetAddress.getLocalHost().getHostAddress()+"/1234");
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, JAXBException, CloneNotSupportedException{
        Truck tr = new Truck();
        tr.init("truck3");
        System.out.println(tr.getUID_agent());
        tr.setSocket(new Socket("localhost",1234));
        String[] l = new String[2];
        l[0]="46.9496892";
        l[1]="31.9983635";
        tr.setCurrentLocation(l);
        AgentController ac = new AgentController(tr);
        ac.setup();
       
    }
    
}
