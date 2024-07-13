/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CustomExceptions;

/**
 *
 * @author Cymon
 */
public class LoginException extends Exception {
    
    public LoginException(){
        super("Invalid username or password, please try again.");
    }
    
    protected LoginException(String message){
        super(message);
    }
    
}
