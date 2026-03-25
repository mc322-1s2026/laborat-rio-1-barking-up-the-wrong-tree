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

        if(nome.isEmpty() || nome == null){
            throw new NexusValidationException("Nome do projeto nao pode ser vazio");
        }
        if( maxWorkload < 0){
            throw new NexusValidationException("A cagra horaria maxima tem que ser um numero natural");
        }
        this.Nome = nome;
        this.maxWorkload = maxWorkload;
        this.currentWorkload = 0;
    }


    /*
    * Adiciona uma task
    * @param: Task task - task a qual se deseja adicionar
    */
    public void addTask(Task task){
        if (getCurrentWorkload() + task.getEffort() > maxWorkload){
            
            throw new NexusValidationException("Ultrapassou o limite de workload");
        }

        this.Tarefas.add(task);
        currentWorkload += task.getEffort();
        
    }


    //Getters

    /* 
    * retorna uma list com todas as tarefas
    * @return: List<Task> com todas as tasks do projeto
    */
    public List<Task> getTarefas(){
        return this.Tarefas;
    }

    /*
    * retorna o nome do projeto
    */
    public String getNome(){
        return this.Nome;
    }

    /*
    * Retorna o workload maximo
    * @return: maxworkload
    */
    public int getMaxWorkload(){
        return this.maxWorkload;
    }

    /*
    * Retorna o workload atual
    * @return: currentWorkload
    */
    public int getCurrentWorkload(){

        return this.currentWorkload;
    }

    /*
    * Retorna o budgetTotal
    * @return: totalBudget
    */
    public float getTotalBudget(){
        return this.totalBudget;
    }



    


}
