package com.example.trainmanager.presenter;

import com.example.trainmanager.contract.TrainingListContract;
import com.example.trainmanager.domain.Training;
import com.example.trainmanager.model.TrainingListModel;

import java.time.LocalDate;
import java.util.List;

public class TrainingListPresenter implements TrainingListContract.Presenter, TrainingListContract.Model.OnLoadTrainingsListener {

    private final TrainingListContract.View view;
    private final TrainingListModel model;

    // Constructor
    public TrainingListPresenter(TrainingListContract.View view) {
        this.view = view;
        this.model = new TrainingListModel();
    }

    @Override
    public void loadTrainings() {
        model.loadTrainings(this);
    }
    @Override
    public void onLoadTrainingsSuccess(List<Training> trainingList) {
        view.listTrainings(trainingList);
    }
    @Override
    public void onLoadTrainingsError(String message) {
        view.showErrorMessage(message);
    }
}
