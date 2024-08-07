/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.PasswordHasher;
import Controller.PasswordValidator;
import Controller.SQLite;
import Controller.SessionManager;
import Model.Logs;
import Model.Session;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author beepxD
 */
public class MgmtLogs extends javax.swing.JPanel {

    public SQLite sqlite;
    public DefaultTableModel tableModel;
    private String session = "";
    private Session currentSession;
    private final PasswordValidator validator = new PasswordValidator();
    private final PasswordHasher hasher = new PasswordHasher();
    
    public MgmtLogs(SQLite sqlite) {
        initComponents();
        this.sqlite = sqlite;
        tableModel = (DefaultTableModel)table.getModel();
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        
//        UNCOMMENT TO DISABLE BUTTONS
//        clearBtn.setVisible(false);
//        debugBtn.setVisible(false);
    }

    public void init(String session){
        this.session = session;
        
        //      CLEAR TABLE
        for(int nCtr = tableModel.getRowCount(); nCtr > 0; nCtr--){
            tableModel.removeRow(0);
        }
        
//      LOAD CONTENTS
        ArrayList<Logs> logs = sqlite.getLogs();
        for(int nCtr = logs.size() - 1; nCtr >= 0; nCtr--){
            tableModel.addRow(new Object[]{
                logs.get(nCtr).getEvent(), 
                logs.get(nCtr).getUsername(), 
                logs.get(nCtr).getDesc(), 
                logs.get(nCtr).getTimestamp()});
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        clearBtn = new javax.swing.JButton();
        debugBtn = new javax.swing.JButton();

        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Event", "Username", "Description", "Timestamp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(80);
            table.getColumnModel().getColumn(1).setPreferredWidth(160);
            table.getColumnModel().getColumn(2).setPreferredWidth(400);
            table.getColumnModel().getColumn(3).setPreferredWidth(240);
        }

        clearBtn.setBackground(new java.awt.Color(255, 255, 255));
        clearBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clearBtn.setText("CLEAR");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        debugBtn.setBackground(new java.awt.Color(255, 255, 255));
        debugBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        debugBtn.setText("ENABLE/DISABLE DEBUG MODE");
        debugBtn.setToolTipText("");
        debugBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debugBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(debugBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(clearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(debugBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    public void designer(JTextField component, String text){
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", 0, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
    }
    
    private boolean verifyUser(){
        try{
            Session current = SessionManager.checkSession(this.sqlite, this.session);
            this.currentSession = current;
            JPasswordField password = new JPasswordField();
            designer(password, "PASSWORD");
            
            Object[] message = {
                "Enter Your Own Password:", password
            };

            int result = JOptionPane.showConfirmDialog(null, message, "ENTER PASSWORD", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
            if(result == JOptionPane.OK_OPTION){
                String username = current.getUsername();
                String passText = new String(password.getPassword());
                try {
                    String hashedPassword = hasher.hash(hasher.hash(passText, "SHA-1"), "SHA-256");
                    if(this.sqlite.isLoginSuccessful(username, hashedPassword)){
                        this.logAction("VERIFY_PASSWORD", this.currentSession.getUsername(), String.format("[SUCCESS] Password Verficiation of User %s OK", username));
                        return true;
                    }
                    else throw new Exception("Wrong Password!");
                } catch (Exception ex){
                    this.logAction("VERIFY_PASSWORD", this.currentSession.getUsername(), String.format("[FAIL] Password Verficiation of User %s failed due to %s", username, ex.getMessage()));
                    JOptionPane.showMessageDialog(this, String.format("Wrong Password!"), "Verification Failed", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return false;
                }
            }
            
        } catch(Exception e){
            e.printStackTrace();
            this.logAction("VERIFY_PASSWORD", this.currentSession.getUsername(), String.format("[FAIL] Server Failure due to %s", e));
        }
        
        return false;
    }
    
    private void logAction(String event, String username, String desc){
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getDefault()); 
        String date = sdf.format(new Date());
        
        this.sqlite.addLogs(event, username, desc, date);
    }
    
    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the logs table?", "CLEAR LOGS TABLE", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                if(this.verifyUser()){
                    
                    try {
                        
                        this.sqlite.dropLogs();
                        JOptionPane.showMessageDialog(this, "Logs has been deleted.", "Clear Table Successful", JOptionPane.INFORMATION_MESSAGE);
                        this.logAction("CLEAR_LOGS", this.currentSession.getUsername(), String.format("[SUCCESS] Logs screen has been deleted by user %s", this.currentSession.getUsername()));
                        
                        //      CLEAR TABLE
                        for(int nCtr = tableModel.getRowCount(); nCtr > 0; nCtr--){
                            tableModel.removeRow(0);
                        }
                        
                    }catch(Exception e){
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, String.format("Logs has not been deleted due to %s.", e), "Clear Table Unsuccessful", JOptionPane.ERROR_MESSAGE);
                        this.logAction("CLEAR_LOGS", this.currentSession.getUsername(), String.format("[FAIL] Logs screen has not been deleted by user %s due to %s", this.currentSession.getUsername(), e));
                    
                    }
                    
                    
                    
                    
                    
                }
            }
    }//GEN-LAST:event_clearBtnActionPerformed

    private void debugBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debugBtnActionPerformed
        if(sqlite.DEBUG_MODE == 1)
            sqlite.DEBUG_MODE = 0;
        else
            sqlite.DEBUG_MODE = 1;
    }//GEN-LAST:event_debugBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton debugBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
