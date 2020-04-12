package com.demo.empmanager.controllers;

import com.demo.empmanager.documents.Person;
import com.demo.empmanager.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
public class PersonControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    PersonRepository personRepository;

    public List<Person> data() {
        return Arrays.asList(new Person("Alex", 30,
                "alex@test.com", LocalDate.of(1990, 2,2),
                20000.00, "INR"));
    }

    @Before
    public void setUp() {
        personRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(personRepository::save)
                .doOnNext(person -> System.out.println("Inserted Person: " + person))
                .blockLast();
    }

    @Test
    public void addPersonTest() {
        Person person = new Person("Bob", 29,
                "bob@test.com", LocalDate.of(1991, 2,2),
                25000.00, "INR");
        webTestClient.post()
                .uri("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(person), Person.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Person.class);
    }
}
