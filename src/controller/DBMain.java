package controller;

import java.util.ArrayList;
import java.util.Scanner;

import dao.FruitDao;
import db.DBConnectionMgr;
import dto.Fruit;

public class DBMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		FruitDao fruitDao = new FruitDao(DBConnectionMgr.getInstance(), sc, new ArrayList<Fruit>());
		
		while(true) {
			System.out.println("[옵션 선택]");
			System.out.println("1. 과일 등록\n2. 과일 정보보기\n3. 과일 수정\n4. 과일 삭제\n5. 과일 판매\n6. 수익보기\n7. 프로그램 종료");
			int choice = sc.nextInt();
			sc.nextLine();
			if(choice == 1) {
				fruitDao.addFruit();
			}else if(choice == 2) {
				System.out.println("1. 전체 정보 보기\n2. 이름으로 검색");
				choice = sc.nextInt();
				sc.nextLine();
				if(choice == 1) {
					fruitDao.getFruit();
				}else {
					fruitDao.getFruitByFruitName();
				}
			}else if(choice == 3) { 
				fruitDao.updateFruit();
			}else if(choice == 4) {
				fruitDao.deleteFruit();
			}else if(choice == 5) {
				fruitDao.saleFruit();
			}else if(choice == 6) {
				fruitDao.showIncome();
			}else if(choice == 7) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
}