/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customerapp.mdb;

import com.customerapp.entity.Customer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author kurt
 */
@MessageDriven(mappedName = "jms/NotificationQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NotificationBean implements MessageListener {
    
    public NotificationBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        //Normally don't process message in the MDB itself. Best if MDB invokes methods in another Session Bean to process
        try{
            Object msgObj = ((ObjectMessage)message).getObject();
            if (msgObj != null){
                Customer customer = (Customer)msgObj;
                System.out.println("Customer with the following details has been updated:");
                StringBuilder sb = new StringBuilder();
                sb.append("Customer ID=");
                sb.append(customer.getCustomerId());
                sb.append(", ");
                sb.append("Name=");
                sb.append(customer.getName());
                sb.append(", ");
                sb.append("Email=");
                sb.append(customer.getEmail());
                System.out.println(sb.toString());
        
            }
        } catch (JMSException ex){
            Logger.getLogger(NotificationBean.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
}
