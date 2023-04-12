package com.javaalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//PreparedStatement를 사용한 삭제
public class Jdbc07_delete {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "square";
		String dbPwd = "1234";

		Connection con = null;
		PreparedStatement pstmt = null; // PreparedStatement 객체 저장
		ResultSet rs = null; // select의 결과 객체

		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공!");

			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("데이터베이스 연결 성공");

			// delete
			int result = 0;
			int bookId = 14;
			String query = "delete from book where book_id = ?";

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bookId);
			result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("삭제 성공!");
			} else {
				System.out.println("삭제 실패!");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("deleteRecord() ERR : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL ERR : " + e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("Connection 자원해제 ERR : " + e.getMessage());
			}
		}

	}// end main

}// end class
