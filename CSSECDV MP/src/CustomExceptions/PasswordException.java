/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CustomExceptions;

/**
 *
 * @author Cymon
 */
public class PasswordException extends Exception{
    
    private String message, header;

    public PasswordException(String message, String header) {
        super();
        this.message = message;
        this.header = header;
    }

    public PasswordException(String message) {
        super();
        this.message = message;
    }
    
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    
    
    
    
    
    
}
