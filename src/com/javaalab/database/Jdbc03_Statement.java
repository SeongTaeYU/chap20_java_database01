package com.javaalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//오라클 드라이버 로딩 및 BOOK 테이블이 있는 계정에 접속후 Statement 객체생성
public class Jdbc03_Statement {

	public static void main(String[] args) {
		//오라클 드라이버 로딩 문자열
		String driver = "oracle.jdbc.driver.OracleDriver";	//실제 클래스이름
		//데이터베이스 연결 문자열
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";	// thin가벼운 데이터베이스
		//데이터베이스 계정명
		String dbId = "square";
		//데이터베이스 비밀번호
		String dbPwd = "1234";
		//데이터베이스 연결 객체
		Connection con = null;
		//커넥션 객체를 통해서 데이터베이스에 쿼리(SQL)를 실행해주는 객체
		Statement stmt = null;
		//실행된 쿼리문의 결과를 반환 받는 객체
		ResultSet rs = null;
		
		try {
			//1.드라이버 로딩
			//Class.forName("문자열") : 문자열로 주어진 JDBC 클래스를 빌드 패스에서 찾아서 로딩
			Class.forName(driver);	// ->하게되면  DriverManager에 자동으로 등록
			System.out.println("1. 드라이버 로드 성공!");

			//2.데이터베이스 커넥션(연결)
			/*커넥션 객체 얻기
			 * Class.forName("문자열")을 통해서 JDBC 드라이버를 메모리 로딩하면 
			 * 자동으로 DriverManager에 등록됨
			 */
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("2. 데이터베이스 연결 성공!");
			
			//3. 커넥션 객체를 통해서 데이터베이스에 쿼리(SQL)를 실행해주는 Statement 객체 얻음
			stmt = con.createStatement();
			System.out.println("3.stmt 객체 생성 성공 : "+stmt);
			
			//4. 생성한 statement 객체를 통해서 쿼리하기 위한
			// SQL 쿼리 문장 만들기(삽입, 수정, 삭제 , 조회)
			String sql = "select * from book";
			
			//5. Statement 객체의 executeQuery() 메소드를 통해서 쿼리 실행
			//데이터베이스에서 조회된 결과가 ResultSet 객체에 담겨옴
			rs = stmt.executeQuery(sql);	//rs참조변수(주소를갖고있는)
			System.out.println("5.sql명령어 성공적으로 실행됨 !! 결과 존재? :"+rs.next());//next()첫번째 데이터를 갖고와라	//1번레코드가 없는 이유 , 첫번째 데이터를 읽어버림, "true" 출력
			System.out.println();
			
			//6.rs.next()의 의미 설명
			while(rs.next()) {	//포인터가 옮겨감 1번째행 2번째행 .... 한 레코드를 갖고온다.
				System.out.println(rs.getInt("book_id")+"\t"
									+rs.getString("book_name")+"\t"
									+rs.getString("publisher")+"\t"
									+rs.getInt("price"));
				//getDate, getDouble 사용
				}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 ERR! : "+e.getMessage());
		}catch (SQLException e) {
			System.out.println("데이터베이스 연결 ERR! : "+e.getMessage());
		}finally{
			try {
				//자원해제
				if(rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if(con != null) {
					con.close();	//자원 반납
				}
			}catch(SQLException e) {
				System.out.println("Connection 자원해제 ERR!"+e.getMessage());
			}
		}
		
		
	}//end main

}//end class
