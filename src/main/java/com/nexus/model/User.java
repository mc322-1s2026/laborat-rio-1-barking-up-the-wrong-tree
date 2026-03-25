package com.nexus.model;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import com.nexus.exception.NexusValidationException;
// import com.nexus.service.Workspace;
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

    /*
    * Retorna o email do usuário
    * @return: String email
    */
    public String consultEmail() {return email;}

    /*
    *Retorna o username do usuario
    * @return string Username
    */
    public String consultUsername() {return username;}

    /*
    * calcula o workload da task
    * @return: long workload
    */
    public long calculateWorkload(){
        ArrayList<Task> allTasks = Task.getAllTasks();

        long workload = allTasks.stream()
        .filter(obj -> obj.getOwner() == this && obj.getStatus().equals(TaskStatus.IN_PROGRESS))
        .count();

        return workload;
    }

    /*
    * Sobrescreve a função toString da classe. Quando se usa println nela, o output fica mais bonitinho
    */
    @Override
    public String toString() {
        return "Username: "+ this.username + "  |  " + "Address: " + this.email;
    }

    /*
    * Verifica se o email é valido usando expressões regulares
    * @param: String email - o email a se verificar
    * @return: True se for válido, false caso contrário
    */
    private boolean isEmailValid(String email){
        
        if(email == null || email.isBlank()){
            return false;
        }

        String emailRegEx = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailChecker = Pattern.compile(emailRegEx);

        return emailChecker.matcher(email).matches();
    }

    /*
    * Conta quantas tarefas foram marcadas como DONE
    * @return: long nofDoneTasks
    */
    public long countDoneTasks(){
        List<Task> userTasks = getUserTasks();

        long nofDoneTasks = userTasks.stream()
        .filter(obj -> obj.getStatus().equals(TaskStatus.DONE))
        .count();

        return nofDoneTasks;
    }

    /*
    * Retorna uma lista com todas as tarefas de um usuário
    * @return: List<Task> com as tarefas
    */
    public List<Task> getUserTasks(){
        ArrayList<Task> allTasks = Task.getAllTasks();

        List<Task> tasks = allTasks.stream()
        .filter(obj -> obj.getOwner() == this)
        .collect(Collectors.toList());

        return tasks;
    }

    /*
    * Verifica se um usuário existe pelo nome
    * @param: String user_desc - descroção
    * @param: List<User> lista_user
    */
    public static User user_existe(String user_desc, List<User> lista_user){
        Integer tmn_list = lista_user.size();
        for(int i = 0; i < tmn_list; i++){
            if (lista_user.get(i).consultUsername().equals(user_desc)){
                return lista_user.get(i);
            }

        }

        throw new NexusValidationException("User nao existe");
    }

   

}