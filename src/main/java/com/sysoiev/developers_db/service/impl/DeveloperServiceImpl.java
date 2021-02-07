package com.sysoiev.developers_db.service.impl;

import com.sysoiev.developers_db.model.Developer;
import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.model.Status;
import com.sysoiev.developers_db.repository.DeveloperRepository;
import com.sysoiev.developers_db.repository.SkillRepository;
import com.sysoiev.developers_db.service.DeveloperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sysoiev.developers_db.utils.DateUtil.getCurrentDate;

@Service
@Slf4j
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public DeveloperServiceImpl(DeveloperRepository developerRepository, SkillRepository skillRepository) {
        this.developerRepository = developerRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public void save(Developer developer) {
        if (developer.getStatus() == null) developer.setStatus(Status.ACTIVE);

        developer.setCreated(getCurrentDate());
        developer.setUpdated(getCurrentDate());

        List<Skill> developerSkills = new ArrayList<>();
        for (Skill skill : developer.getSkills()) {
            Skill skillOfDeveloper = skillRepository.findByName(skill.getName());
            developerSkills.add(skillOfDeveloper);
        }

        developer.setSkills(developerSkills);
        developerRepository.save(developer);
        log.info("IN DeveloperServiceImpl save {}", developer);
    }

    @Override
    public void update(Long id, Developer updatedDeveloper) {
        Developer developer = getById(id);
        if (updatedDeveloper.getFirstName() != null) developer.setFirstName(updatedDeveloper.getFirstName());

        if (updatedDeveloper.getLastName() != null) developer.setLastName(updatedDeveloper.getLastName());

        if (updatedDeveloper.getSalary() != null) developer.setSalary(updatedDeveloper.getSalary());

        if (updatedDeveloper.getBirthDate() != null) developer.setBirthDate(updatedDeveloper.getBirthDate());

        if (updatedDeveloper.getEmploymentDate() != null)
            developer.setEmploymentDate(updatedDeveloper.getEmploymentDate());

        if (updatedDeveloper.getStatus() != null) developer.setStatus(updatedDeveloper.getStatus());

        if (updatedDeveloper.getSkills() != null) {
            List<Skill> developerSkills = new ArrayList<>();
            for (Skill skill : updatedDeveloper.getSkills()) {
                Skill skillDeveloper = skillRepository.findByName(skill.getName());
                developerSkills.add(skillDeveloper);
            }
            developer.setSkills(developerSkills);
        }
        developer.setUpdated(getCurrentDate());
        developerRepository.save(developer);

        log.info("IN DeveloperServiceImpl update {}", developer);
    }

    @Override
    public void delete(Long id) {
        Developer developer = getById(id);
        developer.setStatus(Status.DELETED);
        developer.setUpdated(getCurrentDate());

        developerRepository.save(developer);
        log.info("IN DeveloperServiceImpl delete {}", developer);
    }

    @Override
    public Developer getById(Long id) {
        Developer result = developerRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN getById - no developer found by id: {}", id);
            return null;
        }

        log.info("IN getById - developer: {} found by id: {}", result);

        return result;
    }

    @Override
    public List<Developer> list() {
        log.info("IN DeveloperServiceImpl list");
        return developerRepository.findAll();
    }
}
