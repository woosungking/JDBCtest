package com.java.dao.drivermanager.transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.java.UserDto;
import com.java.dao.UserDao;
import com.java.manager.DBConnectionManager;

public class UserDaoAutoCommitF implements UserDao {

	@Override
	public List<UserDto> findAllUsers() {
		String sql = "SELECT * FROM users";
		List<UserDto> result = new ArrayList<>();

		try (Connection conn = DBConnectionManager.getConnection();
			 Statement stat = conn.createStatement();
			 ResultSet rs = stat.executeQuery(sql)) {

			while (rs.next()) {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				Timestamp timestamp = rs.getTimestamp("created_at");
				result.add(new UserDto(id, name, timestamp));
			}

		} catch (SQLException e) {
			throw new RuntimeException("유저 조회 중 DB 오류 발생", e);
		}

		return result;
	}

	@Override
	public void createUser(String name) {
		String sql = "INSERT INTO users(name) VALUES('" + name + "')";
		try (Connection conn = DBConnectionManager.getConnection();
			 Statement stat = conn.createStatement()) {

			stat.executeUpdate(sql);

		} catch (SQLException e) {
			throw new RuntimeException("유저 등록 중 오류 발생", e);
		}
	}

	@Override
	public void updateUserName(String name, String changedName) throws SQLException {
		String sql = "UPDATE users SET name = '" + changedName + "' WHERE name = '" + name + "'";

		try (Connection conn = DBConnectionManager.getConnection();
			 Statement stat = conn.createStatement()) {

			conn.setAutoCommit(false);
			stat.executeUpdate(sql);
			conn.commit();

		} catch (SQLException e) {
			throw new RuntimeException("업데이트 중 오류", e);
		}
	}

	@Override
	public void deleteUserByName(String name) {
		String sql = "DELETE FROM users WHERE name = '" + name + "'";

		try (Connection conn = DBConnectionManager.getConnection();
			 Statement stat = conn.createStatement()) {

			stat.executeUpdate(sql);

		} catch (SQLException e) {
			throw new RuntimeException("삭제 중 에러 발생", e);
		}
	}
}
