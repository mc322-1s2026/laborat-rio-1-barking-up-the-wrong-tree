package com.nexus.service;

import com.nexus.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Workspace {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
        return;
    }

    public List<Task> getTasks() {
        // Retorna uma visão não modificável para garantir encapsulamento
        return Collections.unmodifiableList(tasks);
    }
}