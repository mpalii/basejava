package ru.javaops.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Establishment implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link establishment;
    private final List<Position> positions;

    public Establishment(String name, String url, List<Position> list) {
        Objects.requireNonNull(name, "parameter name must be not null");
        Objects.requireNonNull(list, "list must be initialized");
        establishment = new Link(name, url);
        positions = list;
    }

    public Establishment(String name, String url, Position... positions) {
        this(name, url, Arrays.asList(positions));
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

    public static class Position implements Serializable {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Position(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(description, "parameter description must be not null");
            Objects.requireNonNull(startDate, "parameter startDate must be not null");
            Objects.requireNonNull(endDate, "parameter endDate must be not null");
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return startDate + " - " + endDate + "\n" + title + " " + description + "\n";
        }
    }
}
