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
            + " locked INTEGER DEFAULT 0\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
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
    
//    public boolean tryUnlock(String username){
//        try (Connection conn = DriverManager.getConnection(driverURL)){
//            
//            String sql = "SELECT lockout_time FROM users WHERE username = ?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, username);
//         
//            
//            ResultSet rs = pstmt.executeQuery();
//            
//            if(rs.next()){
//                
//                String retrievedDateString = rs.getString("lockout_time");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                sdf.setTimeZone(TimeZone.getDefault()); // Use the current time zone
//                
//                Date retrievedDate = sdf.parse(retrievedDateString);
//                Date now = new Date();
//
//                //if date now is before timeout date, return false (failed attempt)
//                if (now.before(retrievedDate))
//                    return false;
//  
//                //else now is equal or after timout date, unlock user
//                else {
//                    
////                  unlock user
//                    sql = "UPDATE users SET locked = ?, lockout_time = ? WHERE username = ?";
//                    pstmt = conn.prepareStatement(sql);
//                    pstmt.setInt(1, 0);
//                    pstmt.setNull(2, java.sql.Types.VARCHAR);
//                    pstmt.setString(3, username);
//                    
//                    pstmt.executeUpdate();
//              
//                    return true;
//                }
//
//            }
//
//        } catch (Exception ex) {
//            System.out.print(ex);
//        }
//        
//        return false;
//    }
    
    public void lockUser(String username) throws Exception {
  
        Connection conn = DriverManager.getConnection(driverURL);
        String sql = "UPDATE users SET locked = ?, role = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        Random random = new Random();
        pstmt.setInt(1, random.nextInt(1, Integer.MAX_VALUE));
        pstmt.setInt(2, 1);
        pstmt.setString(3, username);

        pstmt.executeUpdate();

        
//        try (Connection conn = DriverManager.getConnection(driverURL)){
//            
//            String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ? AND locked = ? AND role != ?)";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, username);
//            pstmt.setInt(2, 0);
//            pstmt.setInt(3, 1);
//         
//            ResultSet rs = pstmt.executeQuery();
//            
//            if(rs.next()){
//                
//                if(rs.getBoolean(1)){
//                    
//                    
//                }
//            }
//            
//           
//
//        } catch (Exception ex) {
//            System.out.print(ex);
//        }
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
    
    public void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, role); 
            pstmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username=(?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            pstmt.executeUpdate();
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public Product getProduct(String name){
        String sql = "SELECT name, stock, price FROM product WHERE name='" + name + "';";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            product = new Product(rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price"));
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return product;
    }
}