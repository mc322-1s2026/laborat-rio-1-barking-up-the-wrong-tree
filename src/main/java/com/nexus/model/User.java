package com.nexus.model;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import com.nexus.exception.NexusValidationException;
import com.nexus.service.Workspace;
import java.util.ArrayList;


public class User {

    private final String username;
    private final String email;

    public User(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new NexusValidationException("Username não pode ser vazio.");
        }

        if(!isEmailValid(email)){
            throw new NexusValidationException("email inválido.");
        }

    
        this.username = username;
        this.email = email;
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

    @Override
    public String toString() {
        return "Username: "+ this.username + "|" + "Address: " + this.email;
    }

    private boolean isEmailValid(String email){
        
        if(email == null || email.isBlank()){
            return false;
        }

        String emailRegEx = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailChecker = Pattern.compile(emailRegEx);

        return emailChecker.matcher(email).matches();
    }

    public long countDoneTasks(){
        List<Task> userTasks = getUserTasks();

        long nofDoneTasks = userTasks.stream()
        .filter(obj -> obj.getStatus().equals(TaskStatus.DONE))
        .count();

        return nofDoneTasks;
    }

    public List<Task> getUserTasks(){
        ArrayList<Task> allTasks = Task.getAllTasks();

        List<Task> tasks = allTasks.stream()
        .filter(obj -> obj.getOwner() == this)
        .collect(Collectors.toList());

        return tasks;
    }

   

}