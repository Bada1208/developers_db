package com.sysoiev.developers_db.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sysoiev.developers_db.model.Role;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolesDto {

    private Long id;
    private String name;

    public static RolesDto fromRole(Role role) {
        RolesDto rolesDto = new RolesDto();
        rolesDto.setId(role.getId());
        rolesDto.setName(role.getName());
        return rolesDto;
    }
}