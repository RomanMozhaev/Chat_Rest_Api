package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Room;
import ru.job4j.repository.RoomRepository;
import ru.job4j.service.RoomService;

import java.util.List;

/**
 * Controller for room commands.
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService rooms;

    @Autowired
    public RoomController(final RoomService rooms) {
        this.rooms = rooms;
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        var result = this.rooms.add(room);
        return new ResponseEntity<>(
                result.orElse(new Room()),
                result.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room(id);
        this.rooms.delete(room);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        if (this.rooms.update(room)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/addPerson/{personId}/{roomId}")
    public ResponseEntity<Void> addPerson(@PathVariable int personId, @PathVariable int roomId) {
        if (this.rooms.addPerson(personId, roomId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deletePerson/{personId}/{roomId}")
    public ResponseEntity<Void> deletePerson(@PathVariable int personId, @PathVariable int roomId) {
        if (this.rooms.deletePerson(personId, roomId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/find")
    public List<RoomRepository.JsonRoom> findAll() {
        return this.rooms.findAll();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        var room = this.rooms.findById(id);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/findByPerson/{id}")
    public List<RoomRepository.JsonRoom> findByPerson(@PathVariable int id) {
        return this.rooms.findByPerson(id);
    }
}
