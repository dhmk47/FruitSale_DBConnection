package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import db.DBConnectionMgr;
import dto.Apple;
import dto.Fruit;
import dto.Strawberry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FruitDao {
	private final DBConnectionMgr pool;
	private final Scanner sc;
	@NonNull
	private ArrayList<Fruit> fruitList;
	
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
		int code = 0;
		String name = null;
		int price = 0;
		int amount = 0;
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
				code = sc.nextInt();
				sc.nextLine();
				pstmt.setInt(1, code);
				
				System.out.print("과일 이름 입력: ");
				name = sc.nextLine();
				pstmt.setString(2, name);
				
				System.out.print(name + " 가격 입력: ");
				price = sc.nextInt();
				sc.nextLine();
				pstmt.setInt(3, price);
				
				System.out.print(name + " 갯수 입력: ");
				amount = sc.nextInt();
				sc.nextLine();
				pstmt.setInt(4, amount);
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
//		Strawberry strawberry = null;
//		Apple apple = null;
		fruitList.clear();
		try {
			con = pool.getConnection();
			sql = "select * from fruit";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Fruit fruit = Fruit.builder()
						.code(rs.getInt(1))
						.fruitName(rs.getString(2))
						.price(rs.getInt(3))
						.amount(rs.getInt(4))
						.build();
				fruitList.add(fruit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		for(Fruit fruit : fruitList) {
			System.out.println(fruit);
		}
//		System.out.println(strawberry);
//		System.out.println(apple);
	}
	
	public void getFruitByFruitName() {
		try {
			con = pool.getConnection();
			sql = "SELECT\r\n"
					+ "	*\r\n"
					+ "FROM\r\n"
					+ "	fruit\r\n"
					+ "WHERE\r\n"
					+ "	fruit_name = ?";
			pstmt = con.prepareStatement(sql);
			System.out.print("검색할 과일 이름을 입력하세요: ");
			pstmt.setString(1, sc.nextLine());
			rs = pstmt.executeQuery();
			rs.next();
			try {
				Fruit fruit = Fruit.builder()
						.code(rs.getInt(1))
						.fruitName(rs.getString(2))
						.price(rs.getInt(3))
						.amount(rs.getInt(4))
						.build();
				System.out.println(fruit);
				
			}catch (SQLDataException e) {
				System.out.println("해당 과일은 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
	
	// update
	public void updateFruit() {
		try {
			con = pool.getConnection();
			sql = "UPDATE\r\n"
					+ "	fruit\r\n"
					+ "SET\r\n"
					+ "	? = ?\r\n"
					+ "WHERE\r\n"
					+ "	CODE = ?";
			pstmt = con.prepareStatement(sql);
			
			System.out.print("어떤 정보를 변경하시겠습니까?(fruit_name, price, amount): ");
			String modifyInfo = sc.nextLine();
			pstmt.setString(1, modifyInfo);
			
			try {
				if(modifyInfo.equals("fruit_name")) {
//					sql = "update fruit set fruit_name = ? where code = ?";
//					pstmt = con.prepareStatement(sql);
					System.out.print("새로운 과일 이름을 입력하세요(문자열): ");
					pstmt.setString(2, sc.nextLine());
					
					
					System.out.print("과일 코드 입력(정수): ");
					pstmt.setInt(3, sc.nextInt());
					sc.nextLine();
				}else if(modifyInfo.equals("price")){
//					sql = "update fruit set price = ? where code = ?";
//					pstmt = con.prepareStatement(sql);
					System.out.print("새로운 가격을 입력하세요(정수): ");
					pstmt.setInt(2, sc.nextInt());
					sc.nextLine();
					
					System.out.print("과일 코드 입력(정수): ");
					pstmt.setInt(3, sc.nextInt());
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
	
	public int updateFruit(int code, int income, int oldAmount, int saleAmount) {
		int updateIncome = 0;
		try {
			sql = "update fruit set amount = ? where code = ?";
			pstmt = con.prepareStatement(sql);
			int newAmount = oldAmount - saleAmount;
			pstmt.setInt(1, newAmount);
			pstmt.setInt(2, code);
			pstmt.executeUpdate();

			
			sql = "select income from my_income";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			int myIncome = rs.getInt(1);
			
			sql = "update my_income set income = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, myIncome + income);
			updateIncome = pstmt.executeUpdate();
			if(updateIncome == 0) {
				sql = "INSERT INTO\r\n"
						+ "	my_income\r\n"
						+ "VALUES(\r\n"
						+ "	1,\r\n"
						+ "	0\r\n"
						+ ")";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				sql = "update my_income set income = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, income);
				updateIncome = pstmt.executeUpdate();
			}else {
				con.commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return updateIncome;
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
	
	public void showIncome() {
		try {
			con = pool.getConnection();
			sql = "select income from my_income";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			System.out.println("현재 수익: " + rs.getInt(1));
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
	
	public void saleFruit() {
		System.out.print("판매할 과일 이름을 입력하세요: ");
		String fruitName = sc.nextLine();
		
		try {
			con = pool.getConnection();
			sql = "select * from fruit where fruit_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fruitName);
			rs = pstmt.executeQuery();
			
			rs.next();
			try {
				rs.getInt(1);
				System.out.println("현재 " + fruitName + " 수량: " + rs.getInt(4));
				System.out.println("몇개를 판매 하시겠습니까?");
				int saleCount = sc.nextInt();
				sc.nextLine();
				if(rs.getInt(4) < saleCount) {
					System.out.println("개수초과...");
				}else {
					int price = rs.getInt(3) * saleCount;
					int result = updateFruit(rs.getInt(1), price, rs.getInt(4), saleCount);
					if(result != 0) {
						System.out.println("판매 완료");
						System.out.println(price + "원을 벌었습니다");
					}else {
						System.out.println("판매 실패");
					}
				}
			}catch (SQLDataException e) {
				System.out.println("해당 과일은 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
}