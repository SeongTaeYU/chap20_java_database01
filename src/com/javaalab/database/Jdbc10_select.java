package com.javaalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// book 테이블에서 "굿스포크" 출판사의 모든 책을 조회하세요.
public class Jdbc10_select {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "square";
		String dbPwd = "1234";
		
		//데이터베이스에 연결하는 다리(bridge)와 같다.
		Connection con = null;
		//쿼리문에 인자를 전달해서 SQL 구문을 실행해주는 객체
		PreparedStatement pstmt = null;
		//실행된 결과를 받아오는 객체
		ResultSet rs = null;
		
		String sql;
		
		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");
			
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("커넥션 객체 생성 성공!");
			String publisher = "굿스포크";
//			sql = "select * from book where publisher = ?";
			
			//★ ★ ★ ★ 2번째 3번째 줄부터는 앞에 space한 칸 띄우기!!★ ★ ★ ★ 
			sql = "select b.book_id, b.book_name, b.price,b.publisher";
			sql += " from book b";				
			sql += " where b.publisher = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,publisher);
			rs = pstmt.executeQuery();
			
			System.out.println("조회하신 출판사는?");
			while(rs.next()) {
				System.out.println(rs.getInt("book_id")+"\t"+
						rs.getString("book_name")+"\t"+
						rs.getString("publisher")+"\t"+
						rs.getInt("price"));
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
				System.out.println("Connection 자원해제 ERR : "+e.getMessage());
			}
		}
	}//end main

}//end Class
