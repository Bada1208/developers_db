package com.sysoiev.developers_db.builder;

import com.sysoiev.developers_db.model.Developer;
import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.model.Status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DeveloperBuilder {

    private Developer developer = new Developer();

    public static DeveloperBuilder developer(String firstName, String lastName) {
        return new DeveloperBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .salary(CommonBuilder.number("1000"))
                .birthDate(CommonBuilder.date("1990-10-12"))
                .employmentDate(CommonBuilder.date("2012-01-12"));
    }

    public static DeveloperBuilder developerDb(String firstName, String lastName) {
        return developer(firstName, lastName)
                .id(CommonBuilder.id("1"))
                .skills(CommonBuilder.list(
                        SkillBuilder.skillDb("s1").build(),
                        SkillBuilder.skillDb("s2").id(CommonBuilder.id("2")).build()))
                .status(Status.ACTIVE)
                .createdDate(CommonBuilder.datetime("2019-11-01 00:00:00"))
                .updatedDate(CommonBuilder.datetime("2019-11-01 00:00:00"));
    }

    public static DeveloperBuilder developerWithSkills(String firstName, String lastName) {
        return developer(firstName, lastName)
                .skills(CommonBuilder.list(
                        SkillBuilder.skill("s1").build(),
                        SkillBuilder.skill("s2").build()));
    }

    public DeveloperBuilder id(Long id) {
        developer.setId(id);
        return this;
    }

    public DeveloperBuilder firstName(String firstName) {
        developer.setFirstName(firstName);
        return this;
    }

    public DeveloperBuilder lastName(String lastName) {
        developer.setLastName(lastName);
        return this;
    }

    public DeveloperBuilder salary(BigDecimal salary) {
        developer.setSalary(salary);
        return this;
    }

    public DeveloperBuilder birthDate(Date birthDate) {
        developer.setBirthDate(birthDate);
        return this;
    }

    public DeveloperBuilder employmentDate(Date employmentDate) {
        developer.setEmploymentDate(employmentDate);
        return this;
    }

    public DeveloperBuilder status(Status status) {
        developer.setStatus(status);
        return this;
    }

    public DeveloperBuilder createdDate(Date date) {
        developer.setCreated(date);
        return this;
    }

    public DeveloperBuilder updatedDate(Date date) {
        developer.setUpdated(date);
        return this;
    }

    public DeveloperBuilder skills(List<Skill> skills) {
        developer.setSkills(skills);
        return this;
    }

    public Developer build() {
        return developer;
    }
}