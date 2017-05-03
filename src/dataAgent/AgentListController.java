/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAgent;

import core.Agent;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Iterator;

/**
 *
 * @author Stanislav
 */
public class AgentListController {
    private AgentList agents = AgentList.getInstance();
    
    public void sendInfoAgents() throws IOException{
        Iterator<Agent> iterator = agents.getAgentList().iterator();
        while(iterator.hasNext()){
            ObjectOutputStream agentsOutputStream = new ObjectOutputStream(iterator.next().getSocket().getOutputStream());
            agentsOutputStream.close();
        }
    }
}
