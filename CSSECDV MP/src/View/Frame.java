package View;

import Controller.Main;
import Controller.PasswordHasher;
import Controller.SessionManager;
import Model.Session;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Frame extends javax.swing.JFrame {

    public Main main;
    public Login loginPnl = new Login();
    public Register registerPnl = new Register();
    
  
    private AdminHome adminHomePnl = new AdminHome();
    private ManagerHome managerHomePnl = new ManagerHome();
    private StaffHome staffHomePnl = new StaffHome();
    private ClientHome clientHomePnl = new ClientHome();
    
    private CardLayout contentView = new CardLayout();
    private CardLayout frameView = new CardLayout();
    
    private static final int MAX_LOGIN = 5;
    private String userSession = "";
    private HashMap<Integer, String> accessMatrix;
    
    
    public Frame() {
        initComponents();
        
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(Frame.this, 
                        "Are you sure you want to exit?", 
                        "Confirm Exit", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    
                    if(!Frame.this.userSession.isBlank()){
                        try {
                            Frame.this.logOutProcedure();
                            dispose(); // Close the frame
                            System.exit(0); // Exit the program
                        } catch(Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(Frame.this, "Connot logout right now. Please try again later or report to admin for details.", "Logout Unsuccessful", JOptionPane.ERROR_MESSAGE);
                            Frame.this.logAction("LOG_OUT", "SESSIONID: " + Frame.this.userSession, String.format("[FAIL] Failure to logout out due to server error: %s.", ex));
                        }
                    }
                    else {
                        dispose(); // Close the frame
                        System.exit(0); // Exit the program
                    }
                }
                
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Container = new javax.swing.JPanel();
        HomePnl = new javax.swing.JPanel();
        Content = new javax.swing.JPanel();
        Navigation = new javax.swing.JPanel();
        adminBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        managerBtn = new javax.swing.JButton();
        staffBtn = new javax.swing.JButton();
        clientBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        setMinimumSize(new java.awt.Dimension(800, 450));

        HomePnl.setBackground(new java.awt.Color(102, 102, 102));
        HomePnl.setPreferredSize(new java.awt.Dimension(801, 500));

        javax.swing.GroupLayout ContentLayout = new javax.swing.GroupLayout(Content);
        Content.setLayout(ContentLayout);
        ContentLayout.setHorizontalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );
        ContentLayout.setVerticalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Navigation.setBackground(new java.awt.Color(204, 204, 204));

        adminBtn.setBackground(new java.awt.Color(250, 250, 250));
        adminBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        adminBtn.setText("Admin Home");
        adminBtn.setFocusable(false);
        adminBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        managerBtn.setBackground(new java.awt.Color(250, 250, 250));
        managerBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        managerBtn.setText("Manager Home");
        managerBtn.setFocusable(false);
        managerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managerBtnActionPerformed(evt);
            }
        });

        staffBtn.setBackground(new java.awt.Color(250, 250, 250));
        staffBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        staffBtn.setText("Staff Home");
        staffBtn.setFocusable(false);
        staffBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                staffBtnActionPerformed(evt);
            }
        });

        clientBtn.setBackground(new java.awt.Color(250, 250, 250));
        clientBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clientBtn.setText("Client Home");
        clientBtn.setFocusable(false);
        clientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientBtnActionPerformed(evt);
            }
        });

        logoutBtn.setBackground(new java.awt.Color(250, 250, 250));
        logoutBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        logoutBtn.setText("LOGOUT");
        logoutBtn.setFocusable(false);
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NavigationLayout = new javax.swing.GroupLayout(Navigation);
        Navigation.setLayout(NavigationLayout);
        NavigationLayout.setHorizontalGroup(
            NavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(managerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(staffBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        NavigationLayout.setVerticalGroup(
            NavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(adminBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(managerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(staffBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout HomePnlLayout = new javax.swing.GroupLayout(HomePnl);
        HomePnl.setLayout(HomePnlLayout);
        HomePnlLayout.setHorizontalGroup(
            HomePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomePnlLayout.createSequentialGroup()
                .addComponent(Navigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HomePnlLayout.setVerticalGroup(
            HomePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Navigation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
            .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(HomePnl, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE))
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(HomePnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminBtnActionPerformed
        try {
            Session session = SessionManager.checkSession(main.sqlite, this.userSession);
            if(this.accessMatrix.get(session.getRole()).equals("Administrator")){
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[SUCCESS] User verified to access %s page", "Administrator"));
                
                adminHomePnl.passSession(userSession);
                Content.remove(adminHomePnl);
                Content.add(adminHomePnl, "adminHomePnl");
                adminHomePnl.showPnl("home");
                contentView.show(Content, "adminHomePnl");
            }
            else {
                JOptionPane.showMessageDialog(this, "You do not have access to this page.", "Access Failure", JOptionPane.ERROR_MESSAGE);
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[FAIL] User not verified to access %s page", "Administrator"));
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Server error. Please contact admin.", "Server Failure", JOptionPane.ERROR_MESSAGE);
            this.logAction("CHECK_SESSION", "SESSIONID: " + this.userSession, String.format("[FAIL] server failure due to %s", e));
        }
        
        
    }//GEN-LAST:event_adminBtnActionPerformed

    private void managerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerBtnActionPerformed
       
        try {
            Session session = SessionManager.checkSession(main.sqlite, this.userSession);
            if(this.accessMatrix.get(session.getRole()).equals("Manager")){
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[SUCCESS] User verified to access %s page", "Manager"));
                
                managerHomePnl.passSession(userSession);
                Content.remove(managerHomePnl);
                Content.add(managerHomePnl, "managerHomePnl");
                managerHomePnl.showPnl("home");
                contentView.show(Content, "managerHomePnl");
            }
            else {
                JOptionPane.showMessageDialog(this, "You do not have access to this page.", "Access Failure", JOptionPane.ERROR_MESSAGE);
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[FAIL] User not verified to access %s page", "Manager"));
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Server error. Please contact admin.", "Server Failure", JOptionPane.ERROR_MESSAGE);
            this.logAction("CHECK_SESSION", "SESSIONID: " + this.userSession, String.format("[FAIL] server failure due to %s", e));
        }
        
        
    }//GEN-LAST:event_managerBtnActionPerformed

    private void staffBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffBtnActionPerformed
        
        try {
            Session session = SessionManager.checkSession(main.sqlite, this.userSession);
            if(this.accessMatrix.get(session.getRole()).equals("Staff")){
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[SUCCESS] User verified to access %s page", "Staff"));
                
                staffHomePnl.passSession(userSession);
                Content.remove(staffHomePnl);
                Content.add(staffHomePnl, "staffHomePnl");
                staffHomePnl.showPnl("home");
                contentView.show(Content, "staffHomePnl");
            }
            else {
                JOptionPane.showMessageDialog(this, "You do not have access to this page.", "Access Failure", JOptionPane.ERROR_MESSAGE);
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[FAIL] User not verified to access %s page", "Staff"));
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Server error. Please contact admin.", "Server Failure", JOptionPane.ERROR_MESSAGE);
            this.logAction("CHECK_SESSION", "SESSIONID: " + this.userSession, String.format("[FAIL] server failure due to %s", e));
        }
        
        
    }//GEN-LAST:event_staffBtnActionPerformed

    private void clientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientBtnActionPerformed
        
        try {
            Session session = SessionManager.checkSession(main.sqlite, this.userSession);
            if(this.accessMatrix.get(session.getRole()).equals("Client")){
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[SUCCESS] User verified to access %s page", "Client"));
                
                clientHomePnl.passSession(userSession);
                Content.remove(clientHomePnl);
                Content.add(clientHomePnl, "clientHomePnl");
                clientHomePnl.showPnl("home");
                contentView.show(Content, "clientHomePnl");
            }
            else {
                JOptionPane.showMessageDialog(this, "You do not have access to this page.", "Access Failure", JOptionPane.ERROR_MESSAGE);
                this.logAction("ACCESS_PAGE", "USERNAME: " + session.getUsername(), String.format("[FAIL] User not verified to access %s page", "Client"));
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Server error. Please contact admin.", "Server Failure", JOptionPane.ERROR_MESSAGE);
            this.logAction("CHECK_SESSION", "SESSIONID: " + this.userSession, String.format("[FAIL] server failure due to %s", e));
        }
        
        
    }//GEN-LAST:event_clientBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        
        try{
           this.logOutProcedure();
           this.logAction("LOG_OUT", "SESSIONID: " + this.userSession, String.format("[SUCCESS] Delinked and Deleted current session %s.", this.userSession));

           Container.removeAll();
           this.init(main);
           this.logAction("LOG_OUT", "SESSIONID: " + this.userSession, "[SUCCESS] Current user logging out.");
           frameView.show(Container, "loginPnl");
           
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot logout right now. Please try again later or report to admin for details.", "Logout Unsuccessful", JOptionPane.ERROR_MESSAGE);
            this.logAction("LOG_OUT", "SESSIONID: " + this.userSession, String.format("[FAIL] Failure to logout out due to server error: %s.", e));
        }
        
  
    }//GEN-LAST:event_logoutBtnActionPerformed
    
    public void init(Main controller){
//        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("CSSECDV - SECURITY Svcs");
        this.setLocationRelativeTo(null);
        
        this.main = controller;
        loginPnl.frame = this;
        registerPnl.frame = this;
        
        adminHomePnl.init(main.sqlite);
        clientHomePnl.init(main.sqlite);
        managerHomePnl.init(main.sqlite);
        staffHomePnl.init(main.sqlite);
        
        Container.setLayout(frameView);
        Container.add(loginPnl, "loginPnl");
        Container.add(registerPnl, "registerPnl");
        Container.add(HomePnl, "homePnl");
        frameView.show(Container, "loginPnl");
        
        Content.setLayout(contentView);
        Content.add(new Home("WELCOME USER!", new java.awt.Color(255, 255, 255)), "home");
        
//        Content.remove(adminHomePnl);
        Content.add(adminHomePnl, "adminHomePnl");
        
//        Content.remove(managerHomePnl);
        Content.add(managerHomePnl, "managerHomePnl");
        
//        Content.remove(staffHomePnl);
        Content.add(staffHomePnl, "staffHomePnl");
        
//        Content.remove(clientHomePnl);
        Content.add(clientHomePnl, "clientHomePnl");
        
        this.getAccessMatrix();
        this.setVisible(true);
    }
    
    public void mainNav(){
        frameView.show(Container, "homePnl");
    }
    
    public void loginNav(){
        frameView.show(Container, "loginPnl");
    }
    
    public void registerNav(){
        frameView.show(Container, "registerPnl");
    }
    
    private void logOutProcedure()throws Exception{
         main.sqlite.removeSession(userSession);
         this.userSession = "";
    }
    
    
    private void getAccessMatrix(){
       HashMap<Integer, String> matrix = main.sqlite.getAccessRoles();
       this.accessMatrix = matrix;
           
    }
    
    
    public void registerAction(String username, String password, String confpass) throws Exception {
        main.sqlite.addUser(username, password, 2);
        SessionManager.createKeyOnRegistration(main.sqlite, username, username);
        this.logAction("SESSION_KEY", "SESSIONKEY", String.format("[SUCCESS] Session key for username = %s created.", username));
    }
   
    public boolean usernameExist(String username){
        if (main.sqlite.usernameExist(username)){
            this.logAction("ATTEMPT_USERNAME", username, String.format("[SUCCESS] Input username = %s verified.", username));
            return true;
        }
        
        this.logAction("ATTEMPT_USERNAME", username, String.format("[FAIL] Input username = %s does not exist.", username));
        return false;
    }
    
    public void createUserSession(String username) throws Exception {
        
        //get role of user
        int role = main.sqlite.getUserRole(username);
       
        //create a session instance
        Session session = new Session(username, role);
        

        //encrypt session object to string
        String encrypted = SessionManager.encrypt(main.sqlite, username, session);
        
        //create a session in sessions db (String session) that returns an ID
        String id = main.sqlite.addSession(username, encrypted, new PasswordHasher());
        this.logAction("CREATE_SESSION", username, String.format("[SUCCESS] Session created (ID: %s).", id));
        
        //pass ID to all panels 
        this.userSession = id;
        
        
    }
    
//    
    
    
    public boolean attemptLoginSuccessful(String username, String password){
        
        if (this.usernameExist(username)){
            
            //check if username and password is correct
            if(!main.sqlite.isLoginSuccessful(username, password)){


                this.logAction("ATTEMPT_LOGIN", username, String.format("[FAIL] Username and password not matched."));
                //if not, increase locked 
                int value = main.sqlite.increaseUserLock(username, MAX_LOGIN);
                this.logAction("ATTEMPT_INCREASE", username, String.format("Number of Attempts for User %s = %d", username, value));

                if(value >= MAX_LOGIN){
                    //if locked > MAX_LOGIN, lock user
    //                main.sqlite.lockUser(username, MAX_TIMEOUT);
                    this.logAction("USER_LOCK", username, String.format("[FAIL] User %s locked due to max login attempts.", username));
                }

                return false;
            }

            this.logAction("ATTEMPT_LOGIN", username, String.format("[SUCCESS] Attempted Username and password matched."));
            return true;
 
        }
        
        return false;
    }
    
    
    public boolean checkIfUSerLocked(String username){
        if(!main.sqlite.isUserUnlocked(username)){
            int value = main.sqlite.increaseUserLock(username, MAX_LOGIN);
            this.logAction("USER_LOCK", username, String.format("[FAIL] User = %s currently locked from logging in.", username));
            this.logAction("ATTEMPT_INCREASE", username, String.format("Number of Attempts for User %s = %d", username, value));
            return false;
        }
        return true;
    }
    
    
//    
//    public boolean attemptUnlockSuccessful(String username){
//        if(!main.sqlite.isUserUnlocked(username, MAX_LOGIN)){
//            
//            this.logAction("USER_LOCK", username, String.format("[FAIL] User = %s currently locked from logging in.", username));
//            
//            if(main.sqlite.tryUnlock(username)){
//                this.logAction("ATTEMPT_UNLOCK", username, String.format("[SUCCESS] User = %s unlocked due to expired timeout.", username));
//                return true;
//            }
//            this.logAction("ATTEMPT_UNLOCK", username, String.format("[FAIL] Current timeout is not yet expired."));
//            return false;
//        }
//        
//        this.logAction("USER_LOCK", username, String.format("[SUCCESS] User = %s is not locked from logging in.", username));
//        return true;
//    }
    
    public void logAction(String event, String username, String desc){
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getDefault()); 
        String date = sdf.format(new Date());
        
        main.sqlite.addLogs(event, username, desc, date);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Container;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel HomePnl;
    private javax.swing.JPanel Navigation;
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton clientBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton managerBtn;
    private javax.swing.JButton staffBtn;
    // End of variables declaration//GEN-END:variables
}
