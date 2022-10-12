package oit.is.z0966.kaizi.janken.model;

public class Janken {
  int no;
  String hand;

  public void handNo() {
    switch (hand) {
      case "Gu":
        this.setNo(1);
        break;

      case "Thoki":
        this.setNo(2);
        break;

      default:
        this.setNo(0);
        break;
    }
  }

  public String buttle(int cpno) {
    String result = " ";
    switch (this.no) {
      case 0: // handno=0「パー」
        switch (cpno) {
          case 0:// aino=0「パー」
            result = "Draw";
            break;
          case 1:// aino=1「グー」
            result = "You Win!";
            break;
          case 2:// aino=2「ちょき」
            result = "You Lose";
            break;
        }
        break;
      case 1: // handno=1「グー」
        switch (cpno) {
          case 0:// aino=0「パー」
            result = "You Lose";
            break;
          case 1:// aino=1「グー」
            result = "Draw";
            break;
          case 2:// aino=2「ちょき」
            result = "You Win!";
            break;
        }
        break;
      case 2: // handno=2「ちょき」
        switch (cpno) {
          case 0:// aino=0「パー」
            result = "You Win!";
            break;
          case 1:// aino=1「グー」
            result = "You Lose";
            break;
          case 2:// aino=2「ちょき」
            result = "Draw";
            break;
        }
        break;
    }
    return result;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getHand() {
    return hand;
  }

  public void setHand(String hand) {
    this.hand = hand;
  }
}
