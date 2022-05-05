package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.InputMismatchException;
import java.util.Scanner;

import db.DBConnectionMgr;
import dto.Apple;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FruitDao {
	private final DBConnectionMgr pool;
	private final Scanner sc;
	
	private String sql;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// insert
	public void addFruit() {
//		String sql = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			sql = "INSERT INTO\r\n"
					+ "	fruit\r\n"
					+ "VALUES(\r\n"
					+ "	?,\r\n"
					+ "	?,\r\n"
					+ "	?,\r\n"
					+ "	?\r\n"
					+ ")";
			pstmt = con.prepareStatement(sql);
			try {
				System.out.println("과일 코드 입력: ");
				pstmt.setInt(1, sc.nextInt());
				sc.nextLine();
				
				System.out.print("과일 이름 입력: ");
				String name = sc.nextLine();
				pstmt.setString(2, name);
				
				System.out.print(name + " 가격 입력: ");
				pstmt.setInt(3, sc.nextInt());
				sc.nextLine();
				
				System.out.print(name + " 갯수 입력: ");
				pstmt.setInt(4, sc.nextInt());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("과일 등록 실패!");
				System.out.println("과일: 문자열\n가격: 정수\n갯수: 정수\n자료형을 지켜주세요");
				pool.freeConnection(con, pstmt, rs);
				sc.nextLine();
				return;
			}
			
			rs = pstmt.executeQuery();
			System.out.println("해당 과일을 등록했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
	
	// select
	public void getFruit() {
		Apple apple = null;
		try {
			con = pool.getConnection();
			sql = "select * from fruit";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt(1) == Apple.getCode()) {
					apple = Apple.builder()
							.fruitName(rs.getString(2))
							.price(rs.getInt(3))
							.amount(rs.getInt(4))
							.build();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		System.out.println(apple);
	}
	
	// update
	public void updateFruit() {
		
	}
	
	// delete
	public void deleteFruit() {
//		String sql = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			con = pool.getConnection();
			sql = "DELETE\r\n"
					+ "FROM\r\n"
					+ "	fruit\r\n"
					+ "WHERE\r\n"
					+ "	CODE = (select\r\n"
					+ "					code\r\n"
					+ "				from\r\n"
					+ "					fruit\r\n"
					+ "				where\r\n"
					+ "					fruit_name = ?)";
			pstmt = con.prepareStatement(sql);
			System.out.print("삭제할 과일 이름 입력: ");
			String name = sc.nextLine();
			pstmt.setString(1, name);
			
			int result = pstmt.executeUpdate();
			if(result != 0) {
				System.out.println("해당 과일을 삭제했습니다.");
			}else {
				System.out.println("해당 과일은 존재하지않습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
}