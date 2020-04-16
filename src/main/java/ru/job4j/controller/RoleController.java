package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Role;
import ru.job4j.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Controller for role commands.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roles;

    @Autowired
    public RoleController(final RoleService roles) {
        this.roles = roles;
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(
                this.roles.add(role),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        Role role = new Role(name);
        this.roles.delete(role);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return StreamSupport.stream(
                this.roles.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }


}
