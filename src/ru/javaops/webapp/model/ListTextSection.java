package ru.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListTextSection extends Section {
    private List<String> listContent;

    public ListTextSection(List<String> listContent) {
        Objects.requireNonNull(listContent, "Parameter listContent must be not null.");
        this.listContent = listContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListTextSection that = (ListTextSection) o;

        return listContent.equals(that.listContent);
    }

    @Override
    public int hashCode() {
        return listContent.hashCode();
    }

    @Override
    public String toString() {
        return listContent.toString() + "\n";
    }
}
