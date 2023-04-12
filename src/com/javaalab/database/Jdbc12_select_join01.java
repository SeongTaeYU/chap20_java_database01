package com.javaalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc12_select_join01 {

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
			
			sql = "select b.book_id, b.book_name, b.publisher, b.price,";
			sql += " os.order_id, os.cust_id, os.book_id, os.saleprice, os.order_date";
			sql += " from book b, orders os";
			sql += " where b.book_id = os.book_id";
			sql += " order by os.order_id";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println("주문정보와 도서정보");
			while(rs.next()) {
				System.out.println(rs.getInt("book_id")+"\t"+
									rs.getString("book_name")+"\t"+
									rs.getString("publisher")+"\t"+
									rs.getInt("price")+"\t"+
									rs.getInt("order_id")+"\t"+
									rs.getInt("cust_id")+"\t"+
									rs.getInt("book_id")+"\t"+
									rs.getInt("saleprice")+"\t"+
									rs.getDate("order_date")
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
				System.out.println("Connection 자원해제 ERR : "+e.getMessage());
			}
		}
		
		
	}

}
