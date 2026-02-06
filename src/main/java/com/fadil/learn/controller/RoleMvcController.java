package com.fadil.learn.controller;

import com.fadil.learn.model.Role;
import com.fadil.learn.repository.RoleRepository;
import com.fadil.learn.service.RoleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleMvcController {

  final private RoleService roleService;
  final private RoleRepository roleRepository;

  @GetMapping
  public String index(Model model) {
    List<Role> listRole = roleRepository.findAll();
    model.addAttribute("roles", listRole);
    return "role/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {

    if (id != null) {
      Role role = roleService.getRoleById(id);
      model.addAttribute("role", role);
    } else {
      model.addAttribute("role", new Role());
    }

    return "role/form";
  }

  @PostMapping("/save")
  public String save(Role role) {
    roleRepository.save(role);
    return roleRepository.findById(role.getId()).isPresent() ? "redirect:/role" : "/role/form";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable(required = true) Integer id) {
    roleRepository.deleteById(id);
    return roleRepository.findById(id).isEmpty() ? "redirect:/role" : "/role";
  }

  // @GetMapping("/delete/{id}")
  // public String delete(@PathVariable Integer id) {
  // roleRepository.deleteById(id);
  // return "redirect:/role";
  // }

  // @PostMapping(value = { "/save/{id}", "/save" })
  // public String add(@PathVariable(required = false) Integer id, Role role) {

  // if (id != null) {
  // role.setId(id);
  // }
  // roleRepository.save(role);
  // return "redirect:/role";
  // }

}
