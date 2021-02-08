package com.sysoiev.developers_db.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.sysoiev.developers_db.model.Skill;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillDto {

    private Long id;
    private String name;

    public static SkillDto fromSkill(Skill skill) {
        SkillDto skillDto = new SkillDto();
        skillDto.setId(skill.getId());
        skillDto.setName(skill.getName());
        return skillDto;
    }

    public static List<SkillDto> toSkillDtos(List<Skill> skills) {
        List<SkillDto> skillDtoList = new ArrayList<>();

        for (Skill skill : skills) {

            skillDtoList.add(fromSkill(skill));
        }
        return skillDtoList;
    }
}