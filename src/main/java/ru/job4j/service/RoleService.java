package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;
import ru.job4j.domain.Role;
import ru.job4j.repository.RoleRepository;

import javax.transaction.Transactional;

/**
 * The role service layer.
 */
@Service
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Role add(Role role) {
        return this.repository.save(role);
    }

    @Transactional
    public void delete(Role role) {
        this.repository.delete(role);
    }

    public Iterable<Role> findAll() {
        return this.repository.findAll();
    }

}
