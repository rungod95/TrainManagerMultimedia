package com.example.trainmanager.presenter;

import com.example.trainmanager.contract.RegisterTrainingContract;
import com.example.trainmanager.model.RegisterTrainingModel;
import com.example.trainmanager.domain.Training;

import java.time.LocalDate;

public class RegisterTrainingPresenter implements RegisterTrainingContract.Presenter, RegisterTrainingContract.Model.OnRegisterTrainingListener {

    private final RegisterTrainingContract.Model model;
    private final RegisterTrainingContract.View view;

    // Constructor
    public RegisterTrainingPresenter(RegisterTrainingContract.View view) {
        this.model = new RegisterTrainingModel();
        this.view = view;
    }

    @Override
    public void saveTraining(String name, String date, String level, int duration) {
        if (name.isEmpty() || date.isEmpty() || level.isEmpty()) {
            view.showErrorMessage("All fields are required.");
            return;
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);
            Training training = new Training(name, parsedDate, level, duration, false);
            registerTraining(training);
        } catch (Exception e) {
            view.showErrorMessage("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    @Override
    public void registerTraining(Training training) {
        model.registerTraining(training, this);
    }

    @Override
    public void onRegisterTrainingSuccess(String message) {
        view.showSuccessMessage(message);
    }

    @Override
    public void onRegisterTrainingError(String message) {
        view.showErrorMessage("Failed to register training: " + message);
    }
}
