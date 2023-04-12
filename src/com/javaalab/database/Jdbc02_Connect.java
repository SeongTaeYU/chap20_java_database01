package com.javaalab.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// * 오라클JDBC 드라이버 로딩 및 square 계정에 접속
public class Jdbc02_Connect {
	public static void main(String[] args)  {
		//오라클 jdbc 드라이버 클래스의 완전한 풀네임
		String driver = "oracle.jdbc.driver.OracleDriver";
//		 * 데이베이스 연결 문자열
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "square";
		String dbPwd = "1234";
		//커넥션 객체 선언
		Connection con = null;
		try {
			/**
			 * Class.forName("문자열") : 문자열로 주어진 JDBC 클래스를 빌드 패스에서 찾아서 로딩
			 */
			Class.forName(driver);
			System.out.println("드라이버 로드 성공!");
			/*커넥션 객체 얻기
			 * Class.forName("문자열")을 통해서 JDBC 드라이버를 메모리 로딩하면 
			 * 자동으로 DriverManager에 등록됨
			 */
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("데이터베이스 연결 성공!");
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 ERR! : "+e.getMessage());
		}catch(SQLException e) {
			System.out.println("데이터베이스 연결 ERR! : "+e.getMessage());
		}finally {	//모든게  수행되도 finally 구문을 탄다.  
			try {
				if(con != null) {
					con.close();	//자원 반납
				}
			}catch(SQLException e) {
				System.out.println("Connection 자원해제 ERR! : "+e.getMessage());
			}
		}
	}//end main
}//end calss
