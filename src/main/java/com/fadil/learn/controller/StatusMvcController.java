package com.fadil.learn.controller;

import com.fadil.learn.model.Status;
import com.fadil.learn.repository.StatusRepository;
import com.fadil.learn.service.StatusService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusMvcController {

  final private StatusService statusService;
  final private StatusRepository statusRepository;

  @GetMapping
  public String index(Model model) {
    List<Status> listStatus = statusRepository.findAll();
    model.addAttribute("statuss", listStatus);
    return "status/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {
    String path = "status/add";

    if (id != null) {
      Status status = statusService.getStatusById(id);
      model.addAttribute("status", status);
      path = "status/edit";
    } else {
      model.addAttribute("status", new Status());
      path = "status/add";
    }

    return path;
  }

  @PostMapping(value = { "/save/{id}", "/save" })
  public String add(@PathVariable(required = false) Integer id, Status status) {

    if (id != null) {
      status.setId(id);
    }
    statusRepository.save(status);
    return "redirect:/status";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable Integer id) {
    statusRepository.deleteById(id);
    return "redirect:/status";
  }

}
