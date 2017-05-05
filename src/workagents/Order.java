/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workagents;

/**
 *
 * @author Stanislav
 */
public class Order {
    private long[] outputPoint;
    private long[] inputPoint;
    private long[] alternativePoint;
    private String orderType;
    private String customer;
    private String orderStatus;

    public Order() {
    }
    
    

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long[] getOutputPoint() {
        return outputPoint;
    }

    public void setOutputPoint(long[] outputPoint) {
        this.outputPoint = outputPoint;
    }

    public long[] getInputPoint() {
        return inputPoint;
    }

    public void setInputPoint(long[] inputPoint) {
        this.inputPoint = inputPoint;
    }

    public long[] getAlternativePoint() {
        return alternativePoint;
    }

    public void setAlternativePoint(long[] alternativePoint) {
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
