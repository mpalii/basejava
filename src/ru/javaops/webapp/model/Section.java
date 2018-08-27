package ru.javaops.webapp.model;

public class Section {
    private String title;
    private TextContainer textContainer;

    public Section(String title, TextContainer textContainer) {
        this.title = title;
        this.textContainer = textContainer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TextContainer getTextContainer() {
        return textContainer;
    }

    public void setTextContainer(TextContainer textContainer) {
        this.textContainer = textContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        if (!title.equals(section.title)) return false;
        return textContainer.equals(section.textContainer);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + textContainer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return textContainer.toString();
    }
}
