package com.example.supplement.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Patient {
    private final UUID id;
    private String name;
    private int age;
    private final List<Disease> diseases;
    private final List<Supplement> currentSupplements;

    public Patient(String name, int age, List<Disease> diseases) {
        this(UUID.randomUUID(), name, age, diseases, new ArrayList<>());
    }

    public Patient(UUID id, String name, int age, List<Disease> diseases, List<Supplement> currentSupplements) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.age = age;
        this.diseases = new ArrayList<>(Objects.requireNonNull(diseases, "diseases"));
        this.currentSupplements = new ArrayList<>(Objects.requireNonNull(currentSupplements, "currentSupplements"));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Disease> getDiseases() {
        return Collections.unmodifiableList(diseases);
    }

    public void addDisease(Disease disease) {
        diseases.add(Objects.requireNonNull(disease, "disease"));
    }

    public List<Supplement> getCurrentSupplements() {
        return Collections.unmodifiableList(currentSupplements);
    }

    public void addSupplement(Supplement supplement) {
        currentSupplements.add(Objects.requireNonNull(supplement, "supplement"));
    }

    public void removeSupplement(Supplement supplement) {
        currentSupplements.remove(supplement);
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", diseases=" + diseases +
            ", currentSupplements=" + currentSupplements +
            '}';
    }
}
