/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAgent;

import java.util.ArrayList;
import core.Agent;
import java.io.Serializable;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stanislav
 */
@XmlRootElement(name = "agents")
//@XmlAccessorType(XmlAccessType.FIELD)
public class AgentList implements Serializable{
    @XmlTransient
    private ArrayList<Agent> agentList;
    
    public static volatile AgentList instance;

    private AgentList() {
        this.agentList = new ArrayList<>();
    }
    
    public synchronized static AgentList getInstance(){
        if(instance == null)
            synchronized (AgentList.class) {
                if(instance == null)
                    instance = new AgentList();
            }
        return instance;
    }
    
    private static Class getClass(String classname) throws ClassNotFoundException{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null)
            classLoader = AgentList.class.getClassLoader();
        return (classLoader.loadClass(classname));
    }
    

    public synchronized boolean add(Agent object){
        if(object != null)
        {
            if(getAgent(object.getUID_agent()) == null){
                agentList.add(object);
                return true;}else
                return false;
        }else{
            return false;
        }
    }
    public synchronized Agent getAgent(String name) throws NullPointerException{
        Agent a = null;
        Agent temp = new Agent();
        Iterator<Agent> iterator = agentList.iterator();
        while(iterator.hasNext()){
            temp = iterator.next();
            if(temp.getUID_agent().equals(name)){
                a = temp;
                return a;
                
            }
        }
        return a;
    }
    @XmlElement(name="agent")
    public void setAgentList(ArrayList<Agent> agentList) {
        this.agentList = agentList;
    }

    public synchronized ArrayList<Agent> getAgentList() {
        return agentList;
    }
    
    
}
