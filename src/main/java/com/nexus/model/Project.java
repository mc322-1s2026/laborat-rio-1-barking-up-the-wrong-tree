package com.nexus.model;

import java.util.ArrayList;
import java.util.List;
import com.nexus.model.Task;
import com.nexus.exception.*;

public class Project {

    public String Nome;
    public float totalBudget;
    private List<Task> Tarefas = new ArrayList<>();
    private Integer maxWorkload;
    private Integer currentWorkload;

    public Project(String nome, Integer maxWorkload){

        this.Nome = nome;
        this.maxWorkload = maxWorkload;
        this.currentWorkload = 0;
        //TODO: Implementar verificacoes e validacoes na criacao de projeto!
    }


    public void addTask(Task task){
        if (getCurrentWorkload() + task.getEffort() > maxWorkload){
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

        return this.getCurrentWorkload();
    }

    public float getTotalBudget(){
        return this.totalBudget;
    }



    


}
