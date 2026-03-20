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
        
        Integer tasksDONE = getTasks().stream()
        .filter(task -> task.getStatus()==TaskStatus.DONE)
        .count();

        float ratio = (tasksDONE/ (getTasks().size() ));

        String projectHealth = Float.toString(ratio);
        String.format("%.2f %%", projectHealth);

        return projectHealth;
    }

    public TaskStatus getBottleneck(){
        /*Identificar qual o status que possui o maior número de tarefas no sistema (exceto DONE) */
        TaskStatus Bottlenecked = TaskStatus.BLOCKED;

        
        int countBlocked = getTasks().stream().filter(task -> task.getStatus() == TaskStatus.BLOCKED).count();
        int countTODO = getTasks().stream().filter(task -> task.getStatus() == TaskStatus.TO_DO).count();
        int countINPROGRESS = getTasks().stream().filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS).count();

        //TODO: recisa retornar o maior Status; Esperando peds responderem o que é pra fazer em caso de empate
        return Bottlenecked;
    }
}