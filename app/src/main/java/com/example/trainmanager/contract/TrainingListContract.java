package com.example.trainmanager.contract;

import com.example.trainmanager.domain.Training;

import java.util.List;

public interface TrainingListContract {
    interface Model {
        interface OnLoadTrainingsListener {
            void onLoadTrainingsSuccess(List<Training> trainingList);
            void onLoadTrainingsError(String message);
        }
        void loadTrainings(OnLoadTrainingsListener listener);
    }

    interface View {
        void listTrainings(List<Training> trainingList);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void loadTrainings();
    }
}
