package com.nexus.service;

import com.nexus.model.*;
import com.nexus.service.*;
import com.nexus.exception.NexusValidationException;
import java.io.IOException;
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
                                users.add(new User(p[1], p[2]));
                                System.out.println("[LOG] Usuário criado: " + p[1]);
                            }
                            
                            // case "CREATE_TASK" -> {
                            //     Task t = new Task(p[1], LocalDate.parse(p[2]));
                            //     workspace.addTask(t);
                            //     System.out.println("[LOG] Tarefa criada: " + p[1]);
                            // }
                            case "CREATE_TASK" -> {
                                String taskName = p[1];
                                String deadline = p[2];                                
                                String effort = p[3];
                                String projectName = p[4]; 
                                
                                LocalDate deadlineDate = LocalDate.parse(deadline);

                                
                                System.out.println("Mexer dps");
                            }
                            case "ASSIGN_USER" -> {
                                String taskId = p[1];
                                String username = p[2];

                                System.out.println("Mexer dps");
                            }
                            case "CHANGE_STATUS" -> {
                                String taskId = p[1];
                                String newStatus = p[2];

                                System.out.println("Mexer dps");

                            }
                            case "REPORT_STATUS" -> {
                                System.out.println("Mexer dps");
                                
                                workspace.getTopPerformers();
                                workspace.getOverloaded();
                                workspace.getProjectHealth();
                                workspace.getBottleneck();
                            }

                            default -> System.err.println("[WARN] Ação desconhecida: " + action);
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