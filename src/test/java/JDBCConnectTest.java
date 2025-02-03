import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectTest {
  private static final String URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
  private static final String USER = "root"; // 사용자명
  private static final String PASSWORD = ""; // 비밀번호

  public static void main(String[] args) {
    Connection connection = null;

    try {
      // JDBC 드라이버 로드
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("데이터베이스에 성공적으로 연결되었습니다.");

    } catch (ClassNotFoundException e) {
      System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("데이터베이스 연결에 실패하였습니다.");
      e.printStackTrace();
    } finally {
      // 연결 종료
      if (connection != null) {
        try {
          connection.close();
          System.out.println("데이터베이스 연결이 종료되었습니다.");
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
