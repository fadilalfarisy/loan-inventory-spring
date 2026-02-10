package com.fadil.learn.controller.api;

import com.fadil.learn.model.Role;
import com.fadil.learn.model.dto.request.CreateRoleRequest;
import com.fadil.learn.service.RoleService;
import com.fadil.learn.util.CustomResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

  final private RoleService roleService;

  @GetMapping
  public ResponseEntity<Object> getAllRole(Model model) {
    List<Role> listRole = roleService.getAllRole();
    return CustomResponse.generate(HttpStatus.OK, "Get list role", listRole);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getRoleById(@PathVariable Integer id) {
    Role role = roleService.getRoleById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get role with id " + id,
        role);
  }

  @PostMapping
  public ResponseEntity<Object> createRole(@RequestBody CreateRoleRequest request) {
    Role newRole = roleService.createRole(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created role", newRole);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateRole(@PathVariable Integer id,
      @RequestBody CreateRoleRequest request) {
    roleService.updateRole(id, request);
    return CustomResponse.generate(HttpStatus.OK, "Updated role with id " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteRole(@PathVariable Integer id) {
    roleService.deleteRole(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted role with id " + id);
  }

}
