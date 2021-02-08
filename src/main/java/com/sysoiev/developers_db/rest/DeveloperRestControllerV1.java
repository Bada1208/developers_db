package com.sysoiev.developers_db.rest;

import com.sysoiev.developers_db.dto.DeveloperDto;
import com.sysoiev.developers_db.model.Developer;
import com.sysoiev.developers_db.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {

    private final DeveloperService developerService;

    @Autowired
    public DeveloperRestControllerV1(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDto> get(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Developer developer = this.developerService.getById(id);

        if (developer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DeveloperDto.fromDeveloper(developer));
    }

    @GetMapping
    public ResponseEntity<List<DeveloperDto>> getAll() {

        List<Developer> developers = this.developerService.list();

        if (developers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DeveloperDto.toDeveloperDtos(developers));
    }

    @PostMapping
    public ResponseEntity<Developer> save(@RequestBody Developer developer) {

        if (developer == null) {
            return ResponseEntity.badRequest().build();
        }

        this.developerService.save(developer);

        return ResponseEntity.status(HttpStatus.CREATED).body(developer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Developer> update(@PathVariable Long id, @RequestBody Developer developer) {

        if (id == null || developer == null) {
            return ResponseEntity.badRequest().build();
        }

        this.developerService.update(id, developer);

        return ResponseEntity.accepted().body(developer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Developer developer = this.developerService.getById(id);

        if (developer == null) {
            return ResponseEntity.notFound().build();
        }

        this.developerService.delete(id);

        return ResponseEntity.accepted().build();
    }
}