package com.sysoiev.developers_db.service.impl;

import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.model.Status;
import com.sysoiev.developers_db.repository.SkillRepository;
import com.sysoiev.developers_db.service.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sysoiev.developers_db.utils.DateUtil.getCurrentDate;

@Service
@Slf4j
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill findByName(String name) {

        Skill result = skillRepository.findByName(name);

        if (result == null) {
            log.warn("IN findByName - no skill found by name: {}", name);
            return null;
        }

        log.info("IN findByName - skill: {} found by name: {}", result);

        return result;
    }

    @Override
    public void save(Skill skill) {
        if (skill.getStatus() == null) skill.setStatus(Status.ACTIVE);

        skill.setCreated(getCurrentDate());
        skill.setUpdated(getCurrentDate());

        skillRepository.save(skill);
        log.info("IN SkillServiceImpl save {}", skill);
    }

    @Override
    public void update(Long id, Skill updatedSkill) {
        Skill skill = getById(id);

        if (updatedSkill.getName() != null) skill.setName(updatedSkill.getName());

        if (updatedSkill.getStatus() != null) skill.setStatus(updatedSkill.getStatus());

        skill.setUpdated(getCurrentDate());
        skillRepository.save(skill);
        log.info("IN SkillServiceImpl update {}", skill);
    }

    @Override
    public void delete(Long id) {
        Skill skill = getById(id);
        skill.setUpdated(getCurrentDate());
        skill.setStatus(Status.DELETED);

        skillRepository.save(skill);
        log.info("IN SkillServiceImpl delete {}", skill);
    }

    @Override
    public Skill getById(Long id) {
        Skill result = skillRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN getById - no skill found by id: {}", id);
            return null;
        }

        log.info("IN getById - skill: {} found by id: {}", result);

        return result;
    }

    @Override
    public List<Skill> list() {
        log.info("IN SkillServiceImpl list");
        return skillRepository.findAllByOrderByIdAsc();
    }

}