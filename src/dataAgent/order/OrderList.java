/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAgent.order;

import java.util.ArrayList;
import java.util.Iterator;
import workagents.Truck;

/**
 *
 * @author Stanislav
 */
public class OrderList{
    private ArrayList<Order> orderList;
    public static volatile OrderList instance;

    private  OrderList() {
        this.orderList = new ArrayList<>();
    }
    
    public synchronized static  OrderList getInstance(){
        if(instance == null)
            synchronized ( OrderList.class) {
                if(instance == null)
                    instance = new  OrderList();
            }
        return instance;
    }

    public synchronized ArrayList<Order> getOrderList() {
        return orderList;
    }
    
    public synchronized boolean add(Order object){
        if(object != null)
        {
            if(!orderList.isEmpty()){
                for(int i = 0; i<orderList.size(); i++){
                    if(object.getOrderId() == orderList.get(i).getOrderId()){
                        object.setOrderStatus(Order.INPROCESS);
                        orderList.set(i, object);
                        return true;
                    }
                }
                orderList.add(object);
            }else{
            orderList.add(object);
            }
            return true;
        }else{
            return false;
        }
    }
    
    public synchronized ArrayList<Order> newOrder(){
        ArrayList<Order> order = new ArrayList<>();
        Iterator<Order> iterator = orderList.iterator();
        while (iterator.hasNext()) {
            Order next = iterator.next();
            if(next.getOrderStatus().equals(Order.NEW)){
                order.add(next);
            }
        }
        if(order.isEmpty()){
            return null;
        }else
            return order;
    }
    
    public synchronized int getSize(){
        return orderList.size();
    }
    
}
