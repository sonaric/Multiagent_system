/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aslcore;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



/**
 *
 * @author Stanislav
 */
@XmlRootElement
@XmlType(propOrder = {"type","sender","receiver","times","address","content","status"})
public class MessageData implements Serializable{
    private String receiver;
    private String sender;
    private String address;
    private Date times;
    private int type;
    
    private String content;
    private boolean satus;
    //private Object obj;
    
    public MessageData() {
    } 

    public String getReceiver() {
        return receiver;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSatus() {
        return satus;
    }

    public void setSatus(boolean satus) {
        this.satus = satus;
    }
    
}
