package com.java.dao.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.java.UserDto;
import com.java.dao.UserDao;
import com.java.manager.DBDataSource;

public class UserDaoImpl2 implements UserDao {

	@Override
	public void deleteUserByName(String name) {
		try (Connection conn = DBDataSource.getConnection();
			 Statement stat = conn.createStatement()) {
			conn.setAutoCommit(false);
			stat.executeUpdate("DELETE FROM kingmu.users WHERE users.name = '" + name + "'");
			conn.commit();

		} catch (SQLException e) {
			throw new RuntimeException("delete 중 에러 발생", e);
		}
	}

	@Override
	public void updateUserName(String name, String changedName) {
		try (Connection conn = DBDataSource.getConnection();
			 Statement stat = conn.createStatement()) {

			conn.setAutoCommit(false);
			stat.executeUpdate("UPDATE kingmu.users SET users.name = '" + changedName + "' WHERE users.name = '" + name + "'");
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException("update 중 에러 발생", e);
		}
	}

	@Override
	public void createUser(String name) {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = DBDataSource.getConnection();

			// Hikari Connection 고유 아이디 출력
			System.out.println(
				"Hikari Connection: " + conn + "\n" +
					"현재 스레드 이름: " + Thread.currentThread().getName()
			);

			stat = conn.createStatement();
			conn.setAutoCommit(false);
			stat.executeUpdate("INSERT INTO kingmu.users(name) VALUES('" + name + "')");
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			throw new RuntimeException("createUser 중 에러 발생", e);
		} finally {
			if (stat != null) try { stat.close(); } catch (SQLException ignored) {}
			if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
		}
	}

	// @Override
	// public void createUser(String name) {
	// 	try (Connection conn = DBDataSource.getConnection();
	// 		 Statement stat = conn.createStatement()) {
	//
	// 		// Hikari Connection 고유 아이디 출력
	// 		System.out.println("Hikari Connection: " + conn);
	//
	// 		conn.setAutoCommit(false);
	// 		stat.executeUpdate("INSERT INTO kingmu.users(name) VALUES('" + name + "')");
	// 		conn.commit();
	//
	// 	} catch (SQLException e) {
	// 		throw new RuntimeException("createUser 중 에러 발생", e);
	// 	}
	// }


	@Override
	public List<UserDto> findAllUsers() {
		List<UserDto> response = new ArrayList<>();

		try (Connection conn = DBDataSource.getConnection();
			 Statement stat = conn.createStatement();
			 ResultSet rs = stat.executeQuery("SELECT * FROM kingmu.users")) {

			while (rs.next()) {
				response.add(new UserDto(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getTimestamp("created_at")
				));
			}

		} catch (SQLException e) {
			throw new RuntimeException("select 중 오류 발생", e);
		}

		return response;
	}
}
