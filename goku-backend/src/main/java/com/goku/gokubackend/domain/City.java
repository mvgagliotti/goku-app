package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.CityException;
import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;
import java.util.Optional;

public class City implements Entity {
    private final Optional<String> id;
    private final String name;
    private final State state;

    public City(Optional<String> id, String name, State state) {
        ValidationUtils.notNull(name, () -> new CityException("City name cannot be null"));
        ValidationUtils.notNull(state, () -> new CityException("State cannot be null"));
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public Optional<String> getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name) && Objects.equals(state, city.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, state);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean hasBusinessKey() {
        return true; //name + state
    }

    public City withId(String id) {
        return new City(Optional.of(id), name, state);
    }
}
