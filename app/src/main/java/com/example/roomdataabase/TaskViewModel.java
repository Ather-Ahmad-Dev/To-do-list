package com.example.roomdataabase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<TaskEntity>> allTasks;

    public TaskViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public void insert(TaskEntity task) {
        repository.insert(task);
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return allTasks;
    }

    public void update(TaskEntity task) {
        repository.update(task);
    }

    public void delete(TaskEntity task) {
        repository.delete(task);
    }

    public void deleteAllTasks() {
        repository.deleteAllTasks();
    }

    public LiveData<List<TaskEntity>> getTasksSortedByTitle() {
        return repository.getTasksSortedByTitle();
    }

    public LiveData<List<TaskEntity>> getTasksSortedByDate() {
        return repository.getTasksSortedByDate();
    }

}
