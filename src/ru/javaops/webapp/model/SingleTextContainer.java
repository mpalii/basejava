package ru.javaops.webapp.model;

import java.util.LinkedList;
import java.util.List;

public class SingleTextContainer extends TextContainer {
    private String textContainer;

    public SingleTextContainer(String textContainer) {
        this.textContainer = textContainer;
    }

    @Override
    public List<String> getContent() {
        List<String> textContainer = new LinkedList<String>();
        textContainer.add(this.textContainer);
        return textContainer;
    }

    @Override
    public void addContent(String content) {
        textContainer = content;
    }

    @Override
    public void removeAllContent() {
        textContainer = "";
    }

    @Override
    public void removeContent(int index) {
        removeAllContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleTextContainer that = (SingleTextContainer) o;

        return textContainer.equals(that.textContainer);
    }

    @Override
    public int hashCode() {
        return textContainer.hashCode();
    }

    @Override
    public String toString() {
        return textContainer + "\n";
    }
}
