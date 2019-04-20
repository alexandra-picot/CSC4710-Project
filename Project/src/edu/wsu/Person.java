package edu.wsu;

import java.util.HashMap;
import java.util.Map;

public class Person {
    String email;
    String firstName;
    String lastName;

    static public String PERSON_EMAIL_KEY = "email";
    static public String PERSON_FIRSTNAME_KEY = "firstName";
    static public String PERSON_LASTNAME_KEY = "lastName";

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass())
            return false;

        Person person = (Person) obj;
        return email.equals(person.email) && firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }

    static public Map<String, String> convertToMap(Person person) {
        Map<String, String> res = new HashMap<>();

        res.put(PERSON_EMAIL_KEY, person.email);
        res.put(PERSON_FIRSTNAME_KEY, person.firstName);
        res.put(PERSON_LASTNAME_KEY, person.lastName);
        return res;
    }
}
