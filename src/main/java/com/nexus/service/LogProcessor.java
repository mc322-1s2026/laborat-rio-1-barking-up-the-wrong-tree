package com.nexus.service;

import com.nexus.model.*;
// import com.nexus.service.*;
import com.nexus.exception.NexusValidationException;
import java.io.IOException;
import java.time.LocalDate;
// import java.time.LocalDate;
import java.util.List;

public class LogProcessor {

    public void processLog(String fileName, Workspace workspace, List<User> users) {
        try {
            // Busca o arquivo dentro da pasta de recursos do projeto (target/classes)
            var resource = getClass().getClassLoader().getResourceAsStream(fileName);
            
            if (resource == null) {
                throw new IOException("Arquivo não encontrado no classpath: " + fileName);
            }

            try (java.util.Scanner s = new java.util.Scanner(resource).useDelimiter("\\A")) {
                String content = s.hasNext() ? s.next() : "";
                List<String> lines = List.of(content.split("\\R"));
                
                for (String line : lines) {
                    if (line.isBlank() || line.startsWith("#")) continue;

                    String[] p = line.split(";");
                    String action = p[0];

                    try {
                        switch (action) {
                            case "CREATE_USER" -> {
                                try {
                                    User user = new User(p[1], p[2]);
                                    users.add(user);
                                    System.out.println("[LOG] Usuário criado: " + p[1]);
                                } catch(NexusValidationException e) {
                                    System.err.println("[ERRO] " + e.getMessage());
                                }
                            }
                            
                            case "CREATE_TASK" -> {
                                String taskName = p[1];
                                String deadline = p[2];                                
                                String effort = p[3];
                                String projectName = p[4]; 
                                LocalDate deadlineDate = LocalDate.parse(deadline);
                                Integer effortInt = Integer.parseInt(effort);
                                Task nova_task = new Task(taskName, deadlineDate, effortInt);
                                //TODO: bug de tarefa nao entrar em projeto e ainda existir
                                Integer index = workspace.Project_existe(projectName);
                                if(index != -1){
                                    workspace.AddTaskProject(nova_task, index);
                                    workspace.addTask(nova_task);
                                }
                                else{
                                    throw new NexusValidationException("Projeto nao existente");
                                }

                                
                                System.out.println("Task " + taskName + " com sucesso e adicionada ao projeto " + projectName);
                            }
                            
                            
                            case "ASSIGN_USER" -> {
                                String taskId = p[1];
                                String username = p[2];
                                Integer taskIdInt = Integer.parseInt(taskId);
                                Task tarefa_id = workspace.getTask_by_ID(taskIdInt, workspace.getTasks());
                                try{
                                    workspace.setTaskUser(tarefa_id, username, users);
                                } catch(NexusValidationException e) {
                                    System.err.println("[ERRO] " + e.getMessage());
                                }

                                System.out.println("Tarefa " + tarefa_id.getTitle() +  " atribuida ao user " + username);
                            }
                            case "CHANGE_STATUS" -> {
                                String taskId = p[1];
                                String newStatus = p[2];
                                Integer taskIdInt = Integer.parseInt(taskId);
                                Task tarefa_id = workspace.getTask_by_ID(taskIdInt, workspace.getTasks());
                                workspace.change_status(tarefa_id, newStatus);
                                System.out.println("Tarefa " + tarefa_id.getTitle() + " movido para " + newStatus);
                                
                                
                                

                            }
                            case "REPORT_STATUS" -> {
                                
                        
                                String projectHealth = workspace.getProjectHealth();
                                if(projectHealth==""){
                                    System.out.println("\nSaúde do Projeto: Não há tarefas no momento!");
                                } else{
                                    System.out.println("\nSaúde do Projeto " + projectHealth);
                                }

                                List<User> topPerformers = workspace.getTopPerformers();
                                System.out.println("\nTop Performers: ");
                                for(User user : topPerformers) { System.out.println(user); }


                                List<User> overloaded = workspace.getOverloaded();
                                System.out.println("\nOverloaded: ");
                                if(overloaded.size()==0){ //o java é bizarro, não deixa fazer if(!) com int
                                    System.out.println("Não há usuários cujas tarefas ultrapassem 10");
                                } else{
                                    for(User user : overloaded) { System.out.println(user); }
                                }


                                TaskStatus status = workspace.getBottleneck();
                                System.out.println("\nGlobal Bottleneck: " + status);
                            }

                            case "CREATE_PROJECT"-> {
                                String nome_project = p[1];
                                String effort = p[2];
                                Integer effortInt = Integer.parseInt(effort);

                                workspace.addProjects(new Project(nome_project, effortInt));
                                System.out.println("Projeto " + nome_project + " criado");                            
                                }

                            default -> new NexusValidationException("[WARN] Ação desconhecida: " + action);
                        }
                    } catch (NexusValidationException e) {
                        System.err.println("[ERRO DE REGRAS] Falha no comando '" + line + "': " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[ERRO FATAL] " + e.getMessage());
        }
    }
}