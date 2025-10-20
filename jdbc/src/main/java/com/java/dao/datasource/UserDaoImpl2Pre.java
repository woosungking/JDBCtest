package com.java.dao.datasource;

import java.sql.SQLException;
import java.util.List;

import com.java.UserDto;
import com.java.dao.UserDao;

public class UserDaoImpl2Pre implements UserDao {
	@Override
	public void deleteUserByName(String name) throws SQLException {

	}

	@Override
	public void updateUserName(String name, String changedName) throws SQLException {

	}

	@Override
	public void createUser(String name) throws SQLException {

	}

	@Override
	public List<UserDto> findAllUsers() {
		return List.of();
	}
}
