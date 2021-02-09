package com.sysoiev.developers_db.service.impl;


import com.sysoiev.developers_db.builder.CommonBuilder;
import com.sysoiev.developers_db.builder.SkillBuilder;
import com.sysoiev.developers_db.model.Developer;
import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.model.Status;
import com.sysoiev.developers_db.repository.DeveloperRepository;
import com.sysoiev.developers_db.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static com.sysoiev.developers_db.builder.CommonBuilder.*;
import static com.sysoiev.developers_db.builder.DeveloperBuilder.*;
import static org.mockito.Mockito.*;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceImplTest {
    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private DeveloperServiceImpl developerService;

    @Test
    public void saveNewDeveloper() {

        Skill skill1 = SkillBuilder.skillDb("s1").build();
        Skill skill2 = SkillBuilder.skillDb("s2").id(id("2")).build();

        Developer developer = developerWithSkills("Alex", "Ivanov").build();

        when(skillRepository.findByName("s1")).thenReturn(skill1);
        when(skillRepository.findByName("s2")).thenReturn(skill2);

        developerService.save(developer);

        Assert.assertEquals(developer.getStatus(), Status.ACTIVE);
        Assert.assertNotNull(developer.getCreated());
        Assert.assertNotNull(developer.getUpdated());

        verify(developerRepository, times(1)).save(developer);
        for (Skill skill : developer.getSkills()) {
            verify(skillRepository, times(1)).findByName(skill.getName());
        }
    }

    @Test
    public void update() {
        Developer developer = developerDb("Alex", "Ivanov").build();
        Date timeUpdatedBefore = developer.getUpdated();

        Skill skill1 = SkillBuilder.skillDb("s1").build();

        Developer updatedDeveloper = developer("Petr", "Petrov")
                .salary(number("800"))
                .birthDate(date("1986-11-11"))
                .employmentDate(date("2018-11-11"))
                .status(Status.NOT_ACTIVE)
                .skills(CommonBuilder.list(skill1))
                .build();

        when(skillRepository.findByName("s1")).thenReturn(skill1);
        when(developerRepository.getOne(id("1"))).thenReturn(developer);

        developerService.update(developer.getId(), updatedDeveloper);

        Assert.assertEquals(updatedDeveloper.getFirstName(), developer.getFirstName());
        Assert.assertEquals(updatedDeveloper.getLastName(), developer.getLastName());
        Assert.assertEquals(updatedDeveloper.getSalary(), developer.getSalary());
        Assert.assertEquals(updatedDeveloper.getBirthDate(), developer.getBirthDate());
        Assert.assertEquals(updatedDeveloper.getEmploymentDate(), developer.getEmploymentDate());
        Assert.assertEquals(updatedDeveloper.getSkills().size(), developer.getSkills().size());
        Assert.assertEquals(Status.NOT_ACTIVE, developer.getStatus());
        Assert.assertNotEquals(timeUpdatedBefore, developer.getUpdated());

        verify(developerRepository, times(1)).save(developer);
    }

    @Test
    public void delete() {

        Developer developer = developerDb("Alex", "Ivanov").build();
        Date timeUpdatedBefore = developer.getUpdated();
        Date timeCreatedBefore = developer.getCreated();

        when(developerRepository.getOne(id("1"))).thenReturn(developer);

        developerService.delete(developer.getId());

        Assert.assertEquals(developer.getStatus(), Status.DELETED);
        Assert.assertNotEquals(timeUpdatedBefore, developer.getUpdated());
        Assert.assertEquals(timeCreatedBefore, developer.getCreated());

        verify(developerRepository, times(1)).save(developer);
    }

    @Test
    public void getById() {

        Developer developer = developerDb("Alex", "Ivanov").build();

        when(developerRepository.getOne(id("1"))).thenReturn(developer);

        developerService.getById(developer.getId());

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, developer.getId());

        verify(developerRepository, times(1)).getOne(expectedId);
    }

    @Test
    public void list() {

        Developer developer1 = developerDb("Alex", "Ivanov").build();
        Developer developer2 = developerDb("Petr", "Petrov").id(id("2")).build();
        List<Developer> employees = CommonBuilder.list(developer1, developer2);

        when(developerRepository.findAll()).thenReturn(employees);

        List<Developer> actualResult = developerService.list();

        Assert.assertEquals(employees.size(), actualResult.size());

        verify(developerRepository, times(1)).findAll();
    }

    @Test
    public void updateFirstAndLastName() {
        Developer developer = developerDb("Alex", "Ivanov").build();

        Developer developerUpdatedFistName = developer("AlexUpdated", null)
                .birthDate(null)
                .employmentDate(null)
                .salary(null)
                .status(null)
                .build();

        Developer developerUpdatedLastName = developer(null, "IvanovUpdated")
                .birthDate(null)
                .employmentDate(null)
                .salary(null)
                .status(null)
                .build();

        when(developerRepository.getOne(id("1"))).thenReturn(developer);

        developerService.update(developer.getId(), developerUpdatedFistName);

        Assert.assertEquals(developerUpdatedFistName.getFirstName(), developer.getFirstName());

        developerService.update(developer.getId(), developerUpdatedLastName);

        Assert.assertEquals(developerUpdatedLastName.getLastName(), developer.getLastName());

    }
}