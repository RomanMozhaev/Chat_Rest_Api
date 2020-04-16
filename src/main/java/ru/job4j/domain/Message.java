package ru.job4j.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * Message entity
 */
@Entity
public class Message {

    /**
     * message id.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * the message room.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "id"))
    private Room room;

    /**
     * the person who creates the message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "id"))
    private Person person;

    /**
     * the message text.
     */
    @Column(nullable = false)
    private String message;

    /**
     * the create date.
     */
    @Column(nullable = false)
    private Calendar date;

    public Message() {
        this.date = Calendar.getInstance();
    }

    public Message(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message1 = (Message) o;
        return id == message1.id
                && Objects.equals(room, message1.room)
                && Objects.equals(person, message1.person)
                && Objects.equals(message, message1.message)
                && Objects.equals(date, message1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, person, message, date);
    }
}
