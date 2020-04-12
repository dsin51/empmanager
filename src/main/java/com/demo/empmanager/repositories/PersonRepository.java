package com.demo.empmanager.repositories;

import com.demo.empmanager.documents.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
    Mono<Person> findByEmail(String email);
    Flux<Person> findPersonBySalaryBetween(double min, double max);
}
