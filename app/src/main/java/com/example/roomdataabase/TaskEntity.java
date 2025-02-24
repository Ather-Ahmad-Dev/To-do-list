package com.example.roomdataabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Entity:
 *          An entity is like a table in the database.
 *          Creating more entity classes is like creating more tables in your database.
 *          Each variable in this entity class is like a colum that is going to store some values.
 *          e.g.
 * <p>
 *          id   title    description           isCompleted
 *          1  homework   do maths homework         yes
 *          2  exercise   do push ups               no
 * <p>
 *          for more clarity.
 *          The first row will gong to be an instance of this class aka an object of this class
 *          that is gonna hold 4 values.
 *<p>
 *          Also but annotate the class with @Entity
 */

@Entity(tableName = "tasks")
public class TaskEntity {

    // creating columns in tables i.e. field in entities.
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title, description;
    private boolean isCompleted;

    // now to created method to handle assign data and retrieve data from columns
    // i.e. getters and setters

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCompleted(boolean completed){
        this.isCompleted = completed;
    }
}
