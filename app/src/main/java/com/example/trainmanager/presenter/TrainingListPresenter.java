package com.example.trainmanager.presenter;

import com.example.trainmanager.contract.TrainingListContract;
import com.example.trainmanager.domain.Training;
import com.example.trainmanager.model.TrainingListModel;

import java.time.LocalDate;
import java.util.List;

public class TrainingListPresenter implements TrainingListContract.Presenter {

    private final TrainingListContract.View view;
    private final TrainingListModel model;

    // Constructor
    public TrainingListPresenter(TrainingListContract.View view) {
        this.view = view;
        this.model = new TrainingListModel();
    }

    @Override
    public void getTrainings() {
        try {
            // Simulamos datos desde el modelo o llamamos a una API
            List<Training> trainings = model.getTrainings();
            view.showTrainings(trainings); // Mostramos los datos en la vista
        } catch (Exception e) {
            view.showError("Error al cargar los entrenamientos: " + e.getMessage());
        }
    }
}
