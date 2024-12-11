package com.example.trainmanager.model;

import com.example.trainmanager.contract.RegisterTrainingContract;
import com.example.trainmanager.api.TrainingApi;
import com.example.trainmanager.api.TrainingApiInterface;
import com.example.trainmanager.domain.Training;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTrainingModel implements RegisterTrainingContract.Model {

    @Override
    public void registerTraining(Training training, OnRegisterTrainingListener listener) {
        TrainingApiInterface trainingApi = TrainingApi.buildInstance();
        Call<Void> callRegisterTraining = trainingApi.addTraining(training);

        callRegisterTraining.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onRegisterTrainingSuccess("Training registered successfully.");
                } else {
                    listener.onRegisterTrainingError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onRegisterTrainingError("Error connecting to the API: " + t.getMessage());
            }
        });
    }
}