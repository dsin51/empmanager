package com.demo.empmanager.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private int age;
    @Id
    private String email;   // email should be unique, hence defined as Id
    private LocalDate dob;  // current impl -> yyyy/mm/dd
    private double salary;
    private String currency;    // refactor to use enum
}
