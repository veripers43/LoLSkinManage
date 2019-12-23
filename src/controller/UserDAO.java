package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.SkinVO;
import model.UserVO;

public class UserDAO {

	// 1.������ ������ �����ͺ��̽��� �Է�
	public int getUserRegiste(UserVO svo) {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into usertbl " + "(userId, userPassword, userName, userEmail, userGender, userPhone)" + ""
				+ "" + " values " + "(?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ���� //����

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getUserId());
			pstmt.setString(2, svo.getUserPassword());
			pstmt.setString(3, svo.getUserName());
			pstmt.setString(4, svo.getUserEmail());
			pstmt.setString(5, svo.getUserGender());
			pstmt.setString(6, svo.getUserPhone());

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// 2. ���̵� �ߺ�Ȯ��
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
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, userId);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				resultCount = rs.getInt(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login ���� ]: UserDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1.6 CLOSE DataBase psmt object
				// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
				// �ݵ�� ������ �ݾƶ�.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("�ڿ� �ݱ� ���� : psmt & con (������ �ڿ�) �ݴ� ���� ������ �߻��߾��.");
			}
		}

		return resultCount;
	}

	// 3.�̸��� �ߺ�Ȯ��
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
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, userEmail);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				resultCount = rs.getInt(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login ���� ]: UserDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1.6 CLOSE DataBase psmt object
				// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
				// �ݵ�� ������ �ݾƶ�.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("�ڿ� �ݱ� ���� : psmt & con (������ �ڿ�) �ݴ� ���� ������ �߻��߾��.");
			}
		}

		return resultCount;
	}

	// 4.���̵�,��й�ȣ ��Ī üũ
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
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, tPW);
			psmt.setString(2, uId);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				resultCount = rs.getInt(1);
			}
			if (resultCount == 0) {
				Function.callAlert("LOGIN ���� : ���̵�� �н����尡 ��ġ���� �ʽ��ϴ�.");
				return resultCount;
			}

		} catch (SQLException e) {
			Function.callAlert("login ���� : TeacherDAO Check PW");

			e.printStackTrace();
		} finally {
			try {
				// 1.6 CLOSE DataBase psmt object
				// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
				// �ݵ�� ������ �ݾƶ�.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("�ڿ� �ݱ� ���� : psmt & con (������ �ڿ�) �ݴ� ���� ������ �߻��߾��.");
			}
		}

		return resultCount;
	}

	// 5.���� ��ü ���
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
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, a);
			psmt.setString(2, b);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				pass = rs.getString(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login ���� ]: SkinDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1. CLOSE DataBase psmt object
				// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
				// �ݵ�� ������ �ݾƶ�.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("�ڿ� �ݱ� ���� : psmt & con (������ �ڿ�) �ݴ� ���� ������ �߻��߾��.");
			}
		}

		return pass;

	}

	// �α����� ȸ���̸� ��������
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
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, userId);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				userName = rs.getString(1);

			}

		} catch (SQLException e) {
			Function.callAlert("[Login ���� ]: SkinDAO");
			e.printStackTrace();
		} finally {
			try {
				// 1. CLOSE DataBase psmt object
				// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
				// �ݵ�� ������ �ݾƶ�.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("�ڿ� �ݱ� ���� : psmt & con (������ �ڿ�) �ݴ� ���� ������ �߻��߾��.");
			}
		}

		return userName;
	}
	
	
	
	
	//ä��
	public  ArrayList<UserVO> searchUserEdit(String userID) throws ClassNotFoundException  {
		ArrayList<UserVO> list=new ArrayList<UserVO>();
		String searchuserId = "select * from usertbl where userId=? ";
		// .2 BRING DataBase Connection
		Connection con = null;
		// .3 (������)MAKE �����ؾ��� Ŭ���� Statement
		PreparedStatement psmt = null;
		// .4 ������ ���� �� �����;� ��  ���ڵ带 ����ִ�  Set ��ü 
		ResultSet rs = null;
		UserVO userVO=null;
		
		try {
			con = DBUtil.getConnection();
			psmt = con.prepareStatement(searchuserId);
			psmt.setString(1, userID);	
			
			// .5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� !�����ö�! ����ϴ� ������ 
			rs = psmt.executeQuery();
			
			if (rs == null) {
				Function.callAlert("[Search Name by UserID] ������ ���� ���� : [Search Selected UserVO] ������ �����ϴ� ���� ������ �߻��߾��.");
				return null;
			}
			
			while(rs.next()) {
				userVO= new UserVO(rs.getString(1),rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				list.add(userVO);
			}
			
		} catch (SQLException e) { 
			Function.callAlert("[Search Name by UserID] �̸�ã�� ���� : ������ ���Կ� ������ �߻��߾��.");
			e.printStackTrace();
		} finally {
			try {
				// .6 CLOSE DataBase psmt object
				// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
				// �ݵ�� ������ �ݾƶ�.
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				Function.callAlert("�ڿ� �ݱ� ���� : psmt & con (������ �ڿ�) �ݴ� ���� ������ �߻��߾��.");
				
			}
		}		
		return list;
	}
	
	

}
