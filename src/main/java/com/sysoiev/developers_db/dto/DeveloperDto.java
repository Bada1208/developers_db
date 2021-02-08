package com.sysoiev.developers_db.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sysoiev.developers_db.model.Developer;
import com.sysoiev.developers_db.model.Skill;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeveloperDto {

    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date employmentDate;

    private List<SkillDto> skills;


    public static DeveloperDto fromDeveloper(Developer developer) {

        DeveloperDto developerDto = new DeveloperDto();

        developerDto.setId(developer.getId());
        developerDto.setFirstName(developer.getFirstName());
        developerDto.setLastName(developer.getLastName());
        developerDto.setEmploymentDate(developer.getEmploymentDate());
        developerDto.setBirthDate(developer.getBirthDate());
        developerDto.setSalary(developer.getSalary());

        List<SkillDto> skillDtoList = new ArrayList<>();

        for (Skill skill : developer.getSkills()) {
            skillDtoList.add(SkillDto.fromSkill(skill));
        }

        developerDto.setSkills(skillDtoList);

        return developerDto;
    }

    public static List<DeveloperDto> toDeveloperDtos(List<Developer> developers) {

        List<DeveloperDto> developerDtoList = new ArrayList<>();

        for (Developer developer : developers) {
            developerDtoList.add(fromDeveloper(developer));
        }
        return developerDtoList;
    }
}

