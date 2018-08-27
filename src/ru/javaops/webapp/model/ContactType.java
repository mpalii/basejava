package ru.javaops.webapp.model;

public enum ContactType {
    TELEPHONE("Тел.:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKEDIN("LinkedIn:"),
    GITHUB("Github:"),
    STACKOVERFLOW("Stackoveflow:"),
    HOMEPAGE("Homepage:");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
