package com.project.TaskManager;

import java.sql.*;

public class TeamManager {

    public int createTeam(String teamName) {
        String sql = "INSERT INTO teams(team_name) VALUES(?)";  
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, teamName);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);   
        } 
        catch (SQLException e) { e.printStackTrace(); } return -1;
    }

    public void joinTeam(int userId, int teamId) {
        // check if they are already in
        if (isMember(userId, teamId)) {
            System.out.println( userId + " is already a member of " + teamId + "team");
            return;
        }
 
        //if !member, then add 

        String sql = "INSERT INTO team_members(user_id, team_id) VALUES(?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, teamId);
            pstmt.executeUpdate();
            System.out.println("Success: User " + userId + " joined team " + teamId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Could nat join dis team");
        }
    }

    // method to check membership
    private boolean isMember(int userId, int teamId) {
        String sql = "SELECT 1 FROM team_members WHERE user_id = ? AND team_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, teamId);
            ResultSet rs = pstmt.executeQuery(); 
            return rs.next(); // if rs.next() = 1, then user is a member
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
