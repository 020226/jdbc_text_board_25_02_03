package com.ex.jdbc.board;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static final String URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
  private static final String USER = "root"; // 사용자명
  private static final String PASSWORD = ""; // 비밀번호

  public static void main(String[] args) {
    System.out.println("== 자바 텍스트 게시판 시작 ==");
    int lastArticleId = 0;
    List<Article> articles = new ArrayList<>();

    Scanner sc = new Scanner(System.in);
    while(true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();
      if(cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 작성 ==");
        System.out.print("제목 : ");
        String subject = sc.nextLine();
        if(subject.trim().isEmpty()) {
          System.out.println("제목을 입력해주세요.");
          continue;
        }
        System.out.print("내용 : ");
        String content = sc.nextLine();
        if(content.trim().isEmpty()) {
          System.out.println("내용을 입력해주세요.");
          continue;
        }
        int id = ++lastArticleId;
        Article article = new Article(id, subject, content);

        Connection conn = null;
        PreparedStatement pstat = null;
        try {
          // JDBC 드라이버 로드
          Class.forName("com.mysql.cj.jdbc.Driver");
          // 데이터베이스 연결
          conn = DriverManager.getConnection(URL, USER, PASSWORD);
          System.out.println("데이터베이스에 성공적으로 연결되었습니다.");
          // SQL 삽입 쿼리
          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += ", `subject` = \"%s\"".formatted(subject);
          sql += ", content = \"%s\";".formatted(content);
          System.out.println(sql);
          pstat = conn.prepareStatement(sql);
          int affectedRows = pstat.executeUpdate();
          System.out.println("affectedRows : " + affectedRows);
          System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
        } catch (ClassNotFoundException e) {
          System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
          e.printStackTrace();
        } catch (SQLException e) {
          System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
          e.printStackTrace();
        } finally {
          // 자원 해제
          try {
            if (pstat != null && !pstat.isClosed()) pstat.close();
            if (conn != null && !conn.isClosed()) conn.close();
            System.out.println("데이터베이스 연결이 닫혔습니다.");
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
      else if(cmd.equals("/usr/article/list")) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
          // JDBC 드라이버 로드
          Class.forName("com.mysql.cj.jdbc.Driver");
          // 데이터베이스 연결
          conn = DriverManager.getConnection(URL, USER, PASSWORD);
          System.out.println("데이터베이스에 성공적으로 연결되었습니다.");
          // SQL 조회 쿼리
          String sql = "SELECT *";
          sql += " FROM article";
          sql += " ORDER BY id DESC";
          pstat = conn.prepareStatement(sql);
          // 쿼리 실행
          rs = pstat.executeQuery();
          // 결과 출력
          // rs.next() : 다음장으로 넘긴다.
          while (rs.next()) {
            int id = rs.getInt("id");
            LocalDateTime regDate = rs.getTimestamp("regDate").toLocalDateTime();
            LocalDateTime updateDate = rs.getTimestamp("updateDate").toLocalDateTime();
            String subject = rs.getString("subject");
            String content = rs.getString("content");
            Article article = new Article(id, regDate, updateDate, subject, content);
            articles.add(article);
          }
          System.out.println(articles);
        } catch (ClassNotFoundException e) {
          System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
          e.printStackTrace();
        } catch (SQLException e) {
          System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
          e.printStackTrace();
        } finally {
          // 자원 해제
          try {
            if (rs != null) rs.close();
            if (pstat != null) pstat.close();
            if (conn != null) conn.close();
            System.out.println("데이터베이스 연결이 닫혔습니다.");
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
        System.out.println("== 게시물 리스트 ==");
        System.out.println("== 번호 | 제목 | 작성 날짜 ==");
        for(Article article : articles) {
          System.out.printf(" %d | %s | %s\n", article.id, article.subject, article.regDate);
        }
      }

      else if(cmd.equals("exit")) {
        System.out.println("== 게시판을 종료합니다. ==");
        break; // 이 시점에서 반복문을 빠져나옴
      }
      else {
        System.out.println("잘못 입력 된 명령어입니다.");
      }
    }
    sc.close(); // 메모리 반납
  }
}
