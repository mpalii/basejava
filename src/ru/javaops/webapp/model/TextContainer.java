package ru.javaops.webapp.model;

import java.util.List;

public abstract class TextContainer {
    public abstract List<String> getContent();
    public abstract void addContent(String content);
    public abstract void removeAllContent();
    public abstract void removeContent(int index);
}
