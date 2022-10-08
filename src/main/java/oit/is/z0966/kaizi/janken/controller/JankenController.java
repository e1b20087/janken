package oit.is.z0966.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String janken() {
    return "janken.html";
  }

  @PostMapping("/janken")
  public String janken(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String sample28(@RequestParam String hand,
      ModelMap model) {
    int handno = 0;
    int aihandno = 1;
    String aihand = "Gu";
    String result = " ";
    switch (hand) {
      case "Gu":
        handno = 1;
        break;

      case "Thoki":
        handno = 2;
        break;

      default:
        handno = 0;
        break;
    }

    switch (handno) {
      case 0: // handno=0「パー」
        switch (aihandno) {
          case 0:// aihandno=0「パー」
            result = "Draw";
            break;
          case 1:// aihandno=1「グー」
            result = "You Win!";
            break;
          case 2:// aihandno=2「ちょき」
            result = "You Lose";
            break;
        }
        break;
      case 1: // handno=1「グー」
        switch (aihandno) {
          case 0:// aihandno=0「パー」
            result = "You Lose";
            break;
          case 1:// aihandno=1「グー」
            result = "Draw";
            break;
          case 2:// aihandno=2「ちょき」
            result = "You Win!";
            break;
        }
        break;
      case 2: // handno=2「ちょき」
        switch (aihandno) {
          case 0:// aihandno=0「パー」
            result = "You Win!";
            break;
          case 1:// aihandno=1「グー」
            result = "You Lose";
            break;
          case 2:// aihandno=2「ちょき」
            result = "Draw";
            break;
        }
        break;
    }

    model.addAttribute("hand", hand);
    model.addAttribute("aihand", aihand);
    model.addAttribute("result", result);
    return "jankengame.html";
  }
}
