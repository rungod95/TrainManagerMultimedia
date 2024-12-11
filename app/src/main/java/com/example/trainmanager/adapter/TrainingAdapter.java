package com.example.trainmanager.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trainmanager.R;
import com.example.trainmanager.domain.Training;
import com.example.trainmanager.view.DetailActivityView;

import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingHolder> {

    private final List<Training> trainingList;

    public TrainingAdapter(List<Training> trainingList) {
        this.trainingList = trainingList;
    }

    @NonNull
    @Override
    public TrainingAdapter.TrainingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_item, parent, false);
        return new TrainingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingAdapter.TrainingHolder holder, int position) {
        holder.nombre.setText(trainingList.get(position).getTipo());
        holder.nivel.setText(trainingList.get(position).getNivel()); //
    }

    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    public class TrainingHolder extends RecyclerView.ViewHolder {

        private TextView nombre;
        private TextView nivel;

        public TrainingHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.tv_training_name);
            nivel = itemView.findViewById(R.id.trainingLevel);

            itemView.setOnClickListener(view -> {
                long trainingId = trainingList.get(getAdapterPosition()).getId();

                Intent intent = new Intent(itemView.getContext(), DetailActivityView.class);
                intent.putExtra("trainingId", trainingId);
                itemView.getContext().startActivity(intent);

            });

            }
        }
    }

