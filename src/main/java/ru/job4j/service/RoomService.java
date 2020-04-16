package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.domain.Room;
import ru.job4j.repository.RoomRepository;

import javax.transaction.Transactional;
import java.util.*;

/**
 * The room service layer.
 */
@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<Room> add(Room room) {
        Optional<Room> result = Optional.empty();
        Optional<Room> foundRoom = this.repository.findByName(room.getName());
        if (foundRoom.isEmpty()) {
            result = Optional.of(this.repository.save(room));
        }
        return result;
    }

    @Transactional
    public void delete(Room room) {
        this.repository.delete(room);
    }

    @Transactional
    public boolean update(Room room) {
        boolean result = true;
        Optional<Room> foundRoom = this.repository.findById(room.getId());
        if (foundRoom.isEmpty()) {
            result = false;
        } else {
            this.repository.save(room);
        }
        return result;
    }

    @Transactional
    public boolean addPerson(int personId, int roomId) {
        boolean result = false;
        Optional<Room> optionalRoom = this.repository.findById(roomId);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.getPersons().add(new Person(personId));
            this.repository.save(room);
            result = true;
        }
        return result;
    }

    @Transactional
    public boolean deletePerson(int personId, int roomId) {
        boolean result = false;
        Optional<Room> optionalRoom = this.repository.findById(roomId);
        if (optionalRoom.isPresent()
                && optionalRoom.get().getPersons().remove(new Person(personId))) {
            this.repository.save(optionalRoom.get());
            result = true;
        }

        return result;
    }

    public List<RoomRepository.JsonRoom> findAll() {
        return this.repository.findAllRoomsWithoutPersons();
    }

    public Optional<Room> findById(int id) {
        return this.repository.findById(id);
    }

    public List<RoomRepository.JsonRoom> findByPerson(int id) {
        return this.repository.findByPerson(id);
    }
}
