package com.demo.empmanager.validators;

import com.demo.empmanager.documents.Person;
import com.demo.empmanager.exceptions.PersonException;

public class PersonValidator {

    public static void validatePerson(Person person) throws PersonException {
        if (person.getName().length() < 3 || person.getName().length() > 20)
            throw new PersonException("name", "name.length.invalid");
        if (person.getAge() < 0 || person.getAge() > 100)
            throw new PersonException("age", "age.invalid");
    }

    public static void validateRange(double min, double max) throws PersonException {
        if (min > max)
            throw new PersonException("range", "min value should be less than max value");
        if (min < 0)
            throw new PersonException("range", "invalid min value, should be positive");
    }
}
