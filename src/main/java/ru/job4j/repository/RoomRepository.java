package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Room;

import java.util.List;
import java.util.Optional;

/**
 * The room repository.
 */
@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    @Query(value = "select id, name from Room", nativeQuery = true)
    List<JsonRoom> findAllRoomsWithoutPersons();

    @Query(value = "select r.id, r.name from Room r INNER JOIN person_room pr ON pr.person_id = :personId", nativeQuery = true)
    List<JsonRoom> findByPerson(@Param("personId") int personId);

    Optional<Room> findByName(String name);

    interface JsonRoom {
        int getId();

        String getName();
    }


}
