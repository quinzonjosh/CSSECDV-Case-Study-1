/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CustomExceptions;

/**
 *
 * @author Cymon
 */
public class AttemptException extends LoginException {
    
    public AttemptException(){
        super("Cannot login right now, please try again later.");
    }
    
}
