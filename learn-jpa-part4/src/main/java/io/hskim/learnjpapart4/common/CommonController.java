package io.hskim.learnjpapart4.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

  @GetMapping(value = "/hello")
  public String getHello() {
    return "Hello";
  }
}
