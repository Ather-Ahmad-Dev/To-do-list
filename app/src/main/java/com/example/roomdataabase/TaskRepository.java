package com.example.roomdataabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDAO taskDao;
    private LiveData<List<TaskEntity>> allTasks;

    public TaskRepository(Application application){
        MyAppDatabase database = MyAppDatabase.getInstance(application);
        taskDao = database.taskDAO();
        allTasks = taskDao.getAllTasks();
    }

    public void inset(TaskEntity task){
        new InsertTaskAsync(taskDao).execute(task);
    }

    public LiveData<List<TaskEntity>> getAllTasks(){
        return allTasks;
    }

    public void update(TaskEntity task){
        new UpdateTaskAsync(taskDao).execute(task);
    }

    public void delete(TaskEntity task){
        new DeleteTaskAsync(taskDao).execute(task);
    }

    public void deleteAllTasks(){
        new DeleteAllTasksAsync(taskDao).execute();
    }

    private static class InsertTaskAsync extends AsyncTask<TaskEntity, Void, Void>{
        private TaskDAO taskDao;

        private InsertTaskAsync(TaskDAO taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... tasks){
            taskDao.insetTask(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsync extends AsyncTask<TaskEntity, Void, Void>{
        private TaskDAO taskDao;

        private UpdateTaskAsync(TaskDAO taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... tasks){
            taskDao.updateTask(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsync extends AsyncTask<TaskEntity, Void, Void> {
        private TaskDAO taskDao;

        private DeleteTaskAsync(TaskDAO taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... tasks) {
            taskDao.deleteTask(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllTasksAsync extends AsyncTask<Void, Void, Void> {
        private TaskDAO taskDao;

        private DeleteAllTasksAsync(TaskDAO taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllTasks();
            return null;
        }
    }
}
