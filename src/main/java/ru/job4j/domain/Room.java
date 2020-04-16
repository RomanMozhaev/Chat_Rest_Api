package ru.job4j.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * the room entity.
 */
@Entity
public class Room {

    /**
     * the room id.
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * the room name.
     */
    @Column(nullable = false, unique = true)
    String name;

    /**
     * the persons in the room.
     */
    @ManyToMany
    @JoinTable(name = "person_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> persons;

    /**
     * the messages in the room.
     */
    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Message> messages;

    public Room() {
    }

    public Room(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
