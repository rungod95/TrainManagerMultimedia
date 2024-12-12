package com.example.trainmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainmanager.R;
import com.example.trainmanager.api.TrainingApi;
import com.example.trainmanager.api.TrainingApiInterface;
import com.example.trainmanager.domain.Training;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivityView extends AppCompatActivity {

    private TextView typeTextView;
    private TextView levelTextView;
    private TextView durationTextView;
    private TextView dateTextView;
    private CheckBox completedCheckBox;
    private Button viewLocationButton;

    private double trainingLatitude;
    private double trainingLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        // Inicializar vistas
        typeTextView = findViewById(R.id.tv_training_name);
        levelTextView = findViewById(R.id.tv_training_level);
        durationTextView = findViewById(R.id.tv_training_duration);
        dateTextView = findViewById(R.id.tv_training_date);
        completedCheckBox = findViewById(R.id.cb_training_completed);
        viewLocationButton = findViewById(R.id.btn_view_location);

        // Obtener el ID del entrenamiento desde el intent
        Intent intent = getIntent();
        long trainingId = intent.getLongExtra("trainingId", 0);

        // Llamada a la API para obtener detalles del entrenamiento
        fetchTrainingDetails(trainingId);

        // Configurar el botón para ver la ubicación
        viewLocationButton.setOnClickListener(v -> openMap());
    }

    private void fetchTrainingDetails(long trainingId) {
        TrainingApiInterface apiService = TrainingApi.buildInstance();
        Call<Training> call = apiService.getTraining((int) trainingId);

        call.enqueue(new Callback<Training>() {
            @Override
            public void onResponse(Call<Training> call, Response<Training> response) {
                if (response.isSuccessful() && response.body() != null) {
                    populateDetails(response.body());
                } else {
                    Toast.makeText(DetailActivityView.this, "Failed to load training details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Training> call, Throwable t) {
                Toast.makeText(DetailActivityView.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateDetails(Training training) {
        typeTextView.setText(training.getTipo()); // Mostrar el tipo en lugar del nombre
        levelTextView.setText("Level: " + training.getNivel());
        durationTextView.setText("Duration: " + training.getDuracion() + " min");
        dateTextView.setText("Date: " + training.getFecha().toString());
        completedCheckBox.setChecked(training.isCompletado());

        // Guardar las coordenadas
        trainingLatitude = training.getLatitude() != null ? training.getLatitude() : 0.0;
        trainingLongitude = training.getLongitude() != null ? training.getLongitude() : 0.0;

        if (trainingLatitude == 0.0 && trainingLongitude == 0.0) {
            viewLocationButton.setEnabled(false);
            Toast.makeText(this, "No location available for this training.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMap() {
        if (trainingLatitude != 0.0 && trainingLongitude != 0.0) {
            Intent mapIntent = new Intent(this, MapViewActivity.class);
            mapIntent.putExtra("latitude", trainingLatitude);
            mapIntent.putExtra("longitude", trainingLongitude);
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
        }
    }
}
