package com.java;

import static java.lang.Thread.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.java.dao.UserDao;
import com.java.dao.datasource.UserDaoImpl2;
import com.java.dao.drivermanager.transaction.UserDaoAutoCommitF;

public class DataSourceMain {
	// public static void main(String[] args) {
	// 	UserDao userDao = new UserDaoImpl2();
	//
	// 	Scanner scanner = new Scanner(System.in);
	// 	boolean go = true;
	// 	int option;
	//
	// 	String name;
	// 	String changedName;
	// 	try{
	// 		while(go){
	// 			System.out.println("1.selectAll 2.delete 3.update 4.create 5.exit");
	// 			option = scanner.nextInt();
	// 			scanner.nextLine();
	// 			switch(option){
	// 				case 1:
	// 					List<UserDto> response = userDao.findAllUsers();
	// 					response.forEach(user -> System.out.println(user.toString()));
	// 					break;
	// 				case 2: // Delete
	// 					System.out.println("지울 이름 입력:");
	// 					name = scanner.nextLine();
	// 					userDao.deleteUserByName(name);
	// 					break;
	// 				case 3: // Update
	// 					System.out.println("대상과 바꿀 이름 입력:");
	// 					name = scanner.nextLine();
	// 					changedName = scanner.nextLine();
	// 					userDao.updateUserName(name, changedName);
	// 					break;
	// 				case 4: // Create
	// 					System.out.println("이름 입력");
	// 					name = scanner.nextLine();
	// 					userDao.createUser(name);
	// 					break;
	// 				case 5: // Exit
	// 					go = false;
	// 					break;
	// 				default:
	// 					go = false;
	// 					break;
	// 			}
	// 		}

	// 	}catch (Exception e){
	// 		throw new RuntimeException("main 오류",e);
	// 	}
	// }

	// public static void main(String[] args) {
	// 	UserDao userDao = new UserDaoImpl2();
	// 	try {
	// 		for (int i = 40; i < 80; i++) {
	// 			System.out.println(i);
	// 			userDao.createUser(String.valueOf(i));
	// 			// Thread.sleep(1000);
	// 			System.out.println("dddd");
	// 			if(i == 30){
	// 				throw new RuntimeException("예외 발생");
	// 			}
	// 		}
	//
	// 	}catch (SQLException e){
	// 		throw new RuntimeException(e);
	// 	}/* catch (InterruptedException e) {
	// 		throw new RuntimeException(e);
	// 	}*/
	// }


	public static void main(String[] args) {
		UserDao userDao = new UserDaoImpl2();

		ExecutorService executor = Executors.newFixedThreadPool(5);

		try {
			for (int i = 3000; i < 4001; i++) {
				final int userId = i;
				executor.submit(() -> {
					try {
						userDao.createUser(String.valueOf("sc"+userId));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
			}
		} finally {
			executor.shutdown();
		}
	}


	// public static void main(String[] args) {
	//
	// 	UserDao userDao = new UserDaoImpl2();
	//
	// 	// 최대 10개 쓰레드 동시 실행
	// 	ExecutorService executor = Executors.newFixedThreadPool(50);
	//
	// 	// CompletableFuture 리스트에 저장해서 모두 완료 확인
	// 	List<CompletableFuture<Void>> futures = new ArrayList<>();
	//
	// 	for (int i = 1; i <= 1000; i++) {
	// 		final String name = "user" + i;
	//
	// 		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
	// 			try {
	// 				// 실제 INSERT
	// 				userDao.createUser(name);
	// 				System.out.println(name + " 생성 완료");
	// 			} catch (RuntimeException e) {
	// 				System.err.println(name + " 생성 실패: " + e.getMessage());
	// 			} catch (SQLException e) {
	// 				throw new RuntimeException(e);
	// 			}
	// 		}, executor);
	//
	// 		futures.add(future);
	//
	// 		// 선택: 약간 딜레이 주어 락 경쟁 완화 (예: 10~50ms)
	// 		try { Thread.sleep(10); } catch (InterruptedException ignored) {}
	// 	}
	//
	// 	// 모든 CompletableFuture 완료 대기
	// 	CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
	// 	allDone.join();
	//
	// 	executor.shutdown();
	// 	try {
	// 		if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
	// 			System.out.println("모든 INSERT 완료되지 않음, 강제 종료");
	// 		} else {
	// 			System.out.println("모든 사용자 생성 완료");
	// 		}
	// 	} catch (InterruptedException e) {
	// 		e.printStackTrace();
	// 	}
	// }
}
