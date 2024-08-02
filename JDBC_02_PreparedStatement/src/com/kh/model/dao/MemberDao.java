package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// DAO (Data Access Object) : DB에 직접 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과 반환(=> JDBC 사용)
public class MemberDao {
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER_NAME = "C##JDBC";
	private final String PASSWORD = "JDBC";
	/*
	 * * JDBC용 객체
	 *   - Connection : DB 연결정보를 담고있는 객체
	 *   - Statement  : 연결된 DB에 sql문을 전달해서 실행하고 결과를 받아주는 객체
	 *   - ResultSet  : SELECT문(DQL) 실행 후 조회된 결과물을 담고있는 객체
	 *   
	 *  * JDBC 과정 (순서*)
	 *  [1] JDBC DRIVER 등록 : 사용할 DBMS(오라클)에서 제공하는 클래스 등록
	 *  [2] Connection 객체 생성 : DB정보 (url,사용자명, 비밀번호)를 통해 해당 DB와 연결하면서 생성
	 *  [3] Statement 객체 생성 : Connection 객체를 이용해서 생성. sql문을 실행하고 결과를 받아줄것임
	 *  [4] sql문 전달해서 실행 후 결과 받기
	 *  	- SELECT 문 실행 시 ResultSet 객체로 조회 결과를 받음
	 *  	- DML(INSERT/UPDATE/DELETE) 실행 시 int 타입으로 처리 결과를 받음 (처리된 행 수)
	 *  [5] 결과에 대한 처리
	 *  	- ResultSet 객체에서 데이터를 하나씩 추출하여 vo객체로 옮겨 담기(저장)
	 *  	- DML의 경우 트랜젝션 처리 (성공했을 때는 commit, 실패했을 때는 rollback)
	 *  [6] 자원 반납 (close) => 생성 역순으로!! 
	 */
	
