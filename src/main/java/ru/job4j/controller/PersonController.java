package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Person;
import ru.job4j.domain.Role;
import ru.job4j.repository.PersonRepository;
import ru.job4j.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Controller for person commands.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService persons;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public PersonController(final PersonService persons,
                            final BCryptPasswordEncoder encoder) {
        this.persons = persons;
        this.encoder = encoder;
    }

    @GetMapping("/find")
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.persons.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/findByRole/{role}")
    public List<Person> findByRole(@PathVariable String role) {
        return StreamSupport.stream(
                this.persons.findByRole(role).spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/findByRoom/{id}")
    public List<PersonRepository.JsonPerson> findPersonsByRoom(@PathVariable int id) {
        return this.persons.findPersonsByRoom(id);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person cryptPerson = person;
        cryptPerson.setRole(new Role("ROLE_USER"));
        cryptPerson.setPassword(encoder.encode(person.getPassword()));
        var result = this.persons.add(cryptPerson);
        return new ResponseEntity<>(
                result.orElse(new Person()),
                result.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adminSign-up")
    public ResponseEntity<Person> createAdmin(@RequestBody Person person) {
        Person cryptPerson = person;
        cryptPerson.setRole(new Role("ROLE_ADMIN"));
        cryptPerson.setPassword(encoder.encode(person.getPassword()));
        var result = this.persons.add(cryptPerson);
        return new ResponseEntity<>(
                result.orElse(new Person()),
                result.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (this.persons.update(person)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person(id);
        this.persons.delete(person);
        return ResponseEntity.ok().build();
    }
}
