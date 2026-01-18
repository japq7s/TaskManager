package com.project.TaskManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:taskmanager.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        conn.createStatement().execute("PRAGMA foreign_keys = ON");
        return conn;
    }

    public static void initialize() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password_hash TEXT NOT NULL)";

        String createTeams = "CREATE TABLE IF NOT EXISTS teams (" +
                "team_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "team_name TEXT NOT NULL)";

        String createMembers = "CREATE TABLE IF NOT EXISTS team_members (" +
                "user_id INTEGER, " +
                "team_id INTEGER, " +
                "PRIMARY KEY (user_id, team_id), " +
                "FOREIGN KEY(user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY(team_id) REFERENCES teams(team_id))";

        String createTasks = "CREATE TABLE IF NOT EXISTS tasks (" +
                "task_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "description TEXT NOT NULL, " +
                "status TEXT DEFAULT 'PENDING', " +
                "team_id INTEGER, " +
                "assigned_to INTEGER, " +
                "FOREIGN KEY(team_id) REFERENCES teams(team_id), " +
                "FOREIGN KEY(assigned_to) REFERENCES users(user_id))";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsers);
            stmt.execute(createTeams);
            stmt.execute(createMembers);
            stmt.execute(createTasks);
            System.out.println("database tables done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}