	/*
	 * 사용자가 입력한 정보들을 DB에 추가하는 메소드 (=> 회원 정보 추가)
	 * 
	 * @parameter m 사용자가 입력한 값들이 담겨있는 Member 객체
	 * @return insert문 실행 후 처리된 행 수
	 */
	public int insertMember(Member m) {
		// insert문 --> int (처리된 행 수) --> 트랜젝션 처리
		int result = 0;
		/*
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, "
				+ "'" + m.getUserId() + "', " // 'user01',
				+ "'" + m.getUserPw() + "', " // 'pass01',
				+ "'" + m.getUserName() + "', " // '아이유',
				+ "'" + m.getGender() + "', " // F
					  + m.getAge() + ","
				+ "'" + m.getEmail() + "', "
				+ "'" + m.getAddress() + "', "
				+ "'" + m.getPhone() + "', "
				+ "'" + m.getHobby() + "', SYSDATE)";
				*/
		String sql = "INSERT INTO MEMBER VALUES (SEQ_USERNO.NEXTVAL, " + "?,?,?,?,?,?,?,?,?,SYSDATE)";
		System.out.println("------------------");
		System.out.println(sql);
		System.out.println("------------------");
		
		// JDBC용 객체 선언
		Connection conn =null;
		//Statement stmt = null;
		PreparedStatement pstmt = null;
		
		try {
		// 1) JDBC DRIVER 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2) Connection  객체 생성 => DB 연결
		conn = DriverManager.getConnection(URL, USER_NAME,PASSWORD);
		conn.setAutoCommit(false);
		// 3) Statement 객체 생성
		// stmt = conn.createStatement();
		// 3') PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql); // 객체 생성 시 sql문 전달(미완성/완성 형태 상관없이 전달 가능)
		// setXXX (XXX : 데이터 타입) --> setXXX(물음표순서, 전달할값(변수))
		pstmt.setString(1, m.getUserId());
		pstmt.setString(2, m.getUserPw());
		pstmt.setString(3, m.getUserName());
		pstmt.setString(4, Character.valueOf(m.getGender()).toString());
		pstmt.setInt(5, m.getAge());
		pstmt.setString(6, m.getEmail());
		pstmt.setString(7, m.getAddress());
		pstmt.setString(8, m.getPhone());
		pstmt.setString(9, m.getHobby());
		// 4) 실행 후 결과받기
		//result = stmt.executeUpdate(sql);
		// 5) 트랜젝션
		result = pstmt.executeUpdate();
		
		
		if(result > 0) {
			conn.commit();
		}else {
			conn.rollback();
		}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				//stmt.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<Member> selectList() {
		// SELECT문 (여러행 조회) --> ResultSet 객체 --> ArrayList<Member> 에 담기
		ArrayList<Member> list = new ArrayList<>(); // 리스트가 비어있음
		// JDBC용 객체 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		//실행할 sql문
		String sql = "SELECT * FROM MEMBER";
		
		try {
		// 1) JDBC 드라이버 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2) connection 객체 생성
		conn = DriverManager.getConnection(URL, USER_NAME,PASSWORD);
		// 3) Statement 객체 생성
		stmt = conn.createStatement();
		// 4) 쿼리 실행 후 결과 받기
		rset = stmt.executeQuery(sql);
		// 5) ResultSet에 담겨진 데이터를 추출
		while(rset.next()) {	// next() : 데이터가 있을 경우 true
			Member m = new Member(
						rset.getInt("USERNO"),
						rset.getString("USERID"),
						rset.getString("USERPW"),
						rset.getString("USERNAME"),
						rset.getString("GENDER") ==  null? ' ' : rset.getString("GENDER").charAt(0),
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
					);
			// ResultSet 객체에서 각 컬럼의 데이터 뽑아내 Member 객체 생성(저장)
			list.add(m);
			// 반복문이 끝난 시점
			// --> 조회된 데이터가 없으면 리스트는 비어 잇고
		    
		}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
			rset.close();
			stmt.close();
			conn.close();
			}catch(SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	public Member selectByUserId(String userId) {
		// SELECT 실행 --> ResultSet (한 행의 데이터 x) --> Member 객체에 저장
		Member m = null;
		// JDBC 객체
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		// 실행할 SQL문
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'";
		System.out.println("----------------------");
		System.out.println(sql);
		System.out.println("----------------------");
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
			// 3) Statement 객채 생성
			stmt =conn.createStatement();
			// 4) sql문 실행 및 결과
			rset = stmt.executeQuery(sql);
			if(rset.next()) {
				m = new Member(
						rset.getInt("USERNO"),
						rset.getString("USERID"),
						rset.getString("USERPW"),
						rset.getString("USERNAME"),
						rset.getString("GENDER") ==  null? ' ' : rset.getString("GENDER").charAt(0),
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
					);

			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
			rset.close();
			stmt.close();
			conn.close();
			}catch(SQLException e) {e.printStackTrace();}
		}
		return m;
	}
	public int deleteByUserId(String userId) {
		int result = 0;
		// JDBC 객체 선언 및 null 로 초기화
		Connection conn = null;
		Statement stmt = null;
		// 실행 시킬 쿼리문 작성
		String sql = "DELETE FROM MEMBER WHERE USERID = '" + userId + "'";
		// 1) jdbc driver 등록
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
			conn.setAutoCommit(false);
			// 3) Statement 객채 생성 => connection 객체로 생성
			stmt =conn.createStatement();
			// 4) sql문 실행 및 결과
			result = stmt.executeUpdate(sql);
			if(result > 0) {
				// 실행시킨 쿼리문이 성공했다면 -> DB에 적용 => COMMIT
				conn.commit();
			}else {
				// 쿼리문이 실패했다면 -> 되돌려야함 -> rollback
				conn.rollback();
			}

		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			//역순 자원 반납
			try {
				stmt.close();
				conn.close();
			}catch(SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	public int updateById(Member m) {
		int result = 0;
		/*
		 *  update member
		 *  	set userpw = 'xx',
		 *  		username = 'xx',
		 *  		address = 'xx',
		 *  		hobby = 'xx'
		 *  	where userid = 'xx';
		 */
		String sql = "UPDATE MEMBER SET USERPW = ?," 
				+ "USERNAME = ?," 
				+ "ADDRESS = ?," 
				+ "PHONE = ?," 
				+ "HOBBY = ?" 
				+ "WHERE USERID = ?";
		
		System.out.println(sql);
				
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME,PASSWORD);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserPw());
			pstmt.setString(2, m.getUserName());
			pstmt.setString(3, m.getAddress());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getHobby());
			pstmt.setString(6, m.getUserId());
			result = pstmt.executeUpdate();
			
			System.out.println("결과 >>> " + result);
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			}catch(SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	public ArrayList<Member> selectByUserName(String keyword){
		ArrayList<Member> list = new ArrayList<>(); // [] -> 비어있는 상태
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 실행할 쿼리문
		String sql = "SELECT * FROM MEMBER "
					+ " WHERE USERNAME LIKE ?";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%"); // "%리%" --> '%리%'
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Member m = new Member(
							rset.getInt("USERNO"),
							rset.getString("USERID"),
							rset.getString("USERPW"),
							rset.getString("USERNAME"),
							rset.getString("GENDER") ==  null? ' ' : rset.getString("GENDER").charAt(0),
							rset.getInt("AGE"),
							rset.getString("EMAIL"),
							rset.getString("ADDRESS"),
							rset.getString("PHONE"),
							rset.getString("HOBBY"),
							rset.getDate("ENROLLDATE")
					);
				list.add(m);
			}
 		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e) {e.printStackTrace();}
		}
		return list;
	}
}
