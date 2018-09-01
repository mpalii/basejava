package ru.javaops.webapp.model;

import java.util.Objects;

public class Establishment {
    private String title;
    private String description;
    private Link link;
    private String startDate;
    private String endDate;

    public Establishment(String name, String url, String position, String startDate, String endDate) {
        this.title = name;
        this.description = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = new Link(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Establishment that = (Establishment) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(link, that.link) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, link, startDate, endDate);
    }

    @Override
    public String toString() {
        return title + "\n" + startDate + " - " + endDate + " " + description + "\n";
    }
}
