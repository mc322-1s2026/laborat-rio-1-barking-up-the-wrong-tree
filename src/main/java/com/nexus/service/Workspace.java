package com.nexus.service;

import com.nexus.Main;
import com.nexus.model.Task;
import com.nexus.model.TaskStatus;
import com.nexus.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.HashMap;


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
        List<User> allUsers = Main.getUsers(); 
        


        return topPerformers; 

    }

    public List<User> getOverloaded(){
        /*Listar todos os usuários cuja carga de trabalho atual (IN_PROGRESS) ultrapassa 10 tarefas. */
        List<User> allUsers = Main.getUsers(); 
        
        List<User> overloaded = allUsers.stream()
        .filter(usr -> usr.calculateWorkload() > 10)
        .collect(Collectors.toList());

        return overloaded;

    }

    public String getProjectHealth(){
        /*Para um dado projeto, calcular o percentual de conclusão (Tarefas DONE / Total de Tarefas) */
        
        long tasksDONE = getTasks().stream()
        .filter(task -> task.getStatus()==TaskStatus.DONE)
        .count();

        float ratio = (tasksDONE/ (getTasks().size() ));

        String projectHealth = Float.toString(ratio);
        String.format("%.2f %%", projectHealth);

        return projectHealth;
    }

    public TaskStatus getBottleneck(){
        /*Identificar qual o status que possui o maior número de tarefas no sistema (exceto DONE) */

        long countBlocked = getTasks().stream().filter(task -> task.getStatus() == TaskStatus.BLOCKED).count();
        long countTODO = getTasks().stream().filter(task -> task.getStatus() == TaskStatus.TO_DO).count();
        long countINPROGRESS = getTasks().stream().filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS).count();

        HashMap<Long, TaskStatus> statusmap = new HashMap();

        statusmap.put(countBlocked, TaskStatus.BLOCKED);
        statusmap.put(countTODO, TaskStatus.TO_DO);
        statusmap.put(countINPROGRESS, TaskStatus.IN_PROGRESS);

        Long maxTask =  Collections.max(statusmap.keySet());

        return statusmap.get(maxTask);
        
    }
}