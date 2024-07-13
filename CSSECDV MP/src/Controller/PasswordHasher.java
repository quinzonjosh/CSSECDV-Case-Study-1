package Controller;


import View.Register;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author quinz
 */
public class PasswordHasher {

    public PasswordHasher() {
    }
    
    public String hash(String password, String algo){
        try {
            MessageDigest md = MessageDigest.getInstance(algo);
        
            md.update(password.getBytes());
            
            byte[] byteData = md.digest();
            
            StringBuilder sb = new StringBuilder();
            for(byte b : byteData){
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
