package com.project.TaskManager;

import org.mindrot.jbcrypt.BCrypt; //haszuje hasla (blowfish ale w javie)
import java.sql.*;

public class UserManager {

    public boolean register(String username, String password) {
        if (userTaken(username)) {
            System.out.println("Username is taken");
            return false;
        }

        String sql = "INSERT INTO users(username, password_hash) VALUES(?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            pstmt.setString(1, username);
            pstmt.setString(2, hashed);
            pstmt.executeUpdate();
            System.out.println("User registered: " + username);
            return true;
        } 
        catch (SQLException e) {
            System.out.println("registration failed");
            return false;
        }
    }

    public int login(String username, String password) {
        String sql = "SELECT user_id, password_hash FROM users WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                if (BCrypt.checkpw(password, rs.getString("password_hash"))) {
                    System.out.println("Login successful :D");
                    return rs.getInt("user_id");
                }
            }
        } 
        catch (SQLException e) { e.printStackTrace(); }
        
        System.out.println("Invalid info, login failed");
        return -1;
    }
    private boolean userTaken(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
            
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}