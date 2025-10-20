package com.java.dao.drivermanager.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.java.UserDto;
import com.java.dao.UserDao;
import com.java.manager.DBConnectionManager;

public class UserDaoAutoCommitT implements UserDao {
	public List<UserDto> findAllUsers()  {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionManager.getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery("SELECT * FROM users");
			List<UserDto> temp = new ArrayList<>();
			while(rs.next()){
				String name =rs.getString("name");
				Long id = rs.getLong("id");
				Timestamp timestamp = rs.getTimestamp("created_at");
				temp.add(new UserDto(id, name,timestamp));
			}
			return temp;
		}catch (SQLException e){
			throw new RuntimeException("유저 조회 중 DB 오류 발생", e);
		}
	}

	public void createUser(String name){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try{
			conn = DBConnectionManager.getConnection();
			stat = conn.createStatement();
			stat.executeUpdate("INSERT INTO users(name) VALUES('" + name + "')");

		}catch (SQLException e){
			throw new RuntimeException("유저 등록 중 오류 발생",e);
		}

	}
	/*
	고의로 예외를 발생, 예외 발생 후에도 Rollback이 안됨을 볼 수 있음.
	 */
	public void updateUserName(String name, String changedName){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try{
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(true); // 기본 값은 True, 실행과 동시에 자동으로 커밋을 찍어버리기 때문
			stat = conn.createStatement();
			stat.executeUpdate("UPDATE users SET users.name ='"+changedName+"' WHERE users.name = '"+name+"'");
			throw new SQLException("고의로 예외 발생");
		}catch (SQLException e){
			throw new RuntimeException("업데이트 중 오류",e);
		}
	}

	public void deleteUserByName(String name){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try{
			conn = DBConnectionManager.getConnection();
			stat = conn.createStatement();
			stat.executeUpdate("DELETE FROM users WHERE users.name = '"+name+"'");
		}catch (SQLException e){
			throw new RuntimeException("삭제 중 에러 발생", e);
		}
	}
}
