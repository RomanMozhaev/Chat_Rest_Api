package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.domain.Role;
import ru.job4j.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The person service layer.
 */
@Service
public class PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }


    public Iterable<Person> findAll() {
        return this.repository.findAll();
    }

    public Optional<Person> findById(int id) {
        return this.repository.findById(id);
    }

    public Iterable<Person> findByRole(String roleName) {
        return this.repository.findByRole(new Role(roleName));
    }

    @Transactional
    public Optional<Person> add(Person person) {
        Optional<Person> result = Optional.empty();
        Optional<Person> foundPerson = this.repository.findByLogin(person.getLogin());
        if (foundPerson.isEmpty()) {
            result = Optional.of(this.repository.save(person));
        }
        return result;
    }

    @Transactional
    public boolean update(Person person) {
        boolean result = true;
        Optional<Person> foundPerson = this.repository.findById(person.getId());
        if (foundPerson.isEmpty()) {
            result = false;
        } else {
            this.repository.save(person);
        }
        return result;
    }

    @Transactional
    public void delete(Person person) {
        this.repository.delete(person);
    }

    public List<PersonRepository.JsonPerson> findPersonsByRoom(int id) {
        return this.repository.findPersonsByRoom(id);
    }
}
