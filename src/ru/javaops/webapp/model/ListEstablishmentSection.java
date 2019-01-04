package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListEstablishmentSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<Establishment> establishmentContent;

    public ListEstablishmentSection() {
        establishmentContent = new ArrayList<>();
    }

    public ListEstablishmentSection(List<Establishment> establishmentContent) {
        Objects.requireNonNull(establishmentContent, "Parameter establishmentContent must be not null.");
        this.establishmentContent = establishmentContent;
    }

    public ListEstablishmentSection(Establishment... items) {
        this(Arrays.asList(items));
    }

    public List<Establishment> getEstablishmentContent() {
        return establishmentContent;
    }

    public Establishment getEstablishmentByUuid(String uuid) {
        int index = getIndexOfEstablishmentByUuid(uuid);
        return establishmentContent.get(index);
    }

    public void deleteEstablishmentByUuid(String uuid) {
        int index = getIndexOfEstablishmentByUuid(uuid);
        establishmentContent.remove(index);
    }

    public void updateEstablishment(Establishment establishment) {
        int index = getIndexOfEstablishmentByUuid(establishment.getUuid());
        establishmentContent.set(index, establishment);
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

    private int getIndexOfEstablishmentByUuid(String uuid) {
        int index = -1;
        for (int i = 0; i < establishmentContent.size(); i++) {
            if (establishmentContent.get(i).getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
