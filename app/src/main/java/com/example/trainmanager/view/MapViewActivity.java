package com.example.trainmanager.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainmanager.R;
import com.example.trainmanager.api.TrainingApi;
import com.example.trainmanager.api.TrainingApiInterface;
import com.example.trainmanager.domain.Training;
import com.example.trainmanager.util.MapUtil;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapViewActivity extends AppCompatActivity {

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private Double selectedLatitude = null;
    private Double selectedLongitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        mapView = findViewById(R.id.mapView);

        // Cargar estilo y configurar gestor de gestos
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
            pointAnnotationManager = MapUtil.initializePointAnnotationManager(mapView);

            // Cargar marcadores existentes desde la API
            fetchTrainingsAndAddMarkers();

            // Configurar el gestor de gestos para agregar un nuevo marcador
            GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(mapView);
            if (gesturesPlugin != null) {
                gesturesPlugin.addOnMapClickListener(point -> {
                    if (pointAnnotationManager != null) {
                        pointAnnotationManager.deleteAll(); // Remover marcadores existentes
                        fetchTrainingsAndAddMarkers(); // Volver a cargar marcadores registrados
                    }

                    // Crear marcador en la ubicación seleccionada
                    PointAnnotationOptions options = new PointAnnotationOptions()
                            .withPoint(point)
                            .withIconImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_marker));
                    pointAnnotationManager.create(options);

                    // Guardar coordenadas seleccionadas
                    selectedLatitude = point.latitude();
                    selectedLongitude = point.longitude();

                    Toast.makeText(this, "Marcador añadido: " + point.latitude() + ", " + point.longitude(), Toast.LENGTH_SHORT).show();
                    return true;
                });
            }
        });

        // Confirmar ubicación seleccionada
        findViewById(R.id.btn_confirm_location).setOnClickListener(v -> {
            if (selectedLatitude != null && selectedLongitude != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latitude", selectedLatitude);
                resultIntent.putExtra("longitude", selectedLongitude);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Selecciona una ubicación primero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTrainingsAndAddMarkers() {
        TrainingApiInterface apiService = TrainingApi.buildInstance();
        apiService.getTrainings().enqueue(new Callback<List<Training>>() {
            @Override
            public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Training training : response.body()) {
                        if (training.getLatitude() != null && training.getLongitude() != null) {
                            Point point = Point.fromLngLat(training.getLongitude(), training.getLatitude());
                            PointAnnotationOptions options = new PointAnnotationOptions()
                                    .withPoint(point)
                                    .withIconImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_marker))
                                    .withTextField(training.getTipo()); // Mostrar el nombre del entrenamiento
                            pointAnnotationManager.create(options);
                        }
                    }
                } else {
                    Toast.makeText(MapViewActivity.this, "Error al cargar los entrenamientos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Training>> call, Throwable t) {
                Toast.makeText(MapViewActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
