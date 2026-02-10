package com.fadil.learn.controller.web;

import com.fadil.learn.model.Status;
import com.fadil.learn.repository.StatusRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/status")
public class StatusViewController {

  private final StatusRepository statusRepository;

  @GetMapping
  public String index(Model model) {
    List<Status> listStatus = statusRepository.findAll();
    model.addAttribute("listStatus", listStatus);
    return "status/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {

    if (id != null) {
      Status status = statusRepository.findById(id).orElse(null);
      model.addAttribute("status", status);
    } else {
      model.addAttribute("status", new Status());
    }

    return "status/form";
  }

  @PostMapping("/save")
  public String save(Status status) {
    statusRepository.save(status);
    return "redirect:/web/status";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable(required = true) Integer id) {
    statusRepository.deleteById(id);
    return "redirect:/web/status";
  }

}
