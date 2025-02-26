package com.example.roomdataabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskEntity.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class MyAppDatabase extends RoomDatabase {

    private static MyAppDatabase instance;

    public abstract TaskDAO taskDAO();

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
