package ru.javaops.webapp.model;

import java.util.List;

public class ListTextSection extends Section {
    private List<String> listContent;

    public ListTextSection(List<String> listContent) {
        this.listContent = listContent;
    }

    public List<String> getListContent() {
        return listContent;
    }

    public void setListContent(List<String> listContent) {
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
        StringBuilder stringBuilder = new StringBuilder();
        for (String element : listContent) {
            stringBuilder.append(element).append("\n");
        }
        return stringBuilder.toString();
    }
}
