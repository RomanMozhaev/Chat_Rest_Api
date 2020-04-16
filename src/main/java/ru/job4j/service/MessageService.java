package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Message;
import ru.job4j.repository.MessageRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The message service layer.
 */
@Service
public class MessageService {

    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Message add(Message message) {
        return this.repository.save(message);
    }

    @Transactional
    public void delete(Message message) {
        this.repository.delete(message);
    }

    public MessageRepository.JsonMessage findById(long id) {
        return this.repository.findById(id);
    }

    public List<MessageRepository.JsonMessage> findByPerson(int id) {
        return this.repository.findByPerson(id);
    }

    public List<MessageRepository.JsonMessage> findByRoom(int id) {
        return this.repository.findByRoom(id);
    }
}
