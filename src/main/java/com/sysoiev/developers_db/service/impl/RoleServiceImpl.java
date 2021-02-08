package com.sysoiev.developers_db.service.impl;

import com.sysoiev.developers_db.model.Role;
import com.sysoiev.developers_db.repository.RoleRepository;
import com.sysoiev.developers_db.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> list() {
        log.info("IN RoleServiceImpl list");
        return roleRepository.findAll();
    }
}