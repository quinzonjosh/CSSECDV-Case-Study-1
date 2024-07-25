/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import CustomExceptions.PasswordException;
import View.Register;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cymon
 */
public class PasswordValidator {
    
    private final PasswordHasher hasher = new PasswordHasher();
    
    public boolean isValidPassword(String password, String confirmPassword) throws PasswordException{
        
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()-+=<>?/{}~|_.].*");
        
        
        if(password.length() < 8 || password.length() > 64){
            throw new PasswordException("Password must be 8 - 64 characters long.");
        }
        else if(!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar){
            throw new PasswordException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        }
        
        else if(!password.equals(confirmPassword)){
            throw new PasswordException("Password and Confirm Password do not match.");
        }
        else return true;
    }
    
   
    
    
    private boolean isPasswordPwned(String hashedPassword){
                
        String prefix = hashedPassword.substring(0,5);
        String suffix = hashedPassword.substring(5).toUpperCase();
        
        try {
            URL url = new URL("https://api.pwnedpasswords.com/range/" + prefix);
        
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            
            while((inputLine  = input.readLine()) != null){
                if(inputLine.startsWith(suffix)){
                    input.close();                    
                    return true;
                }
            }
            
            input.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public String passwordPwndCheck(String password) throws PasswordException{
        
        String hashedPassword = hasher.hash(password, "SHA-1");
        
        if(isPasswordPwned(hashedPassword)){
            throw new PasswordException("Password is too common.");
        } 
        
        return hashedPassword;
        
    }
    
}
