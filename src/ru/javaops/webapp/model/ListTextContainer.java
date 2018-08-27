package ru.javaops.webapp.model;

import java.util.List;

public class ListTextContainer extends TextContainer {
    private List<String> textContainer;

    public ListTextContainer(List<String> textContainer) {
        this.textContainer = textContainer;
    }


    @Override
    public List<String> getContent() {
        return textContainer;
    }

    @Override
    public void addContent(String content) {
        textContainer.add(content);
    }

    @Override
    public void removeAllContent() {
        textContainer.clear();
    }

    @Override
    public void removeContent(int index) {
        if (textContainer.size() != 0) {
            textContainer.remove(textContainer.size() - 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListTextContainer that = (ListTextContainer) o;

        return textContainer.equals(that.textContainer);
    }

    @Override
    public int hashCode() {
        return textContainer.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String element : textContainer) {
            stringBuilder.append(element).append("\n");
        }
        return stringBuilder.toString();
    }
}
