package com.project.TaskManager;

import java.sql.*;
import org.springframework.stereotype.Service;

@Service
public class TaskManager {

    public void addTask(String description, int teamId, int assignedUserId) {
        String sql = "INSERT INTO tasks(description, team_id, assigned_to) VALUES(?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setInt(2, teamId);
            pstmt.setInt(3, assignedUserId);
            pstmt.executeUpdate();
            System.out.println("Task added!");
        } 
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void viewMyTasks(int userId) {
        String sql = "SELECT t.task_id, t.description, t.status, tm.team_name " +
                     "FROM tasks t " +
                     "JOIN teams tm ON t.team_id = tm.team_id " +
                     "WHERE t.assigned_to = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\n~~~~ MY TASKS ~~~~");
            while (rs.next()) {
                System.out.printf("[%s] %s (Team: %s)%n", 
                    rs.getString("status"), 
                    rs.getString("description"),
                    rs.getString("team_name"));
            }
            System.out.println("----------------\n");
        } 
        catch (SQLException e) { e.printStackTrace(); }
    }
}
