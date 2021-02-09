package com.sysoiev.developers_db.service.impl;

import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.model.Status;
import com.sysoiev.developers_db.repository.SkillRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static com.sysoiev.developers_db.builder.SkillBuilder.*;
import static com.sysoiev.developers_db.builder.CommonBuilder.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    @Test
    public void saveNewSkill() {

        Skill skill = skill("skillName").build();

        skillService.save(skill);

        Assert.assertEquals(skill.getStatus(), Status.ACTIVE);
        Assert.assertNotNull(skill.getCreated());
        Assert.assertNotNull(skill.getUpdated());

        verify(skillRepository, times(1)).save(skill);
    }

    @Test
    public void updateSkill() {

        Skill skill = skillDb("skillName").build();
        Date timeUpdatedBefore = skill.getUpdated();

        Skill updatedSkill = skill("updatedName").status(Status.NOT_ACTIVE).build();

        when(skillRepository.getOne(id("1"))).thenReturn(skill);

        skillService.update(skill.getId(), updatedSkill);

        Assert.assertEquals(updatedSkill.getName(), skill.getName());
        Assert.assertEquals(Status.NOT_ACTIVE, skill.getStatus());
        Assert.assertNotEquals(timeUpdatedBefore, skill.getUpdated());

        verify(skillRepository, times(1)).save(skill);
    }

    @Test
    public void deleteSkill() {

        Skill skill = skillDb("skillName").build();

        when(skillRepository.getOne(id("1"))).thenReturn(skill);

        Date timeUpdatedBefore = skill.getUpdated();
        Date timeCreatedBefore = skill.getCreated();

        skillService.delete(skill.getId());

        Assert.assertEquals(skill.getStatus(), Status.DELETED);
        Assert.assertNotEquals(timeUpdatedBefore, skill.getUpdated());
        Assert.assertEquals(timeCreatedBefore, skill.getCreated());

        verify(skillRepository, times(1)).save(skill);
    }

    @Test
    public void getById() {

        Skill skill = skillDb("skillName").build();

        when(skillRepository.getOne(id("1"))).thenReturn(skill);

        skillService.getById(skill.getId());

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, skill.getId());

        verify(skillRepository, times(1)).getOne(expectedId);
    }

    @Test
    public void findAll() {

        Skill skill = skillDb("skillName").build();
        Skill skillOther = skillDb("skillOther").id(id("2")).build();
        List<Skill> skills = list(skill, skillOther);

        when(skillRepository.findAllByOrderByIdAsc()).thenReturn(skills);

        List<Skill> actualResult = skillService.list();

        Assert.assertEquals(skills.size(), actualResult.size());

        verify(skillRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    public void findByName() {

        String skillName = "skillName";
        Skill skill = skillDb(skillName).build();

        when(skillRepository.findByName(skillName)).thenReturn(skill);

        Skill actualSkill = skillService.findByName(skillName);

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, actualSkill.getId());

        verify(skillRepository, times(1)).findByName(anyString());
    }

}
