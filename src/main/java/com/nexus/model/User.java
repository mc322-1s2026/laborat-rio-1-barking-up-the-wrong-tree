package com.nexus.model;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.nexus.service.Workspace;

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
    }

    public String consultEmail() {
        return email;
    }

    public String consultUsername() {
        return username;
    }

    public static void calculateWorkload(Workspace workspace) {
        List<Task> tasks = workspace.getTasks();
        // TODO: Implementar forma de ler lista criada e contar quantas repeticoes de tarefas sao atribuidas ao usuario
        return; 
    }

    private boolean isEmailValid(String email){
        
        if(email == null || email.isBlank()){
            return false;
        }

        // String emailRegEx = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Pattern emailChecker = Pattern.compile(emailRegex);

        // return emailChecker.matcher(email).matches();
        return true;
    }


}