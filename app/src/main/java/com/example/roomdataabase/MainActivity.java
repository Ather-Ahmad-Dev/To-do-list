package com.example.roomdataabase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
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
                adapter.setTasks(tasks);
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
                }
            }
        });

        Spinner spinnerSort = findViewById(R.id.spinnerSort);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    taskViewModel.getTasksSortedByTitle().observe(MainActivity.this, tasks -> adapter.setTasks(tasks));
                } else {
                    taskViewModel.getTasksSortedByDate().observe(MainActivity.this, tasks -> adapter.setTasks(tasks));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}