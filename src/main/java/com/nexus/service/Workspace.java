package com.nexus.service;

import com.nexus.Main;
import com.nexus.exception.NexusValidationException;
import com.nexus.model.Project;
import com.nexus.model.Task;
import com.nexus.model.TaskStatus;
import com.nexus.model.User;

import java.util.ArrayList;
import java.util.List;
// import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.HashMap;




public class Workspace {
    private List<Task> tasks = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public void addTask(Task task) {

        task.incrementTotalTasksCreated();
        tasks.add(task);
    }

    public void addProjects( Project pproj){
        projects.add(pproj);
    }

    public List<Task> getTasks() {
        // Retorna uma visão não modificável para garantir encapsulamento
        return Collections.unmodifiableList(tasks);
    }

    public List<Project> getProjects(){

        return Collections.unmodifiableList(projects);


    }

    public List<User> getTopPerformers(){
        /*Um método que retorna os 3 usuários que possuem o maior número de tarefas no status DONE */
        List<User> allUsers = Main.getUsers(); 
        
        List<User> topThree = allUsers.stream()
        .sorted((a, b) -> Long.compare(b.countDoneTasks(), a.countDoneTasks()))
        .limit(3)
        .collect(Collectors.toList());

        return topThree; //o desempate fica a critério dos próprios mecanismos do stream

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

        if(getTasks().size()==0){
            return "";
        }
        
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

        Long maxTask =  Collections.max(statusmap.keySet()); //o desempate será feito a critério dos mecanismos do próprio HashMap

        return statusmap.get(maxTask);
        
    }
    public Integer Project_existe(String nome_project){
        Integer size_proj = projects.size();
        for(int i = 0; i < size_proj; i++){
            if(projects.get(i).getNome().equals(nome_project)){
                return i;
            }
        }

        return -1;        
    }
    public void AddTaskProject(Task task, Integer Posi_project_add){
        projects.get(Posi_project_add).addTask(task);
        return;
    }

    public void setTaskUser(Task tarefa, String user, List<User> lista_users){
        User user_receber = User.user_existe(user, lista_users);
        tarefa.setOwner(user_receber);
       
    }

    public void change_status(Task tarefa_trocar, String novo_status){
        switch(novo_status){
            case "IN_PROGRESS" ->{  
                  tarefa_trocar.moveToInProgress();
                
            }
            case "BLOCKED" -> {
                tarefa_trocar.setBlocked();
            }
            case "DONE" ->{
                tarefa_trocar.markAsDone();
            }
            default -> new NexusValidationException("Status " + novo_status + " nao e um status valido");



        }


        return;
    }

    public Task getTask_by_ID(Integer id, List<Task> lista_tarefas){
        Integer tmnh_lista = lista_tarefas.size();
        for(int i = 0; i < tmnh_lista; i++){
            if (lista_tarefas.get(i).getId() == id){
                return lista_tarefas.get(i);
            }

        }
        throw new NexusValidationException("Tarefa com Id " + id + " nao encontrada");
    }
}