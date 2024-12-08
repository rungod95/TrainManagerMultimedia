package com.example.trainmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainmanager.R;
import com.example.trainmanager.domain.Training;

import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

    private final List<Training> trainingList;

    // Constructor
    public TrainingAdapter(List<Training> trainingList) {
        this.trainingList = trainingList;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del ítem
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_item, parent, false);
        return new TrainingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        // Obtenemos el entrenamiento en la posición actual
        Training training = trainingList.get(position);

        // Asignamos los valores a las vistas
        holder.nameTextView.setText(training.getName());
        holder.dateTextView.setText(training.getDate().toString());
        holder.levelTextView.setText(training.getLevel());
        holder.durationTextView.setText(training.getDuration() + " min");
        holder.completedTextView.setText(training.isCompleted() ? "Completado" : "Pendiente");
    }

    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    // ViewHolder para el RecyclerView
    static class TrainingViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView dateTextView;
        TextView levelTextView;
        TextView durationTextView;
        TextView completedTextView;

        public TrainingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Enlazamos las vistas del layout del ítem
            nameTextView = itemView.findViewById(R.id.text_name);
            dateTextView = itemView.findViewById(R.id.text_date);
            levelTextView = itemView.findViewById(R.id.text_level);
            durationTextView = itemView.findViewById(R.id.text_duration);
            completedTextView = itemView.findViewById(R.id.text_completed);
        }
    }
}
