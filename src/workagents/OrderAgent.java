/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workagents;

import dataAgent.order.Order;
import aslcore.ACLMessage;
import core.Agent;
import core.AgentController;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stanislav
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderAgent extends Agent{
    
    private Order order;
    public OrderAgent() {
       super();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public static void main(String[]args) throws UnknownHostException, IOException, ClassNotFoundException, JAXBException{
        OrderAgent oa = new OrderAgent();
        oa.setAgentName("orderAgent1");
        oa.setSocket(new Socket("localhost", 1234));
        Order o1 = new Order();
        o1.setCustomer("Stanislav");
        o1.setOrderStatus(Order.NEW);
        o1.setOrderType("something");
        oa.setOrder(o1);
        AgentController ac = new AgentController(oa);
        ac.setup();
    }
    
}
