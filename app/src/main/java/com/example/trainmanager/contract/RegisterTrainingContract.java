package com.example.trainmanager.contract;

import com.example.trainmanager.api.TrainingApi;
import com.example.trainmanager.domain.Training;

public interface RegisterTrainingContract {

    interface Model {
        interface OnRegisterTrainingListener {
            void onRegisterTrainingSuccess(String message);
            void onRegisterTrainingError(String message);
        }
        void registerTraining(Training Training, OnRegisterTrainingListener listener);
    }

    interface View {
        void showSuccessMessage(String message);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void saveTraining(String name, String date, String level, int duration);

        void registerTraining(Training training);
    }
}
