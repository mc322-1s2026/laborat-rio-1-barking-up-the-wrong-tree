package com.nexus.model;

import java.util.ArrayList;
import java.util.List;
import com.nexus.model.Task;

public class Project {
    public String Nome;
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


    public void addTask(Task t){
        Tarefas.add(t);
        if (currentWorkload + t.getEffort() > maxWorkload){
            //TODO: metodo de erro lancado por ultrapassar limite de workload
        }
        else{
            currentWorkload += t.getEffort();
        }
        return;
    }



}
