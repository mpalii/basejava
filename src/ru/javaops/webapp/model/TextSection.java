package ru.javaops.webapp.model;

import java.util.Objects;

public class TextSection implements Section {
    private static final long serialVersionUID = 1L;

    private final String content;

    public TextSection(String content) {
        Objects.requireNonNull(content, "Parameter content must be not null.");
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content + "\n";
    }
}
