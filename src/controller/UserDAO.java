package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.SkinVO;
import model.UserVO;

public class UserDAO {

	// 1.유저의 정보를 데이터베이스에 입력
	public int getUserRegiste(UserVO svo) {
		// ② 데이터 처리를 위한 SQL 문
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into usertbl " + "(userId, userPassword, userName, userEmail, userGender, userPhone)" + ""
				+ "" + " values " + "(?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		int count = 0;
		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성 //보안

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getUserId());
			pstmt.setString(2, svo.getUserPassword());
			pstmt.setString(3, svo.getUserName());
			pstmt.setString(4, svo.getUserEmail());
			pstmt.setString(5, svo.getUserGender());
			pstmt.setString(6, svo.getUserPhone());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// 2. 아이디 중복확인
	public static int checkUserId(String userId) {
		StringBuffer checkuserId = new StringBuffer("select count(*) from usertbl where userId = ? ");
		int resultCount = 0;
		Connection con = null;
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			try {
				con = DBUtil.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			psmt = con.prepareStatement(checkuserId.toString());
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, userId);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				resultCount = rs.getInt(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login 실패 ]: UserDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1.6 CLOSE DataBase psmt object
				// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
				// 반드시 있으면 닫아라.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("자원 닫기 실패 : psmt & con (데이터 자원) 닫는 데에 문제가 발생했어요.");
			}
		}

		return resultCount;
	}

	// 3.이메일 중복확인
	public static int checkUserEmail(String userEmail) {
		StringBuffer checkUserEmail = new StringBuffer("select count(*) from usertbl where userEmail = ? ");
		int resultCount = 0;
		Connection con = null;
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			try {
				con = DBUtil.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			psmt = con.prepareStatement(checkUserEmail.toString());
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, userEmail);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				resultCount = rs.getInt(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login 실패 ]: UserDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1.6 CLOSE DataBase psmt object
				// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
				// 반드시 있으면 닫아라.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("자원 닫기 실패 : psmt & con (데이터 자원) 닫는 데에 문제가 발생했어요.");
			}
		}

		return resultCount;
	}

	// 4.아이디,비밀번호 매칭 체크
	public static int checkPW(String uId, String tPW) {
		StringBuffer checkTchID = new StringBuffer("select count(*) from userTbl where userPassword = ? and userID =?");
		int resultCount = 0;
		Connection con = null;
		PreparedStatement psmt = null;

		ResultSet rs = null;
		try {
			try {
				con = DBUtil.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			psmt = con.prepareStatement(checkTchID.toString());
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, tPW);
			psmt.setString(2, uId);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				resultCount = rs.getInt(1);
			}
			if (resultCount == 0) {
				Function.callAlert("LOGIN 실패 : 아이디와 패스워드가 일치하지 않습니다.");
				return resultCount;
			}

		} catch (SQLException e) {
			Function.callAlert("login 실패 : TeacherDAO Check PW");

			e.printStackTrace();
		} finally {
			try {
				// 1.6 CLOSE DataBase psmt object
				// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
				// 반드시 있으면 닫아라.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("자원 닫기 실패 : psmt & con (데이터 자원) 닫는 데에 문제가 발생했어요.");
			}
		}

		return resultCount;
	}

	// 5.유저 전체 출력
	public ArrayList<UserVO> getUserTotal() {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String dml = "select * from usertbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO userVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userVO = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));
				list.add(userVO);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}

	public ArrayList<UserVO> getUserCheck(String text) {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String dml = "select * from usertbl where userId like ? or userName like  ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO retval = null;
		String sName = "%" + text + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, sName);
			pstmt.setString(2, sName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));
				list.add(retval);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;

	}

	public static String findPass(String a, String b) {

		StringBuffer findPass = new StringBuffer(
				"select userPassword from usertbl where userName = ? and userEmail = ? ");
		String pass = null;

		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			try {
				con = DBUtil.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			psmt = con.prepareStatement(findPass.toString());
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, a);
			psmt.setString(2, b);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				pass = rs.getString(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login 실패 ]: SkinDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1. CLOSE DataBase psmt object
				// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
				// 반드시 있으면 닫아라.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("자원 닫기 실패 : psmt & con (데이터 자원) 닫는 데에 문제가 발생했어요.");
			}
		}

		return pass;

	}

	// 로그인한 회원이름 가져오기
	public static String getLoginName(String userId) {

		StringBuffer checkUserId = new StringBuffer("select userName from usertbl where userId = ?");
		String userName = null;

		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			try {
				con = DBUtil.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			psmt = con.prepareStatement(checkUserId.toString());
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, userId);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				userName = rs.getString(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login 실패 ]: SkinDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1. CLOSE DataBase psmt object
				// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
				// 반드시 있으면 닫아라.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("자원 닫기 실패 : psmt & con (데이터 자원) 닫는 데에 문제가 발생했어요.");
			}
		}

		return userName;
	}
	
	
	
	
	//채민
	public  ArrayList<UserVO> searchUserEdit(String userID) throws ClassNotFoundException  {
		ArrayList<UserVO> list=new ArrayList<UserVO>();
		String searchuserId = "select * from usertbl where userId=? ";
		// .2 BRING DataBase Connection
		Connection con = null;
		// .3 (쿼리문)MAKE 실행해야할 클래스 Statement
		PreparedStatement psmt = null;
		// .4 쿼리문 실행 후 가져와야 할  레코드를 담고있는  Set 객체 
		ResultSet rs = null;
		UserVO userVO=null;
		
		try {
			con = DBUtil.getConnection();
			psmt = con.prepareStatement(searchuserId);
			psmt.setString(1, userID);	
			
			// .5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 !가져올때! 사용하는 번개문 
			rs = psmt.executeQuery();
			
			if (rs == null) {
				Function.callAlert("[Search Name by UserID] 쿼리문 실행 실패 : [Search Selected UserVO] 쿼리문 실행하는 데에 문제가 발생했어요.");
				return null;
			}
			
			while(rs.next()) {
				userVO= new UserVO(rs.getString(1),rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				list.add(userVO);
			}
			
		} catch (SQLException e) { 
			Function.callAlert("[Search Name by UserID] 이름찾기 실패 : 데이터 삽입에 문제가 발생했어요.");
			e.printStackTrace();
		} finally {
			try {
				// .6 CLOSE DataBase psmt object
				// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
				// 반드시 있으면 닫아라.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("자원 닫기 실패 : psmt & con (데이터 자원) 닫는 데에 문제가 발생했어요.");
				
			}
		}		
		return list;
	}
	
	

}
