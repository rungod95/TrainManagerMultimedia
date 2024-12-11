package com.example.trainmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainmanager.R;
import com.example.trainmanager.contract.RegisterTrainingContract;
import com.example.trainmanager.presenter.RegisterTrainingPresenter;

public class RegisterTrainingView extends AppCompatActivity implements RegisterTrainingContract.View {

    private EditText nameEditText, dateEditText, durationEditText;
    private Spinner levelSpinner;
    private Button saveButton, mapButton;

    private RegisterTrainingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_training);

        // Inicializar vistas
        nameEditText = findViewById(R.id.et_training_name);
        dateEditText = findViewById(R.id.et_training_date);
        levelSpinner = findViewById(R.id.sp_training_level);
        durationEditText = findViewById(R.id.et_training_duration);
        saveButton = findViewById(R.id.btn_save_training);
        mapButton = findViewById(R.id.btn_open_map);

        // Configurar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.training_levels,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);

        // Inicializar el presentador
        presenter = new RegisterTrainingPresenter(this);

        // Configurar el botón de guardar
        saveButton.setOnClickListener(v -> {
            String tipo = nameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String level = levelSpinner.getSelectedItem().toString();
            String durationStr = durationEditText.getText().toString().trim();

            if (tipo.isEmpty() || date.isEmpty() || durationStr.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int duration = Integer.parseInt(durationStr);
                presenter.saveTraining(tipo, date, level, duration);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid duration format", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón de mapa
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapViewActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}