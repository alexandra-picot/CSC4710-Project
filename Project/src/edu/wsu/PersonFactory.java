package edu.wsu;

public class PersonFactory {

    static public Person getPerson(String email, String firstName, String lastName, PersonType type) {
        if (type == PersonType.AUTHOR) {
            return new Author(email, firstName, lastName);
        } else {
            return new Person(email, firstName, lastName);
        }
    }

}
