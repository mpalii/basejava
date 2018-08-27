package ru.javaops.webapp.model;

public class Contact {
    private String contact;
    private Link link;

    public Contact(String contact, Link link) {
        this.contact = contact;
        this.link = link;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact1 = (Contact) o;

        if (!contact.equals(contact1.contact)) return false;
        return link != null ? link.equals(contact1.link) : contact1.link == null;
    }

    @Override
    public int hashCode() {
        int result = contact.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return contact;
    }
}
