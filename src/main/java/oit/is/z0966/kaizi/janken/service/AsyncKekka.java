package oit.is.z0966.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0966.kaizi.janken.model.Match;
import oit.is.z0966.kaizi.janken.model.MatchMapper;

import oit.is.z0966.kaizi.janken.model.MatchInfo;
import oit.is.z0966.kaizi.janken.model.MatchInfoMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper MatchMapper;

  @Autowired
  MatchInfoMapper MatchinfoMapper;

  /**
   * 購入対象の果物IDの果物をDBから削除し，購入対象の果物オブジェクトを返す
   *
   * @param id 購入対象の果物のID
   * @return 購入対象の果物のオブジェクトを返す
   */

  @Transactional
  public MatchInfo synselectallid(int id) {
    // 試合中の対象を取得
    MatchInfo acMatch = MatchinfoMapper.selectallidBymatchinfo(id);

    // 非同期でDB更新したことを共有する際に利用する
    // this.dbUpdated = true;

    return acMatch;
  }

  public Match syninsertMatch(int user1, int user2, String user1Hand, String user2Hand) {
    Match inMatch = new Match();
    inMatch.setUser1(user1);
    inMatch.setUser2(user2);
    inMatch.setUser1Hand(user1Hand);
    inMatch.setUser2Hand(user2Hand);
    inMatch.setActive(true);
    // 試合中の対象を取得
    MatchMapper.insertMatch(inMatch);

    // 非同期でDB更新したことを共有する際に利用する
    this.dbUpdated = true;

    return inMatch;
  }

  public ArrayList<Match> syncBytrue() {
    // trueを取得
    ArrayList<Match> finMatch = MatchMapper.selectByistrue();

    // 非同期でDB更新したことを共有する際に利用する
    // this.dbUpdated = true;

    return finMatch;
  }

  /**
   * dbUpdatedがtrueのときのみブラウザにDBからフルーツリストを取得して送付する
   *
   * @param emitter
   */
  @Async
  public void asyncShowMatch(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする
        ArrayList<Match> finmatch = this.syncBytrue();
        emitter.send(finmatch);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowMatch complete");
  }

}
