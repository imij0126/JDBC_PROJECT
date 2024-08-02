package com.kh.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.model.dao.*;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해 처리하는 역할
//				요청받은 메소드에서 전달된 데이터를 가공처리한 후 DAO로 전달해 호출
//				DAO로부터 반환받은 결과에 따라 성공인지 실패인지를 판단 후 응답
public class MemberController {
	/*
	 * 회원 추가 요청을 처리할 메소드
	 * @param userId 회원아이디 
	 */
	public void insertMember(String userId, String userPw, String name, char gender) {
		// view로 부터 전달받은 값들을 dao에게 바로 전달하지 않고
		// 어딘가(Member)에 담아서 전달
		// * 기본생성자 생성 후 setter 메소드 하나하나 저장
		// * 매개변수 생성자를 사용해 객체 생성
		Member m = new Member(userId, userPw, name, gender);
		
		int result = new MemberDao().insertMember(m);
		
		if(result > 0) {
			// DAO의 결과 : 성공할 경우 성공화면
			new MemberMenu().displaySuccess("회원 추가 성공!");
		}else {
			// DAO의 결과 : 실패했을 경우 실패화면 표시
			new MemberMenu().displayFailed("회원 추가 실패!");
		}
		
	}
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList();
		// 조회된 결과 따라 사용자에게 결과 표시
		if(list.isEmpty()) {
			// 조회결과가 없다면 (=> 리스트가 비어있다면)
			new MemberMenu().displayNoData("전체 조회 결과가 없음!");
		}else {
			new MemberMenu().displayMemberList(list);
		}
	}
	public void searchById(String userId) {
		Member m = new MemberDao().selectByUserId(userId);
		if(m == null) {
			// 검색 결과가 없는 경우
			new MemberMenu().displayNoData(userId+"에 해당하는 결과가 없음!");
		}else {
			// 검색결과가 있는 경우
			new MemberMenu().displayMember(m);
		}
	}
	public void deleteById(String userId) {
		int result = new MemberDao().deleteByUserId(userId);
		if(result > 0) {
			// 회원 탈퇴 성공
			new MemberMenu().displaySuccess("회원 탈퇴 성공");
		}else {
			// 검색결과가 있는 경우
			new MemberMenu().displayFailed("회원 탈퇴 실패.");
		}
	}
	public void updateById(String userId
							,String userPw
							,String userName
							,String address
							,String phone
							,String hobby) {
		// 전달받은 값을 Member 객체로 생성하여 DAO에게 전달
		Member m = new Member(); // 기본생성자로 생성
		m.setUserId(userId);		// setter 메소드 사용해 전달받은 값 저장
		m.setUserPw(userPw);
		m.setUserName(userName);
		m.setAddress(address);
		m.setPhone(phone);
		m.setHobby(hobby);
		
		// dml (update)-->int(처리된 행 수)
		int result = new MemberDao().updateById(m);
		
		if(result > 0) {
			// 회원정보 수정 성공
			new MemberMenu().displaySuccess("회원 정보 수정 성공!");
		}else {
			// 회원정보 수정 실패
			new MemberMenu().displayFailed("회원 정보 수정 실패!");
		}
	}
	/*
	 * 사용자가 입력한 이름을 키워드로 하여 회원 정보 검색
	 * @param keyword 이름에 대한 키워드 값
	 */
	public void searchByName(String keyword) {
		ArrayList<Member> list = new MemberDao().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("해당 이름에 대한 회원정보가 없습니다.");
		}else {
			new MemberMenu().displayMemberList(list);
		}
	}
}
