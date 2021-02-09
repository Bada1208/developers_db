package com.sysoiev.developers_db.builder;


import com.sysoiev.developers_db.model.Skill;
import com.sysoiev.developers_db.model.Status;

import java.util.Date;

public class SkillBuilder {

    private Skill skill = new Skill();

    public static SkillBuilder skillDb(String name) {
        return skill(name)
                .id(CommonBuilder.id("1"))
                .status(Status.ACTIVE)
                .createdDate(CommonBuilder.datetime("2019-11-01 00:00:00"))
                .updatedDate(CommonBuilder.datetime("2019-11-01 00:00:00"));
    }

    public static SkillBuilder skill(String name) {
        return new SkillBuilder()
                .name(name);
    }

    public SkillBuilder id(Long id) {
        skill.setId(id);
        return this;
    }

    public SkillBuilder name(String name) {
        skill.setName(name);
        return this;
    }

    public SkillBuilder status(Status status) {
        skill.setStatus(status);
        return this;
    }

    public SkillBuilder createdDate(Date date) {
        skill.setCreated(date);
        return this;
    }

    public SkillBuilder updatedDate(Date date) {
        skill.setUpdated(date);
        return this;
    }

    public Skill build() {
        return skill;
    }
}