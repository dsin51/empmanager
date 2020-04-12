package com.demo.empmanager.initialize;

import com.demo.empmanager.documents.Person;
import com.demo.empmanager.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class PersonDataInitializer implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        initialDataSetUp();
    }

    public List<Person> data() {
        return Arrays.asList(new Person("Alex", 30,
                "alex@test.com", LocalDate.of(1990, 2,19),
                23000.00, "INR")/*,
                new Person("BCD", "Bob", 29,
                        "bob@test.com", LocalDate.of(1991, 12,20),
                        21000.00, "INR"),
                new Person("CDE", "Curran", 28,
                        "curran@test.com", LocalDate.of(1992, 11,21),
                        21000.00, "INR"),
                new Person("DEF", "Dan", 31,
                        "dan@test.com", LocalDate.of(1989, 10,22),
                        22000.00, "INR")*/);
    }

    private void initialDataSetUp() {
        personRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(personRepository::save)
                .thenMany(personRepository.findAll())
                .subscribe(person -> System.out.println("Person Inserted: " + person));
    }


}
