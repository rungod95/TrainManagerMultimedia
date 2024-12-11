package com.example.trainmanager.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class Training implements Parcelable {
    private long id; // Identificador del entrenamiento
    private String tipo; // Tipo del entrenamiento
    private LocalDate fecha; // Fecha del entrenamiento
    private String nivel; // Nivel (Básico, Intermedio, Avanzado)
    private int duracion; // Duración en minutos
    private boolean completado; // Si el entrenamiento está completado
    private Double latitude; // Latitud del entrenamiento
    private Double longitude; // Longitud del entrenamiento

    // Constructor
    public Training(String tipo, LocalDate fecha, String nivel, int duracion, boolean completado) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.nivel = nivel;
        this.duracion = duracion;
        this.completado = completado;
    }

    // Constructor para Parcel
    protected Training(Parcel in) {
        id = in.readLong();
        tipo = in.readString();
        fecha = (LocalDate) in.readSerializable();
        nivel = in.readString();
        duracion = in.readInt();
        completado = in.readByte() != 0;
        latitude = (in.readByte() == 0) ? null : in.readDouble();
        longitude = (in.readByte() == 0) ? null : in.readDouble();
    }

    public static final Creator<Training> CREATOR = new Creator<Training>() {
        @Override
        public Training createFromParcel(Parcel in) {
            return new Training(in);
        }

        @Override
        public Training[] newArray(int size) {
            return new Training[size];
        }
    };

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(tipo);
        dest.writeSerializable(fecha);
        dest.writeString(nivel);
        dest.writeInt(duracion);
        dest.writeByte((byte) (completado ? 1 : 0));
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
    }
}
