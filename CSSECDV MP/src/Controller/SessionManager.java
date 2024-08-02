/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Session;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Cymon
 */
public class SessionManager {
    
   private static final String ALGORITHM = "AES";
   private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    private static SecretKey getKeyFromPassword(String password, String salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
            .getEncoded(), "AES");
        return secret;
    }
    
    
    private static String encrypt(SQLite database, String username, Session object) throws Exception {
        try {
            // Serialize the object to a byte array
            byte[] serializedObject = serializeObject(object);
            
            
            //get session key of user
            SecretKey key = getKeyUsingName(database, username);
            
            // Create a cipher instance
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Encrypt the serialized object 
            byte[] encryptedData = cipher.doFinal(serializedObject);

            // Convert encrypted data to a Base64 string
            return Base64.getEncoder().encodeToString(encryptedData);
            
        } catch (Exception e) {
            // Handle exceptions here, e.g., log, rethrow, or return specific error message
            throw new RuntimeException("Encryption failed", e);
        }
    }
    
    private static Session decrypt(SQLite database, String id, String encrypted) throws Exception{
        try {
            // Decode the Base64 string
            byte[] encryptedData = Base64.getDecoder().decode(encrypted);
            
           
            
            //get session key of user
            SecretKey key = getKeyUsingID(database, id);
            
            // Create a cipher instance
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            
            // Decrypt the data
            byte[] decryptedData = cipher.doFinal(encryptedData);

            // Deserialize the decrypted data back to the object
            return deserializeObject(decryptedData);
        } catch (Exception e) {
            // Handle exceptions here, e.g., log, rethrow, or return specific error message
            throw new RuntimeException("Decryption failed", e);
        }

    }
      
    private static byte[] serializeObject(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(object);
            oos.close();
        }
        return baos.toByteArray();
    }

    private static Session deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            
            Session session = (Session) ois.readObject();
     
            ois.close();
            
            return session;
        }
    }
   
    
    public static void createKeyOnRegistration(SQLite database, String username, String password) throws Exception{
        
        //salt value
        Random random = new Random();
        String salt = String.valueOf(random.nextInt(1, Integer.MAX_VALUE));
        
        SecretKey key = getKeyFromPassword(password, salt);
        String keyString = Base64.getEncoder().encodeToString(key.getEncoded());
        
        
        database.addSessionKeyToUser(username, keyString);

    }
    
    
    private static SecretKey getKeyUsingName(SQLite database, String username) throws Exception{
        
        String key = database.getSessionKeyUsingName(username);
        
        
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKey secret = new SecretKeySpec(keyBytes, "AES");
        
      
        return secret;
        
       
    }
    
    private static SecretKey getKeyUsingID(SQLite database, String id) throws Exception {
        String key = database.getSessionKeyUsingID(id);
        
        
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKey secret = new SecretKeySpec(keyBytes, "AES");
        
      
        return secret;

    }
    
    public static Session checkSession(SQLite database, String id) throws Exception {
        //get session using id
        String encrypted = database.getSession(id);
        database.updateSessionAccess(id);
        
        //decrypt using id and encrypted session
        Session session = decrypt(database, id, encrypted);

        return session;
               
    }
    
    public static String createSession(SQLite database, String username) throws Exception {
        //get role of user
        int role = database.getUserRole(username);
       
        //create a session instance
        Session session = new Session(username, role);
        

        //encrypt session object to string
        String encrypted = encrypt(database, username, session);
        
        //create a session in sessions db (String session) that returns an ID
        String id = database.addSession(username, encrypted, new PasswordHasher());
        return id;
    }

}
