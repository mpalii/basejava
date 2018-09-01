package ru.javaops.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Establishment {
    private final Link establishment;
    private final String title;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Establishment(String name, String url, String title, String description, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(name, "parameter name must be not null");
        Objects.requireNonNull(description, "parameter description must be not null");
        Objects.requireNonNull(startDate, "parameter startDate must be not null");
        Objects.requireNonNull(endDate, "parameter endDate must be not null");
        establishment = new Link(name, url);
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Establishment that = (Establishment) o;
        return Objects.equals(establishment, that.establishment) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(establishment, title, description, startDate, endDate);
    }

    @Override
    public String toString() {
        return establishment + "\n" + startDate + " - " + endDate + " " + title + " " + description + "\n";
    }
}
