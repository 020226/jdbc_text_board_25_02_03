package com.ex.jdbc.board;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
  int id;
  LocalDateTime regDate;
  LocalDateTime updateDate;
  String subject;
  String content;
  // 생성자 메서드 : 객체가 생성 될 때 딱 한번 실행!
  public Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }
  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.subject = (String) articleMap.get("subject");
    this.content = (String) articleMap.get("content");
  }
  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", regDate=" + regDate +
        ", updateDate=" + updateDate +
        ", subject='" + subject + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
