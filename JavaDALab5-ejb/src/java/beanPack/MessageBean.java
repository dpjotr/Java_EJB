/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanPack;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author pjotr
 */
@Stateful
public class MessageBean implements IMessageBean {

    @EJB ISessionManager currentUsers;
    private boolean isLoggedIn;
    @Override
    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }
    private Map<String,String> registry;
    
    private boolean isMessageAmountError;
    private String name;
    private Integer messageCounter;
    private String[] messages;
    
    @Override
    public boolean getIsMessageAmountError() {
        return isMessageAmountError;
    }
    
        @Override
    public void setIsMessageAmountError(boolean isError) {
        isMessageAmountError=isError;
    }
    
    @PostConstruct
    public void init(){
        isLoggedIn=false;
        isMessageAmountError=false;
        registry=new HashMap<>();
        registry.put("admin", "admin");
        registry.put("user", "user");
        messages=new String[]{"message1","message2","message3"};
    }
    
    
    @Override
    public boolean login(String login, String psw) {
        if(login.trim()!=null&&psw.trim()!=null){
            if(registry.containsKey(login)&&registry.get(login).equals(psw)){
                currentUsers.addCurrentUsers(login);
                name=login;
                messageCounter=3;
                isLoggedIn=true;
                return true;
            }
            else name="unregistered";
        }
       
        isLoggedIn=false;                
        
        return false;
    }

    @Override
    public String[] getUsers() throws MessageException {
        if(isLoggedIn){
            String[] result;
            int size=currentUsers.getCurrentUsers().size();
            result=new String[size];
            for(int i=0; i<size;i++) result[i]=currentUsers.getCurrentUsers().get(i);
            return result;
        }         
                
        else throw new MessageException("You have to log in in order to view current users");
        
        

    }

    @Override
    public String getMessage(int index) throws MessageException {
        if(isLoggedIn)
            if(messageCounter>0)
                if(index>0&&index<4){
                    --messageCounter;
                    return messages[index-1]; 
                }
                else throw new MessageException("Message index is incorrect");
            else {
                logout();
                throw new MessageException("Your attempts to get messages have been expired");
            }
        else throw new MessageException("You have to log in in orderto recieve messages");

    }

    @Override
    public boolean logout() {
        currentUsers.removeCurrentUsers(name);
        isLoggedIn=false;
        return true;
    }




}
