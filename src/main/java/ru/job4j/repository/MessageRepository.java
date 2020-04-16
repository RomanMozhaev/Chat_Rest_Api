package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Message;

import java.util.List;

/**
 * The message repository.
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query(value = "select m.id, m.room_id as room, m.person_id as person, m.message, m.date from message m where m.id= :messageId", nativeQuery = true)
    JsonMessage findById(@Param("messageId") long messageId);

    @Query(value = "select m.id, m.room_id as room, m.person_id as person, m.message, m.date from message m where m.person_id= :personId", nativeQuery = true)
    List<JsonMessage> findByPerson(@Param("personId") int personId);

    @Query(value = "select m.id, m.room_id as room, m.person_id as person, m.message, m.date from message m where m.room_id= :roomId", nativeQuery = true)
    List<JsonMessage> findByRoom(@Param("roomId") int roomId);

    interface JsonMessage {

        long getId();

        int getRoom();

        int getPerson();

        String getMessage();

        String getDate();
    }

}
