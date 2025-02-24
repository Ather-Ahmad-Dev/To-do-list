package com.example.roomdataabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskEntity> tasks = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskEntity currentTask = tasks.get(position);
        holder.textViewTitle.setText(currentTask.getTitle());
        holder.textViewDescription.setText(currentTask.getDescription());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentTask);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public TaskEntity getTaskAt(int position) {
        return tasks.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(TaskEntity task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged(); // Refresh RecyclerView when data changes
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(android.R.id.text1);
            textViewDescription = itemView.findViewById(android.R.id.text2);
        }
    }
}
