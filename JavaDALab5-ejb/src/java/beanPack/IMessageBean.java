/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanPack;

import javax.ejb.Local;

/**
 *
 * @author pjotr
 */
@Local
public interface IMessageBean {
    public boolean getIsLoggedIn();
    public boolean getIsMessageAmountError();
    public void setIsMessageAmountError(boolean setter);
    boolean login(String login, String psw);

    String[] getUsers() throws MessageException;

    String getMessage (int index) throws MessageException;

    boolean logout ();
    
}
