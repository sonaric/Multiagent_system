/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import core.Agent;
import core.AgentClientThread;
import dataAgent.AgentList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Stanislav
 */
public class AgentConteiner {
    
    public static void main(String[] args) throws IOException{
        
        ServerSocket srvs = new ServerSocket(1234);
        AgentList agents = AgentList.getInstance();
        
        while(true){
            Socket socket = null;
            while(socket == null)
            {
                socket = srvs.accept();
            }
            new AgentClientThread(socket);
            
        }
        
    }
    
    
}
