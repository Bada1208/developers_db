package com.sysoiev.developers_db.service;

import com.sysoiev.developers_db.model.Skill;

public interface SkillService extends BaseService<Skill> {
    Skill findByName(String name);
}
