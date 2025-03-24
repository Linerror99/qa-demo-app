package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }

  @PostMapping("/login")
  public String processLogin(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
    if ("admin".equals(username) && "pass".equals(password)) {
      model.addAttribute("message", "Connexion réussie !");
      return "success";
    } else {
      model.addAttribute("message", "Identifiants invalides.");
      return "login";
    }
  }
}
