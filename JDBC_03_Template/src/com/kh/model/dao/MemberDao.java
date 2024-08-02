package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

public class MemberDao{
	public int insertMember(Connection conn, Member m) {
		String sql = "INSERT INTO MEMBER VALUES (SEQ_USERNO.NEXTVAL, " + "?,?,?,?,?,?,?,?,?,SYSDATE)";
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,m.getUserId());
			pstmt.setString(2,m.getUserPw());
			pstmt.setString(3,m.getUserName());
			pstmt.setString(4,m.getGender());
			pstmt.setInt(5,m.getAge());
			pstmt.setString(6,m.getEmail());
			pstmt.setString(7, m.getAddress());
			pstmt.setString(8, m.getPhone());
			pstmt.setString(9, m.getHobby());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	public ArrayList<Member> selectList(Connection conn){
		ArrayList<Member> list = new ArrayList<>();
		
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql = "SELECT * FROM MEMBER";
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Member m = new Member(
						rset.getInt("USERNO"),
						rset.getString("USERID"),
						rset.getString("USERPW"),
						rset.getString("USERNAME"),
						rset.getString("GENDER"),
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
					);
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}
	public int updateById(Connection conn,Member m) {
		PreparedStatement pstmt = null;
		// UPDATE MEMBER SET USERPW = '1234', USERNAME = '홍',
		//						ADDRESS = '서울', PHONE = '010-2222-3333'
		String sql = "UPDATE MEMBER SET USERPW = ?," 
				+ "USERNAME = ?," 
				+ "ADDRESS = ?," 
				+ "PHONE = ?," 
				+ "HOBBY = ?" 
				+ "WHERE USERID = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,m.getUserPw());
			pstmt.setString(2,m.getUserName());
			pstmt.setString(3,m.getAddress());
			pstmt.setString(4,m.getPhone());
			pstmt.setString(5,m.getHobby());
			pstmt.setString(6,m.getUserId());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	public int deleteByUserId(Connection conn,String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM MEMBER WHER USERID = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
		
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
}