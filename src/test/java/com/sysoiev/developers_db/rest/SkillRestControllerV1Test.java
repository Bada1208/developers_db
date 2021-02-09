package com.sysoiev.developers_db.rest;

import com.sysoiev.developers_db.builder.CommonBuilder;
import com.sysoiev.developers_db.builder.SkillBuilder;
import com.sysoiev.developers_db.dto.SkillDto;
import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.service.SkillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkillRestControllerV1Test {

    @Mock
    private SkillService skillService;

    @InjectMocks
    private SkillRestControllerV1 skillRestController;

    @Test
    public void get() {

        Skill skill = SkillBuilder.skillDb("s1").build();
        when(skillService.getById(skill.getId())).thenReturn(skill);
        ResponseEntity<SkillDto> response = skillRestController.get(skill.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(skill.getName(), Objects.requireNonNull(response.getBody()).getName());
        assertEquals(skill.getId(), response.getBody().getId());
    }

    @Test
    public void getFail() {

        ResponseEntity<SkillDto> response1 = skillRestController.get(null);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        Long id = CommonBuilder.id("1");
        when(skillService.getById(id)).thenReturn(null);
        ResponseEntity<SkillDto> response2 = skillRestController.get(id);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void getAll() {
        Skill skill1 = SkillBuilder.skillDb("s1").build();
        Skill skill2 = SkillBuilder.skillDb("s2").id(CommonBuilder.id("2")).build();
        List<Skill> skills = CommonBuilder.list(skill1, skill2);

        when(skillService.list()).thenReturn(skills);

        ResponseEntity<List<SkillDto>> response = skillRestController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(skills.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(skill1.getName(), response.getBody().get(0).getName());
        assertEquals(skill2.getName(), response.getBody().get(1).getName());
    }

    @Test
    public void getAllFail() {

        when(skillService.list()).thenReturn(new ArrayList<>());
        ResponseEntity<List<SkillDto>> response = skillRestController.getAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void save() {

        Skill skill = SkillBuilder.skill("s1").build();

        ResponseEntity<Skill> response = skillRestController.save(skill);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(skill.getName(), Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    public void saveFail() {
        ResponseEntity<Skill> response = skillRestController.save(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void update() {
        Skill skill = SkillBuilder.skill("s2").build();

        ResponseEntity<Skill> response = skillRestController.update(CommonBuilder.id("1"), skill);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(skill.getName(), Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    public void updateFail() {
        ResponseEntity<Skill> response = skillRestController.update(null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void delete() {

        Skill skill = SkillBuilder.skillDb("s1").build();
        when(skillService.getById(skill.getId())).thenReturn(skill);

        ResponseEntity<Skill> response = skillRestController.delete(skill.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void deleteFail() {

        Long id = CommonBuilder.id("1");
        when(skillService.getById(id)).thenReturn(null);

        ResponseEntity<Skill> response = skillRestController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}