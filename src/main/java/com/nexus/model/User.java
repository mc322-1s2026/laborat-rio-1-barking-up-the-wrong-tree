package com.nexus.model;
import java.util.List;
import java.util.regex.Pattern;
import com.nexus.service.Workspace;
import java.util.ArrayList;


public class User {

    private final String username;
    private final String email;

    public User(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username não pode ser vazio.");
        }

        if(!isEmailValid(email)){
            throw new IllegalArgumentException("email inválido.");
        }

    
        this.username = username;
        this.email = email;
        allUsers.add(this);
    }

    public String consultEmail() {return email;}

    public String consultUsername() {return username;}


    public long calculateWorkload(){
        ArrayList<Task> allTasks = Task.getAllTasks();

        long workload = allTasks.stream()
        .filter(obj -> obj.getOwner() == this && obj.getStatus().equals(TaskStatus.IN_PROGRESS))
        .count();

        return workload;
    }

    private boolean isEmailValid(String email){
        
        if(email == null || email.isBlank()){
            return false;
        }

        String emailRegEx = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailChecker = Pattern.compile(emailRegEx);

        return emailChecker.matcher(email).matches();
    }

   

}