/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanPack;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author pjotr
 */
@Local
public interface ISessionManager {
    
    public ArrayList<String> getCurrentUsers() ;       
    public void addCurrentUsers(String name) ; 
    public void removeCurrentUsers(String name) ; 
    
    
}
