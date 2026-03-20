package com.nexus.service;

import com.nexus.model.Task;
import com.nexus.model.TaskStatus;
import com.nexus.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;


public class Workspace {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {

        task.incrementTotalTasksCreated();
        tasks.add(task);
    }

    public List<Task> getTasks() {
        // Retorna uma visão não modificável para garantir encapsulamento
        return Collections.unmodifiableList(tasks);
    }

    public List<User> getTopPerformers(){
        /*Um método que retorna os 3 usuários que possuem o maior número de tarefas no status DONE */
        List<User> topPerformers = null; //= getTasks().stream();
        //TODO

        return topPerformers; 

    }

    public List<User> getOverloaded(){
        /*Listar todos os usuários cuja carga de trabalho atual (IN_PROGRESS) ultrapassa 10 tarefas. */
        List<User> Overloaded = null; //getTasks().stream();
        //TODO

        return Overloaded;

    }

    public String getProjectHealth(){
        /*Para um dado projeto, calcular o percentual de conclusão (Tarefas DONE / Total de Tarefas) */
        String projectHealth = "%";
        //TODO

        return projectHealth;
    }

    public TaskStatus getBottleneck(){
        /*Identificar qual o status que possui o maior número de tarefas no sistema (exceto DONE) */
        TaskStatus Bottlenecked = TaskStatus.BLOCKED;
        //TODO

        return Bottlenecked;
    }
}