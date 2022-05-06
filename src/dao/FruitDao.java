package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.InputMismatchException;
import java.util.Scanner;

import db.DBConnectionMgr;
import dto.Apple;
import dto.Strawberry;
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
		Strawberry strawberry = null;
		Apple apple = null;
		try {
			con = pool.getConnection();
			sql = "select * from fruit";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt(1) == Strawberry.getCode()) {
					strawberry = Strawberry.builder()
							.fruitName(rs.getString(2))
							.price(rs.getInt(3))
							.amount(rs.getInt(4))
							.build();
				}else if(rs.getInt(1) == Apple.getCode()) {
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
		
		System.out.println(strawberry);
		System.out.println(apple);
	}
	
	// update
	public void updateFruit() {
		try {
			con = pool.getConnection();
//			sql = "update fruit set ? = ? where code = ?";
//			pstmt = con.prepareStatement(sql);
			
			System.out.print("어떤 정보를 변경하시겠습니까?(fruit_name, price, amount): ");
			String modifyInfo = sc.nextLine();
//			pstmt.setString(1, modifyInfo);
			
			try {
				if(modifyInfo.equals("fruit_name")) {
					sql = "update fruit set fruit_name = ? where code = ?";
					pstmt = con.prepareStatement(sql);
					System.out.print("새로운 과일 이름을 입력하세요(문자열): ");
					pstmt.setString(1, sc.nextLine());
					
					
					System.out.print("과일 코드 입력(정수): ");
					pstmt.setInt(2, sc.nextInt());
					sc.nextLine();
				}else if(modifyInfo.equals("price")){
					sql = "update fruit set price = ? where code = ?";
					pstmt = con.prepareStatement(sql);
					System.out.print("새로운 가격을 입력하세요(정수): ");
					pstmt.setInt(1, sc.nextInt());
					sc.nextLine();
					
					System.out.print("과일 코드 입력(정수): ");
					pstmt.setInt(2, sc.nextInt());
					sc.nextLine();
				}else if(modifyInfo.equals("amount")) {
					sql = "update fruit set amount = ? where code = ?";
					pstmt = con.prepareStatement(sql);
					System.out.print("총 갯수를 입력하세요(정수): ");
					pstmt.setInt(1, sc.nextInt());
					sc.nextLine();
					
					System.out.print("과일 코드 입력(정수): ");
					pstmt.setInt(2, sc.nextInt());
					sc.nextLine();
				}
			} catch (InputMismatchException e) {
				System.out.println("형식을 지켜주세요.");
			}
			
			int result = pstmt.executeUpdate();
			
			if(result != 0) {
				System.out.println("정보가 변경되었습니다.");
				con.commit();
			}else {
				System.out.println("정보를 다시 입력해주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
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