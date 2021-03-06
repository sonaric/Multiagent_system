/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAgent.order;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Stanislav
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    @XmlElementWrapper(name = "out")
    private double[] outputPoint;
    @XmlElementWrapper(name = "in")
    private double[] inputPoint;
    @XmlElementWrapper(name = "alt")
    private double[] alternativePoint;
    private String orderType;
    private String customer;
    private String orderStatus;
    private long orderId;
    private String orderTruck;

    public String getOrderTruck() {
        return orderTruck;
    }

    public void setOrderTruck(String orderTruck) {
        this.orderTruck = orderTruck;
    }
    public final static String NEW = "New";
    public final static String INPROCESS = "Proc";
    public final static String DONE= "Done";

    public Order() {
        setOrderId(new Date().getTime());
    }

    public long getOrderId() {
        return orderId;
    }

    private void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    
    

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double[] getOutputPoint() {
        return outputPoint;
    }

    public void setOutputPoint(double[] outputPoint) {
        this.outputPoint = outputPoint;
    }

    public double[] getInputPoint() {
        return inputPoint;
    }

    public void setInputPoint(double[] inputPoint) {
        this.inputPoint = inputPoint;
    }

    public double[] getAlternativePoint() {
        return alternativePoint;
    }

    public void setAlternativePoint(double[] alternativePoint) {
        this.alternativePoint = alternativePoint;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
    
}
