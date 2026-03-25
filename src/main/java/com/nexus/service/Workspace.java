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

    /*
    *Um método que retorna os 3 usuários que possuem o maior número de tarefas no status DONE 
    * @return: List<User> de tamanho 3 com os três usuários com as maiores notas
    */
    public List<User> getTopPerformers(){
       
        List<User> allUsers = Main.getUsers(); 
        
        List<User> topThree = allUsers.stream()
        .sorted((a, b) -> Long.compare(b.countDoneTasks(), a.countDoneTasks()))
        .limit(3)
        .collect(Collectors.toList());

        return topThree; //o desempate fica a critério dos próprios mecanismos do stream

    }

    /*
    * Listar todos os usuários cuja carga de trabalho atual (IN_PROGRESS) ultrapassa 10 tarefas
    * @return: List<User> overloaded
    */
    public List<User> getOverloaded(){
    
        List<User> allUsers = Main.getUsers(); 
        
        List<User> overloaded = allUsers.stream()
        .filter(usr -> usr.calculateWorkload() > 10)
        .collect(Collectors.toList());

        return overloaded;

    }

    /*
    *Para um dado projeto, calcular o percentual de conclusão (Tarefas DONE / Total de Tarefas)
    * @return: string
    */
    public String getProjectHealth(){
        
        
        long tasksDONE = getTasks().stream()
        .filter(task -> task.getStatus()==TaskStatus.DONE)
        .count();

        if(getTasks().size()==0){
            return "";
        }
        
        float ratio = (tasksDONE/ (getTasks().size() ));

        String projectHealth = String.format("%.2f %%", ratio);

        return projectHealth;
    }

    /*
    *Identificar qual o status que possui o maior número de tarefas no sistema (exceto DONE)
    * @return: (TaskStatus) - taskstatus com maior número de tarefas
    */
    public TaskStatus getBottleneck(){

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
    /*
    * Verifica se um projeto com determinado nome existe.
    * @param: Stringnome_project - nome do projeto
    * 
    * @return: -1 se não existe, inteiro arbitrário caso contrário
    */
    public Integer Project_existe(String nome_project){
        Integer size_proj = projects.size();
        for(int i = 0; i < size_proj; i++){
            if(projects.get(i).getNome().equals(nome_project)){
                return i;
            }
        }

        return -1;        
    }

    /*
    * Adiciona uma tarefa ao projeto
    * @param: Task task - a tarefa
    * @param: Integer posi_project_add - o projeto
    */
    public void AddTaskProject(Task task, Integer Posi_project_add){
        projects.get(Posi_project_add).addTask(task);
        return;
    }

    /*
    * Seta um usuário como dono de uma tarefa, se ele existir
    * @param: Task tarefa - a tarefa
    * @param: String user - o futuro dono
    * @param: List<User> lista_user - a lista de usuários
    */
    public void setTaskUser(Task tarefa, String user, List<User> lista_users){
        User user_receber = User.user_existe(user, lista_users);
        tarefa.setOwner(user_receber);
       
    }

    /*
    * Gerencia a mudança de status de uma tarefa
    * @param: Task tarefa_trocar - tarefa a qual se modificará
    * @param: String novo_status  - futuro status da tarefa
    */
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

    /*
    * Dado um ID, retorna a task com o ID correspondente
    * @param: Integer id - o id da task
    * @param: List<Task> lista_tarefas - a lista de tasks
    * @return: Task
    */
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