import com.ex.jdbc.board.Article;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  private static final String URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
  private static final String USER = "root"; // 사용자명
  private static final String PASSWORD = ""; // 비밀번호

  public static void main(String[] args) {
    Connection conn = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;
    List<Article> articles = new ArrayList<>();
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
        LocalDate regDate = rs.getDate("regDate").toLocalDate();
        LocalDate updateDate = rs.getDate("updateDate").toLocalDate();
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
  }
}