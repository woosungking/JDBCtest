package com.java.dao.drivermanager;

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
/*
1. PreparedStatement를 사용하지 않고 Statement를 사용해보기
3. DriverManager를 통해 커넥션 획득 및 close()를 일부러 누락함으로써 메모리 누수 확인하기
 */

public class UserDaoImpl implements UserDao {

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
		try{
			conn = DBConnectionManager.getConnection();
			System.out.println("Conn :::: " + conn);
			conn.setAutoCommit(false);
			stat = conn.createStatement();
			stat.executeUpdate("INSERT INTO users(name) VALUES('" + name + "')");
			conn.commit();
			// conn.close();
			// stat.close();
		}catch (SQLException e){
			throw new RuntimeException("유저 등록 중 오류 발생",e);
		}

	}

	public void updateUserName(String name, String changedName){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try{
			conn = DBConnectionManager.getConnection();
			stat = conn.createStatement();
			stat.executeUpdate("UPDATE users SET users.name ='"+changedName+"' WHERE users.name = '"+name+"'");
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
