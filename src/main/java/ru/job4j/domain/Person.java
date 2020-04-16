package ru.job4j.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * the person entity.
 */
@Entity
public class Person {

    /**
     * the person id.
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * the person login.
     */
    @Column(unique = true, nullable = false)
    private String login;

    /**
     * the person password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * the person role
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_name", foreignKey = @ForeignKey(name = "name"))
    private Role role;

    public Person() {
    }

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id || login.equals(person.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
