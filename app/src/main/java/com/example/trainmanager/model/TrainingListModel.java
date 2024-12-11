package com.example.trainmanager.model;

import com.example.trainmanager.contract.TrainingListContract;
import com.example.trainmanager.api.TrainingApi;
import com.example.trainmanager.api.TrainingApiInterface;
import com.example.trainmanager.domain.Training;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingListModel implements TrainingListContract.Model {

    @Override
    public void loadTrainings(OnLoadTrainingsListener listener) {
        TrainingApiInterface trainingApi = TrainingApi.buildInstance();
        Call<List<Training>> getTrainingsCall = trainingApi.getTrainings();

        getTrainingsCall.enqueue(new Callback<List<Training>>() {
            @Override
            public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onLoadTrainingsSuccess(response.body());
                } else {
                    listener.onLoadTrainingsError("No trainings available or API error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Training>> call, Throwable t) {
                listener.onLoadTrainingsError("Error connecting to the API: " + t.getMessage());
            }
        });
    }
}