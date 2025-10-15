package com.example.supplement.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Disease {
    private final UUID id;
    private String name;
    private final Set<String> prohibitedSubstances;

    public Disease(String name, Set<String> prohibitedSubstances) {
        this(UUID.randomUUID(), name, prohibitedSubstances);
    }

    public Disease(UUID id, String name, Set<String> prohibitedSubstances) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.prohibitedSubstances = new HashSet<>(Objects.requireNonNull(prohibitedSubstances, "prohibitedSubstances"));
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

    public Set<String> getProhibitedSubstances() {
        return Collections.unmodifiableSet(prohibitedSubstances);
    }

    public void setProhibitedSubstances(Set<String> newSubstances) {
        prohibitedSubstances.clear();
        prohibitedSubstances.addAll(Objects.requireNonNull(newSubstances, "newSubstances"));
    }

    @Override
    public String toString() {
        return "Disease{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", prohibitedSubstances=" + prohibitedSubstances +
            '}';
    }
}
