package controller;

import java.util.Scanner;

import dao.FruitDao;
import db.DBConnectionMgr;

public class DBMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		FruitDao fruitDao = new FruitDao(DBConnectionMgr.getInstance(), sc);
		
		while(true) {
			System.out.println("[옵션 선택]");
			System.out.println("1. 과일 등록\n2. 현재 과일 동기화 및 정보 보기\n3. 과일 수정\n4. 과일 삭제\n5. 프로그램 종료");
			int choice = sc.nextInt();
			sc.nextLine();
			if(choice == 1) {
				fruitDao.addFruit();
			}else if(choice == 2) {
				fruitDao.getFruit();
			}else if(choice == 3) { 
				fruitDao.updateFruit();
			}else if(choice == 4) {
				fruitDao.deleteFruit();
			}else if(choice == 5) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
}