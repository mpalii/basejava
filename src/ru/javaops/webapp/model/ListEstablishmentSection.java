package ru.javaops.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListEstablishmentSection implements Section {
    private static final long serialVersionUID = 1L;

    private final List<Establishment> establishmentContent;

    public ListEstablishmentSection(List<Establishment> establishmentContent) {
        Objects.requireNonNull(establishmentContent, "Parameter establishmentContent must be not null.");
        this.establishmentContent = establishmentContent;
    }

    public ListEstablishmentSection(Establishment... items) {
        this(Arrays.asList(items));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListEstablishmentSection that = (ListEstablishmentSection) o;
        return Objects.equals(establishmentContent, that.establishmentContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(establishmentContent);
    }

    @Override
    public String toString() {
        return establishmentContent.toString() + "\n";
    }
}
