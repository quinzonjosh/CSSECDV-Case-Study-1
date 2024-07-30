package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;

public class SQLite {
    
    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";
    
    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL,\n"
            + " name TEXT NOT NULL,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createLogsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " event TEXT NOT NULL,\n"
            + " username TEXT NOT NULL,\n"
            + " desc TEXT NOT NULL,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " name TEXT NOT NULL UNIQUE,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " price REAL DEFAULT 0.00\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL UNIQUE,\n"
            + " password TEXT NOT NULL,\n"
            + " role INTEGER DEFAULT 2,\n"
            + " failed_attempts INTEGER DEFAULT 0, \n"
            + " locked INTEGER DEFAULT 0,\n"
            + " session_key TEXT\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createSessionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS sessions (\n"
            + " id TEXT NOT NULL,\n"
            + " value TEXT NOT NULL,\n"
            + " created_at TEXT NOT NULL,\n"
            + " last_accessed TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table sessions in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void deleteProduct(String productName){
        String sql = "DELETE FROM product WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, productName);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.print(ex);
        }

    };

    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addHistory(String username, String name, int stock, String timestamp) {
        String sql = "INSERT INTO history(username,name,stock,timestamp) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setInt(3, stock);
            pstmt.setString(4, timestamp);
            
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, event);
            pstmt.setString(2, username);
            pstmt.setString(3, desc);
            pstmt.setString(4, timestamp);

            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name,stock,price) VALUES(?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, name);
            pstmt.setInt(2, stock);
            pstmt.setDouble(3, price);
            
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addUser(String username, String password) {
        String sql = "INSERT INTO users(username,password) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addSessionKeyToUser(String username, String key) throws Exception {
        Connection conn = DriverManager.getConnection(driverURL);
        String sql = "UPDATE users SET session_key = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, key);
        pstmt.setString(2, username);

        pstmt.executeUpdate();
        
        conn.close();
        
    }
    
    public String getSessionKeyUsingName(String username) throws Exception {
        
        Connection conn = DriverManager.getConnection(driverURL);
        
        String sql = "SELECT session_key FROM users WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);


        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next()){
            String key = rs.getString(1);
            conn.close();
            return key;
        }
       
        return "";
    
    }
    
    public String getSessionKeyUsingID(String id) throws Exception {
        
        Connection conn = DriverManager.getConnection(driverURL);
        
        String sql = "SELECT users.session_key "
                + "FROM users "
                + "INNER JOIN session_user_bridge ON users.id=session_user_bridge.user_id "
                + "WHERE session_user_bridge.session_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);


        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next()){
            String key = rs.getString(1);
            conn.close();
            return key;
        }
       
        return "";
    
    }
    
    public int getUserRole(String username) throws Exception {
        Connection conn = DriverManager.getConnection(driverURL);
        
        String sql = "SELECT role FROM users WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);


        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next()){
            int role = rs.getInt(1);
            conn.close();
            return role;
        }
       
        throw new Exception("No role found.");
    }
    
    public String addSession(String username, String session, PasswordHasher hasher) throws Exception {
        
        //create an ID
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getDefault()); 
        String date = sdf.format(new Date());
        String id = hasher.hash(date, "SHA-256");
        
        String sql = "INSERT INTO sessions(id, value, created_at, last_accessed) VALUES(?, ?, ?, ?)";
        
        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, session);
        pstmt.setString(3, date); 
        pstmt.setString(4, date); 
        pstmt.executeUpdate();
        
        conn.close();
        
        int userID = this.getUserID(username);
        this.linkUserToSession(userID, id);
        
        return id;
    }
    
    private int getUserID(String username) throws Exception {
        Connection conn = DriverManager.getConnection(driverURL);
        
        String sql = "SELECT id FROM users WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);


        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next()){
            int value = rs.getInt(1);
            conn.close();
            return value;
        }
       
        throw new Exception("No id found.");
    } 
    
    private void linkUserToSession(int id, String session) throws Exception{
        
        String sql = "INSERT INTO session_user_bridge(session_id, user_id) VALUES(?, ?)";
        
        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, session);
        pstmt.setInt(2, id);
 
        pstmt.executeUpdate();
        
        conn.close();
        
    }
    
    public String getSession(String id) throws Exception{
        Connection conn = DriverManager.getConnection(driverURL);
        
        String sql = "SELECT value FROM sessions WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);


        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next()){
            String value = rs.getString(1);
            conn.close();
            return value;
        }
       
        throw new Exception("No value found.");
    }
    
    private void removeLinkToSession(String id) throws Exception {
        String sql = "DELETE FROM session_user_bridge WHERE session_id=(?)";
        
        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, id);
            
        pstmt.executeUpdate();
        conn.close();
    }
    
    public void removeSession(String id) throws Exception {
        
        this.removeLinkToSession(id);
        
        String sql = "DELETE FROM sessions WHERE id=(?)";
        
        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, id);
            
        pstmt.executeUpdate();
        conn.close();
        
    }
    
    public boolean usernameExist(String username){
        String sql = "SELECT username FROM users WHERE LOWER(username) = LOWER(?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, username);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
//                System.out.println("User: " + rs.getString("username") + " exist.");
                return true;
            }

        } catch (Exception ex) {
            System.out.print(ex);
        }
        
        return false;
    }
    
    public boolean isLoginSuccessful(String username, String password){
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ? AND password = ?)";
        try (Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
//                System.out.println("User: " + rs.getString("username") + " exist.");
                return rs.getBoolean(1);
            }

        } catch (Exception ex) {
            System.out.print(ex);
        }
        
        return false;
    }
    
    public int increaseUserLock(String username, int max){
        try (Connection conn = DriverManager.getConnection(driverURL)){
            
            String sql = "SELECT failed_attempts FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
         
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
//                System.out.println("User: " + rs.getString("username") + " exist.");
                int failed = rs.getInt(1) + 1;
 
                sql = "UPDATE users SET failed_attempts = ? WHERE username = ?";
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, failed);
                pstmt.setString(2, username);
                
                pstmt.executeUpdate();
//                conn.close();
                
                if(failed >= max){
                    conn.close();
                    if(this.isUserUnlocked(username)){
                        this.lockUser(username);
                    }
                }
                    
                
                return failed;
            }

        } catch (Exception ex) {
            System.out.print(ex);
        }
        
        return -1;
    }
    
    public boolean isUserUnlocked(String username){
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ? AND locked = ? AND role != ?)";
        try (Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, username);
            pstmt.setInt(2, 0);
            pstmt.setInt(3, 1);
            
            ResultSet rs = pstmt.executeQuery();
            
            
            if(rs.next()){
//                System.out.println("User: " + rs.getString("username") + " exist.");
                return rs.getBoolean(1);
            }
            
            

        } catch (Exception ex) {
            System.out.print(ex);
        }
        
        return false;
    }
    
    public void lockUser(String username) throws Exception {
  
        Connection conn = DriverManager.getConnection(driverURL);
        String sql = "UPDATE users SET locked = ?, role = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        Random random = new Random();
        pstmt.setInt(1, random.nextInt(1, Integer.MAX_VALUE));
        pstmt.setInt(2, 1);
        pstmt.setString(3, username);

        pstmt.executeUpdate();

    }
    
    public void unlockUser(String username) throws Exception {
        Connection conn = DriverManager.getConnection(driverURL);
        String sql = "UPDATE users SET locked = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, 0);
        pstmt.setString(2, username);

        pstmt.executeUpdate();
    }
    
    public void changeUserRole(String username, char role) throws Exception {
        
        Connection conn = DriverManager.getConnection(driverURL);
        String sql = "UPDATE users SET role = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, Integer.parseInt(String.valueOf(role)));
        pstmt.setString(2, username);

        pstmt.executeUpdate();
        
    }
    
    public void changeUserPassword(String username, String password) throws Exception{
        Connection conn = DriverManager.getConnection(driverURL);
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, password);
        pstmt.setString(2, username);

        pstmt.executeUpdate();
    }
    
    public HashMap<Integer, String> getAccessRoles() {
        String sql = "SELECT code, access FROM access_roles";
        HashMap<Integer, String> accessMatrix = new HashMap<>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                accessMatrix.put(rs.getInt("code"), rs.getString("access"));
            }
           
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return accessMatrix;
    }
    
    public ArrayList<History> getHistory(){
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList<History> histories = new ArrayList<History>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return histories;
    }
    
    public ArrayList<History> getHistory(String username){
        String sql = "SELECT id, username, name, stock, timestamp FROM history"
                + " WHERE username = LOWER(?)";
        ArrayList<History> histories = new ArrayList<History>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, username);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return histories;
    }
    
    public ArrayList<Logs> getLogs(){
        String sql = "SELECT id, event, username, desc, timestamp FROM logs";
        ArrayList<Logs> logs = new ArrayList<Logs>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                                   rs.getString("event"),
                                   rs.getString("username"),
                                   rs.getString("desc"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return logs;
    }
    
    public ArrayList<Product> getProduct(){
        String sql = "SELECT id, name, stock, price FROM product";
        ArrayList<Product> products = new ArrayList<Product>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return products;
    }
    
    public ArrayList<User> getUsers(){
        String sql = "SELECT id, username, password, role, failed_attempts, locked FROM users";
        ArrayList<User> users = new ArrayList<User>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("password"),
                                   rs.getInt("role"),
                                   rs.getInt("failed_attempts"),
                                   rs.getInt("locked")));
