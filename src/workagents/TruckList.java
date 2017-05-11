/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workagents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stanislav
 */
@XmlRootElement(name = "truck")
public class TruckList implements Serializable{
     @XmlTransient
    private ArrayList<Truck> agentList;
    
    public static volatile TruckList instance;

    private TruckList() {
        this.agentList = new ArrayList<>();
    }
    
    public synchronized static TruckList getInstance(){
        if(instance == null)
            synchronized (TruckList.class) {
                if(instance == null)
                    instance = new TruckList();
            }
        return instance;
    }
    
    private static Class getClass(String classname) throws ClassNotFoundException{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null)
            classLoader = TruckList.class.getClassLoader();
        return (classLoader.loadClass(classname));
    }
    

    public synchronized boolean add(Truck object) throws NullPointerException, CloneNotSupportedException{
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
    public synchronized Truck getAgent(String name) throws NullPointerException, CloneNotSupportedException{
        Truck a = null;
        Truck temp = new Truck();
        Iterator<Truck> iterator = agentList.iterator();
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
    public void setAgentList(ArrayList<Truck> agentList) {
        this.agentList = agentList;
    }

    public synchronized ArrayList<Truck> getAgentList() {
        return agentList;
    }
    
}
