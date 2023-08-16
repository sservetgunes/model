package com.example.model.service;

import com.example.model.veri.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    void deleteRole(Long id);
}
