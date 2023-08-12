package org.example.model;

import java.util.Map;

public class Team {
    private final Map<Department, Employee> teamMembers;


    public Team(Map<Department, Employee> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Map<Department, Employee> getTeamMembers() {
        return teamMembers;
    }
}
