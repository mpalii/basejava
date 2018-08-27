package ru.javaops.webapp.model;

import java.util.LinkedList;
import java.util.List;

public class EstablishmentContainer extends TextContainer {
    private List<Establishment> textContainer;

    public EstablishmentContainer(List<Establishment> textContainer) {
        this.textContainer = textContainer;
    }


    @Override
    public List<String> getContent() {
        List<String> textContainer = new LinkedList<>();
        for (Establishment element : this.textContainer) {
            textContainer.add(element.toString());
        }
        return textContainer;
    }

    @Override
    public void addContent(String content) {
        throw new UnsupportedOperationException("public void addContent(String content) - unsupports in EstablishmentContainer!");
    }

    @Override
    public void removeAllContent() {
        textContainer.clear();
    }

    @Override
    public void removeContent(int index) {
        removeAllContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstablishmentContainer that = (EstablishmentContainer) o;

        return textContainer.equals(that.textContainer);
    }

    @Override
    public int hashCode() {
        return textContainer.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Establishment element : textContainer) {
            stringBuilder.append(element).append("\n");
        }
        return stringBuilder.toString();
    }
}
