package com.sysoiev.developers_db.rest;

import com.sysoiev.developers_db.dto.SkillDto;
import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillRestControllerV1 {

    private final SkillService skillService;

    @Autowired
    public SkillRestControllerV1(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDto> get(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Skill skill = this.skillService.getById(id);

        if (skill == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(SkillDto.fromSkill(skill));
    }

    @GetMapping
    public ResponseEntity<List<SkillDto>> getAll() {

        List<Skill> skills = this.skillService.list();

        if (skills.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(SkillDto.toSkillDtos(skills));
    }

    @PostMapping
    public ResponseEntity<Skill> save(@RequestBody Skill skill) {

        if (skill == null) {
            return ResponseEntity.badRequest().build();
        }

        this.skillService.save(skill);

        return ResponseEntity.status(HttpStatus.CREATED).body(skill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> update(@PathVariable Long id, @RequestBody Skill skill) {

        if (id == null || skill == null) {
            return ResponseEntity.badRequest().build();
        }

        this.skillService.update(id, skill);

        return ResponseEntity.accepted().body(skill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Skill> delete(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Skill skill = this.skillService.getById(id);

        if (skill == null) {
            return ResponseEntity.notFound().build();
        }

        this.skillService.delete(id);

        return ResponseEntity.accepted().build();
    }
}