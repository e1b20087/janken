package oit.is.z0966.kaizi.janken.controller;

//import javax.swing.text.html.parser.Entity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.z0966.kaizi.janken.model.Entry;

import oit.is.z0966.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @Autowired
  private Entry room;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);

    return "janken.html";
  }

  @PostMapping("/janken")
  public String janken(@RequestParam String name, ModelMap model) {
    String yname = "Hi!" + name;
    model.addAttribute("name", yname);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String sample28(@RequestParam String hand,
      ModelMap model) {
    Janken you = new Janken();
    you.setNo(0);
    you.setHand(hand);
    Janken cp = new Janken();
    cp.setNo(1);
    cp.setHand("Gu");
    String result = " ";
    you.handNo();
    /*
     * switch (hand) {
     * case "Gu":
     * handno = 1;
     * break;
     *
     * case "Thoki":
     * handno = 2;
     * break;
     *
     * default:
     * handno = 0;
     * break;
     * }
     */
    result = you.buttle(cp.getNo());
    /*
     * switch (handno) {
     * case 0: // handno=0「パー」
     * switch (aihandno) {
     * case 0:// aihandno=0「パー」
     * result = "Draw";
     * break;
     * case 1:// aihandno=1「グー」
     * result = "You Win!";
     * break;
     * case 2:// aihandno=2「ちょき」
     * result = "You Lose";
     * break;
     * }
     * break;
     * case 1: // handno=1「グー」
     * switch (aihandno) {
     * case 0:// aihandno=0「パー」
     * result = "You Lose";
     * break;
     * case 1:// aihandno=1「グー」
     * result = "Draw";
     * break;
     * case 2:// aihandno=2「ちょき」
     * result = "You Win!";
     * break;
     * }
     * break;
     * case 2: // handno=2「ちょき」
     * switch (aihandno) {
     * case 0:// aihandno=0「パー」
     * result = "You Win!";
     * break;
     * case 1:// aihandno=1「グー」
     * result = "You Lose";
     * break;
     * case 2:// aihandno=2「ちょき」
     * result = "Draw";
     * break;
     * }
     * break;
     * }
     */

    model.addAttribute("hand", hand);
    model.addAttribute("aihand", cp.getHand());
    model.addAttribute("result", result);
    return "janken.html";
  }

  public String sample38(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);

    return "janken.html";
  }

}
