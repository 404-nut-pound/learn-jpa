package io.hskim.learnjpapart2.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class CommonController {

  @GetMapping(value = "/hello")
  public String getMethodName(Model model) {
    model.addAttribute("hello", "Hello, World!");

    return "hello";
  }

}
