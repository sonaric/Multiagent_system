/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workagents;

import core.Agent;
import core.AgentController;
import java.io.IOException;
import java.net.UnknownHostException;
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
        tr.setAgentName("truck4");
        tr.setStatus(tr.Free);
        System.out.println(tr.getUID_agent());
        AgentController ac = new AgentController(tr);
        ac.setup();
       
    }
    
}
