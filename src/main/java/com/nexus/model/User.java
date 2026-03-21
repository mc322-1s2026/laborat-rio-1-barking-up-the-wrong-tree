package com.nexus.model;
import java.util.List;
import java.util.regex.Pattern;

import com.nexus.exception.NexusValidationException;
import com.nexus.service.Workspace;

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

    public static void calculateWorkload(Workspace workspace, User usuario) {
        Integer workload_verificar = 0;
        List<Task> tasks = workspace.getTasks();
        for (Task t : tasks){
            if (usuario == t.getOwner()){
                if(t.getStatus().equals(TaskStatus.IN_PROGRESS)){
                    workload_verificar += 1;
                }
            }
        
            System.out.println("O usuario " + usuario.username + "tem um workload de: " + workload_verificar);
        }
        return; 
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