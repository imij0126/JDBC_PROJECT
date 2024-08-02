package com.kh.view;


import com.kh.model.vo.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;

// View : 사용자가 보게될 시각적인 요소 (화면) 출력 및 입력
public class MemberMenu {
	// Scanner 객체 생성
	private Scanner sc = new Scanner(System.in);
	// MemberController 객체 생성
	private MemberController mc = new MemberController();
	/*
	 * 사용자가 보게 될 첫 화면 : 메인메뉴(화면)
	 */
	public void mainMenu() {
		while(true) {
			System.out.println("============ 회원 관리 프로그램");
			System.out.println("1. 회원 추가");			// C(Create)
			System.out.println("2. 전체 회원 조회");		// R(read)
			System.out.println("3. 회원 아이디로 검색");	// R(read)
			System.out.println("4. 회원 정보 수정");		// U(Update)
			System.out.println("5. 회원 탈퇴");			// D(Delete)
			System.out.println("6. 회원 정보 찾기");
			System.out.println("9. 프로그램 종료");
			
			System.out.print(">> 메뉴 번호 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1:
				addMenu();
				break;
			case 2:
				mc.selectList();
				break;
			case 3:
				searchById();
				break;
			case 4:
				updatemenu();
				break;
			case 5:
				deleteById();
				break;
			case 6:
				searchByName();
				break;
			case 9:
				System.out.println("프로그램을 종료합니다.");
				return;
			}
		}
	}
	/*
	 *  회원 추가를 위한 메뉴(화면) : 회원 정보 입력받아서 추가 요청
	 */
	public void addMenu() {
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();
		
		System.out.print("이름 : ");
		String name =sc.nextLine();
		
		System.out.print("성별 (M/F) : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		// 회원 추가 요청 --> Controller 에게 요청
		mc.insertMember(userId,userPw,name,gender);
	}
	public void searchById() {
		System.out.print("아이디를 검색하세요 : ");
		String id = sc.nextLine();
		mc.searchById(id);
	}
	public void updatemenu() {
		System.out.println("---- 회원 정보 ----");
		System.out.print("회원 아이디 : ");
		String id = sc.nextLine();
		System.out.print("변경할 비밀번호 : ");
		String pwd = sc.nextLine();
		System.out.print("변경할 이름 : ");
		String name = sc.nextLine();
		System.out.print("변경할 주소 : ");
		String addr = sc.nextLine();
		System.out.print("변경할 연락처 : ");
		String phone = sc.nextLine();
		System.out.print("변경할 취미 : ");
		String hobby = sc.nextLine();
		
		mc.updateById(id,pwd,name,addr,phone,hobby);
	}
	public void deleteById() {
		System.out.print("아이디를 검색하세요 : ");
		String id = sc.nextLine();
		mc.deleteById(id);
	}
	public void searchByName() {
		System.out.println("--- 회원 이름으로 키워드 검색 ---");
		System.out.print("검색할 회원명 입력 : ");
		String name = sc.nextLine();
		mc.searchByName(name);
	}
	/*
	 * 요청 처리 후 성공했을 경우 사용자에게 표시할 화면
	 * @param message 성공 메세지
	 */
	public void displaySuccess(String message) {
		System.out.println("서비스 요청 성공 : " +message);
	}
	/*
	 * 요청 처리 후 실패했을 경우 사용자에게 표시할 화면
	 * @param emssage 실패메시지
	 */
	public void displayFailed(String message) {
		System.out.println("서비스 요청 실패 : " + message);
	}
	public void displayNoData(String message) {
		System.out.println(message);
	}
	public void displayMemberList(ArrayList<Member> list) {
		System.out.println("--- 조회 결과 ---");
		//for문
		//
		for(Member m : list) {
			System.out.println(m);
		}
	}
	/*
	 * 조회 결과 Member 객체를 화면에 표시
	 * @param m
	 */
	public void displayMember(Member m) {
		System.out.println("---- 조회 결과 ----");
		System.out.println(m);
	}
	
}
