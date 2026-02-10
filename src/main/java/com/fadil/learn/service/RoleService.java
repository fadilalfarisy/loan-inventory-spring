package com.fadil.learn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fadil.learn.model.Role;
import com.fadil.learn.model.dto.request.CreateRoleRequest;
import com.fadil.learn.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  public List<Role> getAllRole() {
    return roleRepository.findAll();
  }

  public Role getRoleById(Integer id) {
    return roleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Role with id " + id + " is not found"));
  }

  public Role createRole(CreateRoleRequest roleRequest) {
    Role role = new Role();

    role.setName(roleRequest.getName());
    role.setLevel(roleRequest.getLevel());

    return roleRepository.save(role);
  }

  public Role updateRole(Integer id, CreateRoleRequest roleRequest) {
    Role status = getRoleById(id);

    status.setName(roleRequest.getName());
    status.setLevel(roleRequest.getLevel());

    return roleRepository.save(status);
  }

  public void deleteRole(Integer id) {
    getRoleById(id);
    roleRepository.deleteById(id);
  }

}
