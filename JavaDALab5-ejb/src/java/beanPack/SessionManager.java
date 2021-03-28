/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanPack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author pjotr
 */
@Singleton
public class SessionManager implements ISessionManager {

    private ArrayList<String> currentUsers;
    
    @PostConstruct
    public void init(){
        currentUsers=new ArrayList<String>();
    }
    @Override
    public ArrayList<String> getCurrentUsers() {
        return currentUsers;
    }
    @Override
    public void addCurrentUsers(String name) {
        if(currentUsers!=null)
        currentUsers.add(name);
    }

    @Override
    public void removeCurrentUsers(String name) {
       if(currentUsers!=null)
        currentUsers.remove(name);
    }
    
}
