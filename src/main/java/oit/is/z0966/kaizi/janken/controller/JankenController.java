package oit.is.z0966.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

public class JankenController {
  @PostMapping
  public String janken(@PathVariable String name, ModelMap model) {
    model.addAttribute("yourName", name);
    return "janken.html";
  }
}
