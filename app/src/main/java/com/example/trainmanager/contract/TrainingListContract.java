package com.example.trainmanager.contract;

import com.example.trainmanager.domain.Training;

import java.util.List;

public interface TrainingListContract {
    interface View {
        void showTrainings(List<Training> trainings);
        void showError(String message);
    }

    interface Presenter {
        void getTrainings();
    }

    interface Model {
        interface OnFinishedListener {
            void onFinished(List<Training> trainings);
            void onFailure(Throwable t);
        }

        void fetchTrainings(OnFinishedListener listener);
    }
}