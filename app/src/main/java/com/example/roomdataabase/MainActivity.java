package com.example.roomdataabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskViewModel taskViewModel;
    private EditText editTextTitle, editTextDescription;
    private TextView textViewTasks;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTitle = findViewById(R.id.editTextTaskTitle);
        editTextDescription = findViewById(R.id.editTextTaskDescription);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        textViewTasks = findViewById(R.id.textViewTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false; // No move functionality needed
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                TaskEntity taskToDelete = adapter.getTaskAt(position);
                taskViewModel.delete(taskToDelete);
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(task -> {
            editTextTitle.setText(task.getTitle());
            editTextDescription.setText(task.getDescription());

            buttonAddTask.setText("Update Task");
            buttonAddTask.setOnClickListener(v -> {
                task.setTitle(editTextTitle.getText().toString());
                task.setDescription(editTextDescription.getText().toString());
                taskViewModel.update(task);
                buttonAddTask.setText("Add Task");
                editTextTitle.setText("");
                editTextDescription.setText("");
            });
        });

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> tasks) {
                adapter.setTasks(tasks); // Update RecyclerView when data changes
            }
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if (!title.isEmpty()) {
                    TaskEntity task = new TaskEntity();
                    task.setTitle(title);
                    task.setDescription(description);
                    taskViewModel.insert(task);

                    editTextTitle.setText("");
                    editTextDescription.setText("");

               //     displayTasks();
                }
            }
        });

        //displayTasks();
    }

    private void displayTasks() {
        taskViewModel.getAllTasks().observe(this, tasks -> {
            if (tasks != null) {
                StringBuilder tasksText = new StringBuilder();
                for (TaskEntity task : tasks) {
                    tasksText.append(task.getTitle()).append(": ").append(task.getDescription()).append("\n");
                }
                textViewTasks.setText(tasksText.toString());
            } else {
                textViewTasks.setText("No tasks available.");
            }
        });
    }

}