package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Person;
import ru.job4j.domain.Role;

import java.util.List;
import java.util.Optional;

/**
 * The person repository.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByLogin(String login);

    Iterable<Person> findByRole(Role role);

    @Query(value = "select distinct p.id, p.login, p.role_name as role from person p INNER JOIN person_room pr ON pr.room_id = :roomId", nativeQuery = true)
    List<JsonPerson> findPersonsByRoom(@Param("roomId") int roomId);

    interface JsonPerson {

        int getId();

        String getLogin();

        String getRole();
    }


}
