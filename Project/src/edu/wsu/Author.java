package edu.wsu;

public class Author extends Person {
    String affiliation;

    public Author(String email, String firstName, String lastName) {
        super(email, firstName, lastName);
    }

    public Author(String email, String firstName, String lastName, String affiliation) {
        super(email, firstName, lastName);
        this.affiliation = affiliation;
    }
}
