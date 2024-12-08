package com.example.trainmanager.view;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainmanager.R;
import com.example.trainmanager.adapter.TrainingAdapter;
import com.example.trainmanager.contract.TrainingListContract;
import com.example.trainmanager.presenter.TrainingListPresenter;
import com.example.trainmanager.domain.Training;

import java.util.List;

public class TrainingListView extends AppCompatActivity implements TrainingListContract.View {

    private RecyclerView recyclerView;
    private TrainingAdapter adapter;
    private TrainingListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);

        recyclerView = findViewById(R.id.trainingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new TrainingListPresenter(this);
        presenter.getTrainings();
    }

    @Override
    public void showTrainings(List<Training> trainings) {
        adapter = new TrainingAdapter(trainings);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}