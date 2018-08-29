package ru.javaops.webapp.model;

import java.util.LinkedList;
import java.util.List;

public class Establishment {
    private String name;
    private Link link;
    private List<String> positions;

    public Establishment(String name, Link link) {
        positions = new LinkedList<>();
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Establishment that = (Establishment) o;

        if (!name.equals(that.name)) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("\n");
        for (String position : positions) {
            stringBuilder.append(position).append("\n");
        }
//        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
