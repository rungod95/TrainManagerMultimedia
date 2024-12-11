package com.example.trainmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainmanager.R;
import com.example.trainmanager.adapter.TrainingAdapter;
import com.example.trainmanager.contract.TrainingListContract;
import com.example.trainmanager.domain.Training;
import com.example.trainmanager.presenter.TrainingListPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TrainingListView extends AppCompatActivity implements TrainingListContract.View {

    private TrainingAdapter trainingAdapter;
    private ArrayList<Training> trainingList;
    private TrainingListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);

        // Configura la Toolbar personalizada
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new TrainingListPresenter(this);

        trainingList = new ArrayList<>();

        RecyclerView trainingsView = findViewById(R.id.trainingRecyclerView);
        trainingsView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        trainingsView.setLayoutManager(linearLayoutManager);

        trainingAdapter = new TrainingAdapter(trainingList);
        trainingsView.setAdapter(trainingAdapter);

        // Configuración del botón flotante
        FloatingActionButton addTrainingFab = findViewById(R.id.addTrainingFab);
        addTrainingFab.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterTrainingView.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refrescar la lista de entrenamientos al volver del segundo plano
        trainingList.clear();
        presenter.loadTrainings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.training_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_view_map) {
            // Acción para abrir el mapa
            Intent intent = new Intent(this, MapViewActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_add_training) {
            // Acción para registrar un nuevo entrenamiento
            Intent intent = new Intent(this, RegisterTrainingView.class);
            startActivity(intent);
        }
        return true;
    }

    public void registerTraining() {
        Intent intent = new Intent(this, RegisterTrainingView.class);
        startActivity(intent);
    }


   @Override
    public void listTrainings(List<Training> trainingList) {
        this.trainingList.addAll(trainingList);
        trainingAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override

    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

