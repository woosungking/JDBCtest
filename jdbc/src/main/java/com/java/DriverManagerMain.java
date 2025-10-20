// package com.java;
//
// import java.sql.SQLException;
// import java.util.List;
// import java.util.Scanner;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
//
// import com.java.dao.UserDao;
// import com.java.dao.drivermanager.UserDaoImpl;
// import com.java.dao.drivermanager.transaction.UserDaoAutoCommitF;
//
// public class DriverManagerMain {
// 	// public static void main(String[] args) throws SQLException {
// 	// 	// UserDao userDao = new UserDaoImpl();
// 	// 	// UserDao userDao = new UserDaoAutoCommitT();
// 	// 	UserDao userDao = new UserDaoAutoCommitF();
// 	//
// 	// 	Scanner scanner = new Scanner(System.in);
// 	// 	boolean go = true;
// 	// 	int option;
// 	//
// 	// 	String name;
// 	// 	String changedName;
// 	// 	try{
// 	// 		while(go){
// 	// 			System.out.println("1.selectAll 2.delete 3.update 4.create 5.exit");
// 	// 			option = scanner.nextInt();
// 	// 			scanner.nextLine();
// 	// 			switch(option){
// 	// 				case 1:
// 	// 					List<UserDto> response = userDao.findAllUsers();
// 	// 					response.forEach(user -> System.out.println(user.toString()));
// 	// 					break;
// 	// 				case 2: // Delete
// 	// 					System.out.println("지울 이름 입력:");
// 	// 					name = scanner.nextLine();
// 	// 					userDao.deleteUserByName(name);
// 	// 					break;
// 	// 				case 3: // Update
// 	// 					System.out.println("대상과 바꿀 이름 입력:");
// 	// 					name = scanner.nextLine();
// 	// 					changedName = scanner.nextLine();
// 	// 					userDao.updateUserName(name, changedName);
// 	// 					break;
// 	// 				case 4: // Create
// 	// 					System.out.println("이름 입력");
// 	// 					name = scanner.nextLine();
// 	// 					userDao.createUser(name);
// 	// 					break;
// 	// 				case 5: // Exit
// 	// 					go = false;
// 	// 					break;
// 	// 				default:
// 	// 					go = false;
// 	// 					break;
// 	// 			}
// 	// 		}
// 	//
// 	// 	}catch (Exception e){
// 	// 		throw new RuntimeException("main 오류",e);
// 	// 	}
// 	// }
// 	//
// 	public static void main(String[] args) {
// 		UserDao userDao = new UserDaoImpl();
// 		try {
// 			for (int i = 1; i < 100; i++) {
// 				System.out.println(i);
// 				userDao.createUser(String.valueOf(i));
//
// 				// 5회마다 1초 쉬기
// 				if (i % 10 == 0) {
// 					Thread.sleep(10000);
// 				}
// 			}
// 		} catch (Exception e) {
// 			throw new RuntimeException(e);
// 		}
// 	}
// }
//
// 	//
// 	// public static void main(String[] args) {
// 	// 	UserDao userDao = new UserDaoImpl();
// 	//
// 	//
// 	// 	// 스레드 풀 생성 (동시에 10개 정도 실행)
// 	// 	ExecutorService executor = Executors.newFixedThreadPool(100);
// 	//
// 	// 	try {
// 	// 		for (int i = 40000000; i < 40010000; i++) {
// 	// 			final int userId = i;
// 	// 			executor.submit(() -> {
// 	// 				try {
// 	// 					userDao.createUser(String.valueOf("sc"+userId));
// 	// 					System.out.println("Created user " + userId);
// 	// 				} catch (SQLException e) {
// 	// 					e.printStackTrace();
// 	// 				}
// 	// 			});
// 	// 		}
// 	// 	} finally {
// 	// 		// 모든 작업 끝나면 스레드 풀 종료
// 	// 		executor.shutdown();
// 	// 	}
// 	// }
// //}