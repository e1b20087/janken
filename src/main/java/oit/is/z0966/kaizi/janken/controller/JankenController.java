package oit.is.z0966.kaizi.janken.controller;

//import javax.swing.text.html.parser.Entity;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;

import oit.is.z0966.kaizi.janken.model.Entry;

import oit.is.z0966.kaizi.janken.model.Janken;

import oit.is.z0966.kaizi.janken.model.User;
import oit.is.z0966.kaizi.janken.model.UserMapper;

import oit.is.z0966.kaizi.janken.model.Match;
import oit.is.z0966.kaizi.janken.model.MatchMapper;

import oit.is.z0966.kaizi.janken.model.MatchInfo;
import oit.is.z0966.kaizi.janken.model.MatchInfoMapper;

import oit.is.z0966.kaizi.janken.service.AsyncKekka;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class JankenController {

  @Autowired
  private Entry room;

  @Autowired
  UserMapper UserMapper;

  @Autowired
  MatchMapper MatchMapper;

  @Autowired
  MatchInfoMapper MatchInfoMapper;

  @Autowired
  AsyncKekka kekka;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);
    ArrayList<User> Users = UserMapper.selectAllByname();
    model.addAttribute("Users", Users);
    ArrayList<Match> Matchs = MatchMapper.selectAllBymatch();
    model.addAttribute("Matchs", Matchs);
    ArrayList<Match> Matchinfo = MatchInfoMapper.selecttrueBymatchinfo();
    model.addAttribute("MatchInfo", Matchinfo);
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

  /**
   *
   *
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/match")
  public String match(Principal prin, @RequestParam Integer id, ModelMap model) {
    String loginUser = prin.getName();
    User MUsers = UserMapper.selectById(id);
    model.addAttribute("MUsers", MUsers);
    model.addAttribute("loginUser", loginUser);
    // ModelMap型変数のmodelにtasuResult2という名前の変数で，tasuResultの値を登録する．
    // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
    return "match.html";

  }

  @GetMapping("/fight")
  @Transactional
  public String wait(Principal prin, @RequestParam Integer id, @RequestParam String hand, ModelMap model) {
    String loginUser = prin.getName();
    User MUsers = UserMapper.selectById(id);
    User yUsers = UserMapper.selectBynId(loginUser);

    int youid = yUsers.getId();
    int mid = MUsers.getId();

    MatchInfo Matchcon = this.kekka.synselectallid(youid);
    boolean matchac = Matchcon.getisActive();

    if (matchac != true) {
      MatchInfo Matchinfo = new MatchInfo();
      Matchinfo.setUser1(youid);
      Matchinfo.setUser2(mid);
      Matchinfo.setUser1Hand(hand);
      Matchinfo.setActive(true);
      MatchInfoMapper.insertMatchinfo(Matchinfo);
    } else {

    }

    return "wait.html";

  }

  /**
   * JavaScriptからEventSourceとして呼び出されるGETリクエスト SseEmitterを返すことで，PUSH型の通信を実現する
   *
   * @return
   */
  @GetMapping("/wait/sse")
  public SseEmitter sample59() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.kekka.asyncShowMatch(sseEmitter);
    return sseEmitter;
  }

  /*
   * @GetMapping("/fight")
   *
   * @Transactional
   * public String match(Principal prin, @RequestParam Integer id, @RequestParam
   * String hand, ModelMap model) {
   * String loginUser = prin.getName();
   * User MUsers = UserMapper.selectById(id);
   * User yUsers = UserMapper.selectBynId(loginUser);
   * Janken you = new Janken();
   * you.setNo(0);
   * you.setHand(hand);
   * Janken cp = new Janken();
   * cp.setNo(1);
   * cp.setHand("Gu");
   * String result = " ";
   * you.handNo();
   * result = you.buttle(cp.getNo());
   * Match Match1 = new Match();
   * int youid = yUsers.getId();
   * int mid = MUsers.getId();
   * String youhand = you.getHand();
   * String mhand = cp.getHand();
   * Match1.setUser1(youid);
   * Match1.setUser2(mid);
   * Match1.setUser1Hand(youhand);
   * Match1.setUser2Hand(mhand);
   * MatchMapper.insertMatch(Match1);
   * model.addAttribute("hand", hand);
   * model.addAttribute("aihand", cp.getHand());
   * model.addAttribute("result", result);
   * model.addAttribute("MUsers", MUsers);
   * model.addAttribute("loginUser", loginUser);
   * model.addAttribute("nowMatch", Match1);
   * return "match.html";
   * }
   */
  public String sample38(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);

    return "janken.html";
  }

}
