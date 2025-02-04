package com.ex.jdbc.board;

import java.time.LocalDate;

public class Article {
  int id;
  LocalDate regDate;
  LocalDate updateDate;
  String subject;
  String content;
  // 생성자 메서드 : 객체가 생성 될 때 딱 한번 실행!
  public Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }
  public Article(int id, LocalDate regDate, LocalDate updateDate, String subject, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.subject = subject;
    this.content = content;
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
