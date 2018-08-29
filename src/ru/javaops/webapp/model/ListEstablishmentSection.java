package ru.javaops.webapp.model;

import java.util.List;

public class ListEstablishmentSection extends Section {
    private List<Establishment> establishmentContent;

    public ListEstablishmentSection(List<Establishment> establishmentContent) {
        this.establishmentContent = establishmentContent;
    }

    public List<Establishment> getEstablishmentContent() {
        return establishmentContent;
    }

    public void setEstablishmentContent(List<Establishment> establishmentContent) {
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
        StringBuilder stringBuilder = new StringBuilder();
        for (Establishment element : establishmentContent) {
            stringBuilder.append(element).append("\n");
        }
        return stringBuilder.toString();
    }
}
