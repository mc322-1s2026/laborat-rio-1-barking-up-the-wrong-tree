package com.nexus.model;

import java.util.ArrayList;
import java.util.List;
import com.nexus.model.Task;

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
        return;
        //TODO: Implementar verificacoes e validacoes na criacao de projeto!
    }


    public void addTask(Task task){
        if (currentWorkload + task.getEffort() > maxWorkload){
            throw new NexusValidationException("Ultrapassou o limite de workload")
        }
        else{
            this.Tarefas.add(task);
            currentWorkload += task.getEffort();
        }
        return;
    }


    


}
