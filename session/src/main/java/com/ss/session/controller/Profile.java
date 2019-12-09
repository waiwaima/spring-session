package com.ss.session.controller;

import java.io.Serializable;

public class Profile implements Serializable {
  private String name;

  public Profile(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
