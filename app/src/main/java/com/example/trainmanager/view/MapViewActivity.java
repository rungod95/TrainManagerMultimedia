package com.example.trainmanager.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainmanager.R;
import com.example.trainmanager.util.MapUtil;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

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

            GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(mapView);
            if (gesturesPlugin != null) {
                gesturesPlugin.addOnMapClickListener(point -> {
                    if (pointAnnotationManager != null) {
                        pointAnnotationManager.deleteAll(); // Remover marcadores existentes
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
