package ru.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class Establishment {
    private final Link establishment;
    private final List<Position> positions;

    public Establishment(String name, String url, List<Position> list) {
        Objects.requireNonNull(name, "parameter name must be not null");
        Objects.requireNonNull(list, "list must be initialized");
        establishment = new Link(name, url);
        positions = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Establishment that = (Establishment) o;
        return Objects.equals(establishment, that.establishment) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(establishment, positions);
    }

    @Override
    public String toString() {
        return establishment + "\n" + positions;
    }
}
