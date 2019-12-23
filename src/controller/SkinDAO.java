package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import model.SkinVO;
import model.UserVO;

public class SkinDAO {

	// 1.��Ų ������ �����ͺ��̽��� �Է�
	public int getSkinRegiste(SkinVO svo) {
		// �� ������ ó���� ���� SQL ��

		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into skintbl "
				+ "(skinName, skinChamp, skinPrice, skinRegister, skinImage1, skinImage2, skinImage3 )" + "" + ""
				+ " values " + "(?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ���� //����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getSkinName());
			pstmt.setString(2, svo.getSkinChamp());
			pstmt.setInt(3, svo.getSkinPrice());
			pstmt.setString(4, svo.getSkinRegister());
			pstmt.setString(5, svo.getSkinImage1());
			pstmt.setString(6, svo.getSkinImage2());
			pstmt.setString(7, svo.getSkinImage3());

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

	// 2-0 ���ϴ� ���̺� ��������
	public ArrayList<SkinVO> getSkinSelect() {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select a.userName, sum(b.skinPrice) from saletbl c inner join usertbl a on c.userid = a.userid inner join"
				+ " skintbl b on c.skinName = b.skinName group by a.userid order by sum(b.skinPrice) desc limit 5;";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO skinVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				skinVO = new SkinVO(rs.getString(1), rs.getInt(2));
				list.add(skinVO);
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

	// 2-0 ���ϴ� ���̺� ��������
	public ArrayList<SkinVO> getSkinSelect2() {
		ArrayList<SkinVO> list2 = new ArrayList<SkinVO>();
		String dml = "select month(c.saleDate) as 'month', sum(b.skinPrice) from saletbl c inner join usertbl a on c.userid ="
				+ " a.userid inner join skintbl b on c.skinName = b.skinName group by month order by month ;";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO skinVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				skinVO = new SkinVO(rs.getString(1), rs.getInt(2));
				list2.add(skinVO);
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

		return list2;
	}

	// 2.��ü ��Ų ���
	public ArrayList<SkinVO> getSkinTotal() {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select * from skintbl;";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO skinVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				skinVO = new SkinVO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(skinVO);
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

	// 3.��Ų�� �ߺ�Ȯ��
	public static int checkSkinName(String SkinName) {
		StringBuffer checkskinName = new StringBuffer("select count(*) from skintbl where skinName = ? ");
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
			psmt = con.prepareStatement(checkskinName.toString());
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, SkinName);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getInt(1));
				resultCount = rs.getInt(1);

			}
			System.out.println("resultCont" + resultCount);
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

		return resultCount;
	}

	// 4.������ �ɸ����� ��Ų�̹��� ���ϸ� ��������
	public static String getSelectSkin1(String cn) {

		StringBuffer checkskinName = new StringBuffer("select skinimage1 from skintbl where skinName = ?");
		String imageFile1 = null;

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
			psmt = con.prepareStatement(checkskinName.toString());
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, cn);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				imageFile1 = rs.getString(1);

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

