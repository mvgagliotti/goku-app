package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.DomainException;
import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;

public class State {
    private final String abbreviation;
    private final String name;

    public State(String abbreviation, String name) {
        ValidationUtils.notNull(abbreviation, () -> new DomainException("State abbreviation cannot be null"));
        ValidationUtils.notNull(name, () -> new DomainException("State name cannot be null"));
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(abbreviation, state.abbreviation) && Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviation, name);
    }

    @Override
    public String toString() {
        return "State{" +
                "abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
