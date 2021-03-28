
package view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beanPack.IMessageBean;
import beanPack.MessageException;
import javax.ejb.EJB;

public class MainPage extends HttpServlet {
   

    private String message="Read system messages here";
    @EJB IMessageBean service;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>\n" +
                        "    <head>\n") ;           
            out.println((service.getIsLoggedIn()==true?"You have loged in":"You have not log in")+"<br>");
            out.println(message);
            out.println("    </head>\n" +
                        "    <body>\n" +
                        "         <br>\n" +
                        "           <br>\n" +
                        "        <center>   \n" +
                        "         <form action=\"index.html\" method=\"get\">\n"+
                        "            <input type=\"submit\" name=\"Action\" value=\"login\"/>"+
                        "        </form><br><hr/>\n" +      
                        "        <form action=\"MainPage\" method=\"get\">\n"+
                        "            <input type=\"submit\" name=\"Action\" value=\"logout\"/><br><br>"+
                        "            <input type=\"submit\" name=\"Action\" value=\"getUsers\"/><br><br>"+
                        "        </form><br><hr/>\n" + 
                        "        <form action=\"MainPage\" method=\"get\">\n"+
                        "            <label>Enter message number<label><input type=\"text\" name=\"MessageNumber\" size=\"2\"/> "+                   
                        "            <input type=\"submit\" name=\"Action\" value=\"getMessages\"/><br><br>"+
                        "        </form><br><hr/>\n" +  
                        "        </center>\n" +
                        "    </body>\n" +
                        "</html>");

        }
    }
    
    
    boolean tryParseInt(String value) {  
     try {  
         Integer.parseInt(value);  
         return true;  
      } catch (NumberFormatException e) {  
         return false;  
      }  
}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String action=(String)request.getParameter("Action");
        if (action.equals("login")){
            service.login(request.getParameter("Login").trim()
                                     ,request.getParameter( "Password"));
        }  
        if (action.equals("logout")){
            service.logout();
        }
        if (action.equals("getUsers")){
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Current users: ");
                    for (int i = 0; i < service.getUsers().length; i++) {
                        stringBuilder.append(service.getUsers()[i]+", ");
}
                message=stringBuilder.toString();
            }   catch (MessageException ex) {
                    message=ex.getMessage();
                }
        }
            
        if (action.equals("getMessages")){
            service.setIsMessageAmountError(false);
            String messageNumber=request.getParameter("MessageNumber");
            if(tryParseInt(messageNumber))
                    try {
                            message=service.getMessage(Integer.parseInt(messageNumber));
                    }   catch (MessageException ex) {
                            message=ex.getMessage();
                        }
            else message="You have to use integer number";

        }    

        
        
        
        processRequest(request, response);
     
       
    }
    
    
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


   
    @Override
    public String getServletInfo() {
        
        return "Short description";
    }


    

}