		return imageFile1;
	}

	// ������Ʈ �ҷ�����
	public static ArrayList<SkinVO> pieChartSetting(String text) {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select b.skinChamp, count(b.skinPrice) from(select * from saletbl where userid = ?) as c inner join usertbl a on c.userid = a.userid  "
				+ "inner join skintbl b on c.skinName = b.skinName group by b.skinChamp;";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO retval = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, text);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new SkinVO(rs.getString(1), rs.getInt(2));
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

	// 4.������ �ɸ����� ��Ų�̹��� ���ϸ� ��������
	public static String getSelectSkin2(String cn) {

		StringBuffer checkskinName = new StringBuffer("select skinimage2 from skintbl where skinName = ?");
		String imageFile2 = null;

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
			psmt = con.prepareStatement(checkskinName.toString());
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, cn);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				imageFile2 = rs.getString(1);

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

		return imageFile2;
	}

	public ArrayList<SkinVO> getSkinCheck(String text) {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select * from skintbl where skinChamp like ? or skinName like  ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO retval = null;
		String sName = "%" + text + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, sName);
			pstmt.setString(2, sName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new SkinVO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
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

	public void getSkinDelete(String skinName) {
		String dml = "delete from skintbl where skinName = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, skinName);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��Ų����");
				alert.setHeaderText("��Ų ���� �Ϸ�.");
				alert.setContentText("��Ų ���� ����!!!");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�л� ����");
				alert.setHeaderText("�л� ���� ����.");
				alert.setContentText("�л� ���� ����!!!");

				alert.showAndWait();
			}

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

	}

	public int getSkinEdit(SkinVO svo) {
		String dml = "update skintbl set skinPrice = ?, skinImage1 = ?,  " + "skinImage2 = ? where skinName = ?;";

		Connection con = null;
		PreparedStatement pstmt = null;

		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ���� //����
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, svo.getSkinPrice());
			pstmt.setString(2, svo.getSkinImage1());
			pstmt.setString(3, svo.getSkinImage2());
			pstmt.setString(4, svo.getSkinName());

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

	// ä��
	public ArrayList<SkinVO> getBarChart() {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select b.skinName, count(c.skinName) from saletbl c inner join usertbl a on c.userid = "
				+ "a.userid inner join skintbl b on c.skinName = b.skinName group by "
				+ "b.skinName order by count(c.skinName) desc limit 10;";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			// pstmt.setString(1, skinName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new SkinVO(rs.getString(1), rs.getInt(2));
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

	// ä��
	public ArrayList<SkinVO> skinVipRanking() {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select a.userName, sum(b.skinPrice) from saletbl c inner join usertbl a on c.userid = a.userid inner join"
				+ " skintbl b on c.skinName = b.skinName group by a.userid order by sum(b.skinPrice) desc limit 5;";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO skinVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				skinVO = new SkinVO(rs.getString(1), rs.getInt(2));
				list.add(skinVO);
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

	// ä��
	public ArrayList<SkinVO> getNewSkin(SkinVO svo) {
		ArrayList<SkinVO> nSkin = new ArrayList<SkinVO>();
		String dml = "select skinName, skinPrice, skinRegister from skintbl order by skinRegister desc limit 5";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO skinVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
//			pstmt.setString(1, svo.getSkinName());
//			pstmt.setInt(2, svo.getSkinPrice());
//			pstmt.setString(3, svo.getSkinRegister());

			rs = pstmt.executeQuery();
			while (rs.next()) {

				skinVO = new SkinVO(rs.getString(1), rs.getInt(2), rs.getString(3));

				nSkin.add(skinVO);
			}
		} catch (SQLException se) {
			System.out.println(se);
			se.printStackTrace();
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
		return nSkin;
	}

	// ä��
	public static String getSelectSkin(String cn) {

		StringBuffer checkskinName = new StringBuffer("select skinimage2 from skintbl where skinName = ?");
		String imageFile = null;
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
			psmt = con.prepareStatement(checkskinName.toString());
			// ù��° ����ǥ �ڸ� -> studentID ��ġ �����ִ� �۾�
			psmt.setString(1, cn);

			// 3.5 ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
			// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
			// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
			rs = psmt.executeQuery();

			while (rs.next()) {

				imageFile = rs.getString(1);

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

		return imageFile;
	}

	// 2.��ü ��Ų ���
	public ArrayList<SkinVO> getSkinSelect(SkinVO svo) {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select * from skintbl where skinChamp = ? ";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO skinVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getSkinChamp());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				File selectedFile = new File("/images/" + rs.getString(5));
				String localUrl = selectedFile.toURI().toURL().toString();
				skinVO = new SkinVO(new Image(localUrl, false), rs.getString(1), rs.getString(2), rs.getInt(3),
						rs.getString(4));
				list.add(skinVO);

				// pstmt.setString(1, "�Ƹ�");
			}
		} catch (SQLException se) {
			System.out.println(se);
			se.printStackTrace();
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

	// ä�� ������Ʈ ����
	public static ArrayList<SkinVO> pieChartSetting2() {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select  skinChamp, count(skinName) as cnt from skintbl group by skinchamp;";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO retval = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new SkinVO(rs.getString(1), rs.getInt(2));
				list.add(retval);

			}
		} catch (SQLException se) {
			System.out.println(se);
			se.printStackTrace();
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
	
	
	public int getSkinRegiste2(SkinVO svo) {
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into saletbl " + "(userId, skinName, saleDate )" + " values " + "(?, ?, now())";

		System.out.println(svo.getSkinName());
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println(svo);

		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// System.out.println(svo.);
			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ���� //����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, LoginController.userId);
			pstmt.setString(2, svo.getSkinName());

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
	
	public ArrayList<SkinVO> getSkinCheck2(String name) throws Exception {
		ArrayList<SkinVO> list = new ArrayList<SkinVO>();
		String dml = "select * from skintbl where skinChamp like ? or skinName like  ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SkinVO retval = null;
		String sName = "%" + name + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, sName);
			pstmt.setString(2, sName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				File selectedFile = new File("C:/images/" + rs.getString(5));
				String localUrl = selectedFile.toURI().toURL().toString();
				retval = new SkinVO(new Image(localUrl, false), rs.getString(1), rs.getString(2), rs.getInt(3),
						rs.getString(4));
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
	

}
