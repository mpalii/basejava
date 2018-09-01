package ru.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListEstablishmentSection extends Section {
    private List<Establishment> establishmentContent;

    public ListEstablishmentSection(List<Establishment> establishmentContent) {
        Objects.requireNonNull(establishmentContent, "Parameter establishmentContent must be not null.");
        this.establishmentContent = establishmentContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListEstablishmentSection that = (ListEstablishmentSection) o;

        return establishmentContent.equals(that.establishmentContent);
    }

    @Override
    public int hashCode() {
        return establishmentContent.hashCode();
    }

    @Override
    public String toString() {
        return establishmentContent.toString() + "\n";
    }
}
