package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//드라이버를 적재, 아이디와 패스워드로 mysql 데이타베이스에 연결요청 -> 객체참조변수를 준다

public class DBUtil {

	// 1.드라이버명 저장.
	private static String driver = "com.mysql.jdbc.Driver";

	// 2.데이터베이스 url명 저장
	private static String url = "jdbc:mysql://localhost/skinDB";

	// 2.드라이버를 적재하고 데이터베이스를 연결하는 함수
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1.드라이버를 적재
		Class.forName(driver);
		// 2.데이터베이스 연결
		Connection con = DriverManager.getConnection(url, "root", "123456");
		return con;
	}

}
