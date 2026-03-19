package com.nexus.model;

import java.util.ArrayList;
import java.util.List;
import com.nexus.model.Task;

public class Project {
    public String Nome;
    private List<Task> Tarefas = new ArrayList<>();
    public Integer Horas_totais;


    public void addTask(Task t){
        Tarefas.add(t);
        return;
    }



}
