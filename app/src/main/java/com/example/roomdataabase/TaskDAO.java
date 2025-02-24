package com.example.roomdataabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** DAO:
 *        aka Data Access Object
 *        A class that contains sql queries like insert delete and more.
 * <p>
 *        Annotating class with @Dao and also making it an interface will make the room database to
 *        handle lots of thing on it own, so that, you don't have to write every thing explicitly.
 *        That's why @insert works
 */

@Dao
public interface TaskDAO {

    /**
     * simply use @Insert to write an insert query
     * here in insert we are inserting a task object in the table
     * means inserting id, title, description, and flag/state
     */
    @Insert
    void insetTask(TaskEntity task);

    // you can also write customized queries like this.
    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> getAllTasks();

    @Update
    void updateTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);

    @Query("Delete FROM tasks")
    void deleteAllTasks();
}
