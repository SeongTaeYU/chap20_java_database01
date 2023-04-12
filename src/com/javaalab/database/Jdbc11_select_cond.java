package com.javaalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// book 테이블에서 "역도" 관련 책들 중에서 10,000원이 넘는 책을 조회
// like 연산자 사용례 : LIKE CONCAT('%' || '역도', '%')
public class Jdbc11_select_cond {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "square";
		String dbPwd = "1234";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql;
		
		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");
			
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("커넥션 객체 생성 성공!");
			
			String book_name = "역도";
			int price = 10000;
			sql = "select b.* from book b";
			sql += " where b.book_name like concat('%' || ?, '%')";
			sql += " and price > ?";
					
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,book_name);
			pstmt.setInt(2,price);
			rs = pstmt.executeQuery();
			
			System.out.println("조회하신 책제목은?");
			while(rs.next()) {
				System.out.println(rs.getInt("book_id")+"\t"+
									rs.getString("book_name")+"\t"+
									rs.getString("publisher")+"\t"+
									rs.getInt("price")
						);
			}
			
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 ERR : "+e.getMessage());
		}catch(SQLException e) {
			System.out.println("SQL ERR : "+e.getMessage());
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(SQLException e) {
				System.out.println("Connection 자원해체 ERR : "+e.getMessage());
			}
		}
	}

}
