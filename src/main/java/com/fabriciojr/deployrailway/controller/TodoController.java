package com.fabriciojr.deployrailway.controller;

import com.fabriciojr.deployrailway.domain.Todo;
import com.fabriciojr.deployrailway.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @GetMapping
    public ResponseEntity<List<Todo>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> find(@PathVariable final UUID id) {
        final Optional<Todo> todo = repository.findById(id);

        if(todo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(todo.get());
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody final Todo todo) {
        final Todo saved = repository.save(todo);
        return ResponseEntity.created(URI.create(saved.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable UUID id,
                                       @RequestBody Todo dto) {
        final Optional<Todo> saved = repository.findById(id);

        if(saved.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final Todo todo = saved.get();
        todo.setTitle(dto.getTitle());
        todo.setContent(dto.getContent());

        return ResponseEntity.ok(repository.save(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        final Optional<Todo> saved = repository.findById(id);

        if(saved.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repository.delete(saved.get());
        return ResponseEntity.ok().build();
    }

}
