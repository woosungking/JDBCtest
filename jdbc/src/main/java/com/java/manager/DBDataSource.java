package com.java.manager;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBDataSource {

	// 1️⃣ 안전하게 싱글톤으로 HikariCP 초기화
	private static final HikariDataSource dataSource;
	static {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:oracle:thin:@//localhost:1521/ORCLPDB1");
		config.setUsername("kingmu");
		config.setPassword("rla1234");
		config.setIsolateInternalQueries(true);
		config.setMinimumIdle(30);      // 최소 풀 크기
		config.setMaximumPoolSize(100);   // 최대 풀 크기
		// config.setLeakDetectionThreshold(2000);
		config.setAutoCommit(false);     // Connection 기본 autoCommit false
		dataSource = new HikariDataSource(config);
	}

	public static Connection getConnection() throws SQLException {
		// 래핑: 내부 쿼리든 애플리케이션 쿼리든 conn 사용 시 찍히도록
		return dataSource.getConnection();
	}


	// HikariCP 종료
	public static void shutdown() {
		if (dataSource != null && !dataSource.isClosed()) {
			dataSource.close();
		}
	}
}
