package com.sysoiev.developers_db.repository;

import com.sysoiev.developers_db.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findByName(String name);

    List<Skill> findAllByOrderByIdAsc();
}
