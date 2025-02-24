package com.example.roomdataabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//  This Annotation lets java knows that use this class to created database.
@Database(entities = {TaskEntity.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    // created a an object of this class and made it static so
    // can be accessed through out the package
    private static MyAppDatabase instance;

    // made this method abstract so that, at compile time, room will autogenerate the code it requires for this method.
    public abstract TaskDAO taskDAO();

    // used "synchronized" so that only a single thread can access this method
    // else, problems.
    public static synchronized MyAppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyAppDatabase.class, "my_app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
