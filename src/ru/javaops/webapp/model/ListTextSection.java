package ru.javaops.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<String> listContent;

    public ListTextSection() {
    }

    public ListTextSection(List<String> listContent) {
        Objects.requireNonNull(listContent, "Parameter listContent must be not null.");
        this.listContent = listContent;
    }

    public ListTextSection(String... items) {
        this(Arrays.asList(items));
    }

    public List<String> getListContent() {
        return listContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return Objects.equals(listContent, that.listContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listContent);
    }

    @Override
    public String toString() {
        return listContent.toString() + "\n";
    }
}
