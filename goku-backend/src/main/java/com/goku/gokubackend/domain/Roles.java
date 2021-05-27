package com.goku.gokubackend.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Roles {
    private final List<String> values;

    public Roles(String...roles) {
        this.values = Arrays.asList(roles);
    }

    public List<String> getValues() {
        return values;
    }

    public String asCSV() {
        return String.join(",", values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return Objects.equals(values, roles.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return "Roles{" +
                "values=" + values +
                '}';
    }
}
