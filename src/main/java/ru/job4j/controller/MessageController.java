package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.*;
import ru.job4j.repository.MessageRepository;
import ru.job4j.service.MessageService;

import java.util.List;

/**
 * Controller for message commands.
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messages;

    @Autowired
    public MessageController(final MessageService messages) {
        this.messages = messages;
    }


    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return new ResponseEntity<>(
                this.messages.add(message),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = new Message(id);
        this.messages.delete(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/{id}")
    public MessageRepository.JsonMessage findById(@PathVariable long id) {
        return this.messages.findById(id);
    }

    @GetMapping("/findByPerson/{id}")
    public List<MessageRepository.JsonMessage> findByPerson(@PathVariable int id) {
        return this.messages.findByPerson(id);
    }

    @GetMapping("/findByRoom/{id}")
    public List<MessageRepository.JsonMessage> findByRoom(@PathVariable int id) {
        return this.messages.findByRoom(id);
    }
}
