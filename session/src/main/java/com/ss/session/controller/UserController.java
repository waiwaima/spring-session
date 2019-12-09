package com.ss.session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

  @GetMapping("/user")
  public String getUser(HttpServletRequest request) {
    Profile profile = (Profile) request.getSession().getAttribute("user");
    if (profile == null) {
      profile = new Profile("user");
      request.getSession().setAttribute("user", profile);
    }
    String name = "";
    if (profile != null) {
      name = profile.getName();
    }
    return name;
  }

  @PostMapping("/user")
  public String setUser(@RequestBody Profile profile, HttpServletRequest request) {
    request.getSession().setAttribute("user", profile);
    return profile.getName();
  }
}
