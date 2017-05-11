/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Stanislav
 */
@XmlRootElement(name = "agent")
@XmlType(propOrder = {"agentName","UID_agent","status"})
public class Agent implements Serializable{
    private transient Socket socket;
    private String agentName;
    private String UID_agent;
    private String status;

    public Agent(String agentName) {
        this.agentName = agentName;
    }

    public Agent() {
        if(agentName == null)
        {
            Date date = new Date();
            agentName = String.valueOf(date.getTime());
        }
       
    }
    @XmlElement
    public void setUID_agent(String UID_agent) {
        this.UID_agent = UID_agent;
    }
    
    
    
    protected void createUniqAgentName() throws UnknownHostException{
        UID_agent = agentName+":"+System.getProperty("user.name")+"@"+InetAddress.getLocalHost().getHostAddress()+"/1234";  
    }

    public Socket getSocket() {
        return socket;
    }

    public String getAgentName() {
        return agentName;
    }
    @XmlTransient
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    @XmlElement
    public void setAgentName(String agentName) throws UnknownHostException {
        this.agentName = agentName;
        createUniqAgentName();
    }
    @XmlElement
    public void setStatus(String status) {
        this.status = status;
    }

    public String getUID_agent() {
        return UID_agent;
    }

    public String getStatus() {
        return status;
    }
    
    public Agent getParent(){
        return this;
    }
    
    
    
}
