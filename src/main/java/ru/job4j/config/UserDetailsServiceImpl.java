package ru.job4j.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.Optional;

/**
 * the server for getting user details.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * the repository where persons are stored.
     */
    private final PersonRepository repository;

    public UserDetailsServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    /**
     * it loads user details by username.
     * @param login - the login/ user name.
     * @return - user details.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Person> person = this.repository.findByLogin(login);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }

        return new User(person.get().getLogin(), person.get().getPassword(), person.get().getAuthorities());
    }
}