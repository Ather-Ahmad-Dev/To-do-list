package com.example.roomdataabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;

public class AddTaskBottomSheet extends BottomSheetDialogFragment {
    private EditText editTextTitle, editTextDescription;
    private Button buttonSave;
    private TaskViewModel taskViewModel;
    private int taskId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_add_task, container, false);

        editTextTitle = view.findViewById(R.id.editTextTaskTitle);
        editTextDescription = view.findViewById(R.id.editTextTaskDescription);
        buttonSave = view.findViewById(R.id.buttonSaveTask);

        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        if (getArguments() != null) {
            taskId = getArguments().getInt("taskId", -1);
            String taskTitle = getArguments().getString("taskTitle", "");
            String taskDescription = getArguments().getString("taskDescription", "");

            editTextTitle.setText(taskTitle);
            editTextDescription.setText(taskDescription);
            buttonSave.setText("Update Task");
        }

        buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();

            if (!title.isEmpty()) {
                if (taskId == -1) {
                    Date currentDate = new Date(System.currentTimeMillis());
                    TaskEntity newTask = new TaskEntity(title, description, currentDate);
                    taskViewModel.insert(newTask);
                } else {
                    Date currentDate = new Date(System.currentTimeMillis());
                    TaskEntity updatedTask = new TaskEntity(title, description, currentDate);
                    updatedTask.setId(taskId);
                    taskViewModel.update(updatedTask);
                }
                dismiss();
            } else {
                editTextTitle.setError("Title is required");
            }
        });

        return view;
    }
}
