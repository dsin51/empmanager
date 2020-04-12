package com.demo.empmanager.controllers;

import com.demo.empmanager.documents.Person;
import com.demo.empmanager.error.ErrorResponse;
import com.demo.empmanager.exceptions.PersonException;
import com.demo.empmanager.repositories.PersonRepository;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.demo.empmanager.validators.PersonValidator.validatePerson;
import static com.demo.empmanager.validators.PersonValidator.validateRange;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    public ResponseEntity<ErrorResponse> handleException(PersonException pe) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(pe.getField(), pe.getMessage()));
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> addPerson(@RequestBody Person person) throws PersonException, MongoWriteException {
        validatePerson(person);
        return personRepository.insert(person);
    }

    @GetMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Person> getPerson(@RequestParam(required = false) String email) {
        if (email != null)
            return personRepository.findByEmail(email).flux();
        else
            return personRepository.findAll();
    }

    @GetMapping("/person/salary")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Person> getPersonBySalaryRange(@RequestParam double min, @RequestParam double max)
            throws PersonException {
        validateRange(min, max);
        return personRepository.findPersonBySalaryBetween(min, max);
    }

    @PostMapping("/person/age")
    public Mono<ResponseEntity<Person>> updatePersonAge(@RequestBody Person person) throws PersonException {
        validatePerson(person);
        return personRepository.findById(person.getEmail())
                .flatMap(currentPerson -> {
                    currentPerson.setAge(person.getAge());
                    return personRepository.save(currentPerson);
                })
                .map(updatedPerson -> new ResponseEntity<>(updatedPerson, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
