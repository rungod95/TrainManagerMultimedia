package com.example.trainmanager.model;

import com.example.trainmanager.domain.Training;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingListModel {

    public List<Training> getTrainings() {
        // Simulación de datos estáticos (puedes cambiar esto por llamadas a una API)
        List<Training> trainings = new ArrayList<>();
        trainings.add(new Training("Cardio", LocalDate.of(2024, 1, 10), "Intermedio", 45, true));
        trainings.add(new Training("Fuerza", LocalDate.of(2024, 2, 5), "Avanzado", 60, false));
        trainings.add(new Training("Flexibilidad", LocalDate.of(2024, 3, 15), "Básico", 30, true));
        return trainings;
    }
}