//                System.out.println("Attempts: " + users.getLast().getFailed_attempts());
            }
        } catch (Exception ex) {}
        return users;
    }
    
    public void addUser(String username, String password, int role) throws Exception {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";
        
        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setInt(3, role); 
        pstmt.executeUpdate();
        
        conn.close();
       
    }
    
    public void removeUser(String username) throws Exception {
        String sql = "DELETE FROM users WHERE username=(?)";
        
        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, username);
            
        pstmt.executeUpdate();
        System.out.println("User " + username + " has been deleted.");
    }
    
    public Product getProduct(String name){
        String sql = "SELECT name, stock, price FROM product WHERE name=?;";
        
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ){
            
            pstmt.setString(1, name);
            
            ResultSet rs = pstmt.executeQuery();
            product = new Product(rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price"));
        } catch (Exception ex) {
            System.out.print(ex);
        }
        
        
        
        return product;
    }

    public void updateProduct(String productName, int purchasedAmt){
        String sql = "UPDATE product SET stock = stock - ? WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, purchasedAmt);
            pstmt.setString(2, productName);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.print(ex);
        }

    }

    public void updateProduct(String oldProductName, String newProductName, int stock, float price){
        String sql = "UPDATE product " +
                        "SET name = ?, " +
                            "stock = ?, " +
                            "price = ? " +
                        "WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, newProductName);
            pstmt.setInt(2, stock);
            pstmt.setFloat(3, price);
            pstmt.setString(4, oldProductName);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

}