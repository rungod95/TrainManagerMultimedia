package com.example.trainmanager.api;

import com.example.trainmanager.domain.Training;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TrainingApiInterface {
    @GET("trainings")
    Call<List<Training>> getTrainings();

    @GET("trainings/{id}")
    Call<Training> getTraining(@Path("id") int id);

    @POST("trainings")
    Call<Void> addTraining(@Body Training trainingDTO);

    @POST("trainings/{id}/register")
    Call<Void> addTraining(@Path("id") int id);

}
