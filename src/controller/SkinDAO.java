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

	// 1.스킨 정보를 데이터베이스에 입력
	public int getSkinRegiste(SkinVO svo) {
		// ② 데이터 처리를 위한 SQL 문

		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into skintbl "
				+ "(skinName, skinChamp, skinPrice, skinRegister, skinImage1, skinImage2, skinImage3 )" + "" + ""
				+ " values " + "(?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		int count = 0;
		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성 //보안
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getSkinName());
			pstmt.setString(2, svo.getSkinChamp());
			pstmt.setInt(3, svo.getSkinPrice());
			pstmt.setString(4, svo.getSkinRegister());
			pstmt.setString(5, svo.getSkinImage1());
			pstmt.setString(6, svo.getSkinImage2());
			pstmt.setString(7, svo.getSkinImage3());

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

	// 2-0 원하는 테이블만 가져오기
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

	// 2-0 원하는 테이블만 가져오기
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

	// 2.전체 스킨 출력
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

	// 3.스킨명 중복확인
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
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, SkinName);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getInt(1));
				resultCount = rs.getInt(1);

			}
			System.out.println("resultCont" + resultCount);
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

		return resultCount;
	}

	// 4.선택한 케릭터의 스킨이미지 파일명 가져오기
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
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, cn);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				imageFile1 = rs.getString(1);

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

		return imageFile1;
	}

	// 파이차트 불러오기
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

	// 4.선택한 케릭터의 스킨이미지 파일명 가져오기
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
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, cn);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				imageFile2 = rs.getString(1);

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
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, skinName);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("스킨삭제");
				alert.setHeaderText("스킨 삭제 완료.");
				alert.setContentText("스킨 삭제 성공!!!");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("학생 삭제");
				alert.setHeaderText("학생 삭제 실패.");
				alert.setContentText("학생 삭제 실패!!!");

				alert.showAndWait();
			}

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

	}

	public int getSkinEdit(SkinVO svo) {
		String dml = "update skintbl set skinPrice = ?, skinImage1 = ?,  " + "skinImage2 = ? where skinName = ?;";

		Connection con = null;
		PreparedStatement pstmt = null;

		int count = 0;
		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성 //보안
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, svo.getSkinPrice());
			pstmt.setString(2, svo.getSkinImage1());
			pstmt.setString(3, svo.getSkinImage2());
			pstmt.setString(4, svo.getSkinName());

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

	// 채민
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

	// 채민
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

	// 채민
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

	// 채민
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
			// 첫번째 물음표 자리 -> studentID 매치 시켜주는 작업
			psmt.setString(1, cn);

			// 3.5 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
			// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
			// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
			rs = psmt.executeQuery();

			while (rs.next()) {

				imageFile = rs.getString(1);

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

		return imageFile;
	}

	// 2.전체 스킨 출력
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

				// pstmt.setString(1, "아리");
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

	// 채민 파이차트 셋팅
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
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into saletbl " + "(userId, skinName, saleDate )" + " values " + "(?, ?, now())";

		System.out.println(svo.getSkinName());
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println(svo);

		int count = 0;
		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// System.out.println(svo.);
			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성 //보안
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, LoginController.userId);
			pstmt.setString(2, svo.getSkinName());

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
