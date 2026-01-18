package com.project.TaskManager;


public class Main {
    public static void main(String[] args) {
        Database.initialize();

        UserManager userMgr = new UserManager();
        TeamManager teamMgr = new TeamManager();
        TaskManager taskMgr = new TaskManager();

        //  registerl&ogin
        userMgr.register("oskar", "zlotyStrzal5");
        userMgr.register("oskar", "zlotyStrzal5"); //username taken error
        int userId = userMgr.login("oskar", "zlotyStrzal5");

        if (userId != -1) {
            // team management
            int teamId = teamMgr.createTeam("GrupaNaOOP");
            teamMgr.joinTeam(userId, teamId);

            // task management
            taskMgr.addTask("zrob baze danych", teamId, userId);
            taskMgr.addTask("zrob frontend", teamId, userId);

            // view tasks
            taskMgr.viewMyTasks(userId);
        }
        int teamId = teamMgr.createTeam("Developers");
        teamMgr.joinTeam(userId, teamId); 
        //already a member error
        teamMgr.joinTeam(userId, teamId);   
    }
}