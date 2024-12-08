package com.example.trainmanager.domain;

import java.time.LocalDate;

public class Training {
    private String name; // Nombre del entrenamiento
    private LocalDate date; // Fecha del entrenamiento
    private String level; // Nivel (Básico, Intermedio, Avanzado)
    private int duration; // Duración en minutos
    private boolean completed; // Si el entrenamiento está completado

    // Constructor
    public Training(String name, LocalDate date, String level, int duration, boolean completed) {
        this.name = name;
        this.date = date;
        this.level = level;
        this.duration = duration;
        this.completed = completed;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
