package com.example.roomdataabase;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDAO taskDao;
    private final LiveData<List<TaskEntity>> allTasks;
    private final ExecutorService executorService;

    public TaskRepository(Application application) {
        MyAppDatabase database = MyAppDatabase.getInstance(application);
        taskDao = database.taskDAO();
        allTasks = taskDao.getAllTasks();
        executorService = Executors.newFixedThreadPool(3);
    }

    public void insert(TaskEntity task) {
        executorService.execute(() -> taskDao.insetTask(task));
    }

    public void update(TaskEntity task) {
        executorService.execute(() -> taskDao.updateTask(task));
    }

    public void delete(TaskEntity task) {
        executorService.execute(() -> taskDao.deleteTask(task));
    }

    public void deleteAllTasks() {
        executorService.execute(taskDao::deleteAllTasks);
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<TaskEntity>> getTasksSortedByTitle() {
        return taskDao.getTasksSortedByTitle();
    }

    public LiveData<List<TaskEntity>> getTasksSortedByDate() {
        return taskDao.getTasksSortedByDate();
    }
}
