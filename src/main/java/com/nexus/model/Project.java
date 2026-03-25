package com.nexus.model;

import java.util.ArrayList;
import java.util.List;
// import com.nexus.model.Task;
import com.nexus.exception.*;

public class Project {

    private String Nome;
    private float totalBudget;
    private List<Task> Tarefas = new ArrayList<>();
    private Integer maxWorkload;
    private Integer currentWorkload;

    public Project(String nome, Integer maxWorkload){

        if( nome.isEmpty() || nome == null){
            throw new NexusValidationException("Nome do projeto nao pode ser vazio");
        }
        if( maxWorkload < 0){
            throw new NexusValidationException("A cagra horaria maxima tem que ser um numero natural");
        }
        this.Nome = nome;
        this.maxWorkload = maxWorkload;
        this.currentWorkload = 0;
    }


    public void addTask(Task task){
        if (getCurrentWorkload() + task.getEffort() > maxWorkload){
            Task.destruir_task(task);
            throw new NexusValidationException("Ultrapassou o limite de workload");
            
        }

        this.Tarefas.add(task);
        currentWorkload += task.getEffort();
        
    }


    //Getters

    public List<Task> getTarefas(){
        return this.Tarefas;
    }

    public String getNome(){
        return this.Nome;
    }

    public int getMaxWorkload(){
        return this.maxWorkload;
    }

    public int getCurrentWorkload(){

        return this.currentWorkload;
    }

    public float getTotalBudget(){
        return this.totalBudget;
    }



    


}
