
package View;

import Controller.PasswordHasher;
import Controller.PasswordValidator;
import CustomExceptions.PasswordException;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class Register extends javax.swing.JPanel {
    
    
    public Frame frame;
    private final PasswordValidator validator = new PasswordValidator();
    private final PasswordHasher passwordHasher = new PasswordHasher();
    
    public Register() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registerBtn = new javax.swing.JButton();
        passwordFld = new javax.swing.JPasswordField();
        usernameFld = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        confpassFld = new javax.swing.JPasswordField();
        backBtn = new javax.swing.JButton();

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        confpassFld.setBackground(new java.awt.Color(240, 240, 240));
        confpassFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confpassFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confpassFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "CONFIRM PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        backBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        backBtn.setText("<Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confpassFld, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(200, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBtn)
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confpassFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        String username = usernameFld.getText();
        String password = new String(passwordFld.getPassword());
        String confirmPassword = new String(confpassFld.getPassword());
        
        try{
            
            if(!hasEmptyFields(username, password, confirmPassword)){
                
                usernameFld.setText("");
                passwordFld.setText("");
                confpassFld.setText("");
                
                if(isValidUsername(username) && 
                validator.isValidPassword(password, confirmPassword)){
                    
                    String hashedPassword = validator.passwordPwndCheck(password);
                    String finalHashedPassword = passwordHasher.hash(hashedPassword, "SHA-256");

                    frame.registerAction(username, finalHashedPassword, Arrays.toString(confpassFld.getPassword()));
                    
                    JOptionPane.showMessageDialog(this, "New user successfully registered", "Registration Successful", JOptionPane.PLAIN_MESSAGE);
                    frame.logAction("NOTICE", username, "User creation Successful");
                    frame.loginNav();
  
                }
            }
      
            
        } catch(PasswordException e){
            e.setHeader("Registration Failed");
            JOptionPane.showMessageDialog(this, e.getMessage(), e.getHeader(), JOptionPane.ERROR_MESSAGE);
            
        }
        
    }//GEN-LAST:event_registerBtnActionPerformed
    
    private boolean hasEmptyFields(String username, String password, String confirmPassword){
        if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please complete the registration form.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return true;
        } 
        return false;
    }
    
    private boolean isValidUsername(String username){
        if(username.length() < 5 || username.length() > 20) {
            JOptionPane.showMessageDialog(this, "Username must be 5 - 20 characters long.", "Registration Failed", JOptionPane.ERROR_MESSAGE);            
            return false;
        } 
        if(username.contains(" ")){
            JOptionPane.showMessageDialog(this, "Username must not contain spaces.", "Registration Failed", JOptionPane.ERROR_MESSAGE);            
            return false;
        }
        for(char c : username.toCharArray()){
            if(!Character.isLetterOrDigit(c) && c != '_' && c != '-' && c != '.'){
                JOptionPane.showMessageDialog(this, "Username may only contain special characters dots, dashes, and underscores", "Registration Failed", JOptionPane.ERROR_MESSAGE);                                
                return false;
            }
        }
        
        if(frame.usernameExist(username)){
            JOptionPane.showMessageDialog(this, "Username already exist.", "Registration Failed", JOptionPane.ERROR_MESSAGE);                                
            return false;
        }
        return true;
    }
    
//    private boolean isValidPassword(String password, String confirmPassword){
//        
//        boolean hasUppercase = !password.equals(password.toLowerCase());
//        boolean hasLowercase = !password.equals(password.toUpperCase());
//        boolean hasDigit = password.matches(".*\\d.*");
//        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()-+=<>?/{}~|_.].*");
//        
//        if(password.length() < 8 || password.length() > 64){
//            JOptionPane.showMessageDialog(this, "Password must be 8 - 64 characters long.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        if(!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar){
//            JOptionPane.showMessageDialog(this, "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        
//        if(!password.equals(confirmPassword)){
//            JOptionPane.showMessageDialog(this, "Password and Confirm Password do not match.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        return true;
//    }
    
    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        frame.loginNav();
    }//GEN-LAST:event_backBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JPasswordField confpassFld;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
