package com.example.trainmanager.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trainmanager.R;
import com.example.trainmanager.api.TrainingApi;
import com.example.trainmanager.api.TrainingApiInterface;
import com.example.trainmanager.domain.Training;
import com.example.trainmanager.util.MapUtil;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapViewActivity extends AppCompatActivity {

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
            pointAnnotationManager = MapUtil.initializePointAnnotationManager(mapView);
            fetchTrainingsAndAddMarkers();
        });
    }

    private void fetchTrainingsAndAddMarkers() {
        TrainingApiInterface apiService = TrainingApi.buildInstance();
        apiService.getTrainings().enqueue(new Callback<List<Training>>() {
            @Override
            public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addMarkers(response.body());
                } else {
                    Toast.makeText(MapViewActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Training>> call, Throwable t) {
                Toast.makeText(MapViewActivity.this, "Error al obtener entrenamientos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarkers(List<Training> trainings) {
        for (Training training : trainings) {
            if (training.getLatitude() == null || training.getLongitude() == null) {
                Toast.makeText(this, "Coordenadas inválidas para el entrenamiento: " + training.getTipo(), Toast.LENGTH_SHORT).show();
                continue;
            }

            Point point = Point.fromLngLat(training.getLongitude(), training.getLatitude());
            PointAnnotationOptions options = new PointAnnotationOptions()
                    .withIconImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_marker))
                    .withTextField(training.getTipo())
                    .withPoint(point);

            pointAnnotationManager.create(options);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pointAnnotationManager != null) {
            pointAnnotationManager.onDestroy();
        }
        mapView.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
