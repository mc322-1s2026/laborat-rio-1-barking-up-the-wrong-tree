package com.nexus.model;

import com.nexus.exception.*;
import java.time.LocalDate;

public class Task {
    // Métricas Globais (Alunos implementam a lógica de incremento/decremento)
    public static int totalTasksCreated = 0;
    public static int totalValidationErrors = 0;
    public static int activeWorkload = 0;

    private static int nextId = 1;

    private final int id; //a keyword FINAL faz o ID gerado no momento da criação nunca poder ser alterado
    private final LocalDate deadline; // Imutável após o nascimento
    private String title;
    private TaskStatus status;
    private User owner;
    private Integer estimatedEffort; //Tem que ser em horas;

    public Task(String title, LocalDate deadline, Integer esforco) {
        this.id = nextId++;
        this.deadline = deadline;
        this.title = title;
        this.status = TaskStatus.TO_DO;
        this.estimatedEffort = esforco;
        
        // Ação do Aluno:
        totalTasksCreated++; 
        
    }

    /**
     * Move a tarefa para IN_PROGRESS.
     * Regra: Só é possível se houver um owner atribuído e não estiver BLOCKED.
     */
    public void moveToInProgress(User user) {
        // Se falhar, incrementar totalValidationErrors e lançar NexusValidationException

        if(user.consultUsername()==null){
            incrementTotalValidationErrors();
            throw new NexusValidationException("Operação só é permitida se houver um User atribuído como owner");
        }


        setStatus(TaskStatus.IN_PROGRESS);
        
    }

    /**
     * Finaliza a tarefa.
     * Regra: Só pode ser movida para DONE se não estiver BLOCKED.
     */
    public void markAsDone() {
        TaskStatus status = getStatus();
        if(status == TaskStatus.BLOCKED){
            incrementTotalValidationErrors();
            throw new NexusValidationException("Só é permitido se a tarefa não estiver no status BLOCKED");
        }
        
        setStatus(TaskStatus.DONE);
    }

    public void setBlocked(boolean blocked) {
        TaskStatus status = getStatus();
        if(status == TaskStatus.DONE){
            incrementTotalValidationErrors();
            throw new NexusValidationException("Uma tarefa em DONE não pode ser movida para BLOCKED");
        } 

        setStatus(TaskStatus.BLOCKED);
    }

    // Getters
    public int getId() { return id; }
    public TaskStatus getStatus() { return status; }
    public String getTitle() { return title; }
    public LocalDate getDeadline() { return deadline; }
    public User getOwner() { return owner; }
    public Integer getEffort() {return estimatedEffort;}

    //setters
    public void setStatus(TaskStatus newstatus){
        this.status = newstatus;
    }

    public void incrementTotalValidationErrors(){
        Task.totalValidationErrors++;
    }

    public void incrementWorkload(){
        Task.activeWorkload++;
    }

    public void incrementTotalTasksCreated(){
        Task.totalTasksCreated++;
    }

    
}