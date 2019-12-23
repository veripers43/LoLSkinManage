package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.SaleVO;
import model.UserVO;

public class SaleDAO {

	public ArrayList<SaleVO> getBuyList(String text) {
		ArrayList<SaleVO> list = new ArrayList<SaleVO>();

		String dml = "select c.saleDate, b.skinName, b.skinPrice from saletbl c inner join usertbl a on c.userid = a.userid"
				+ " inner join skintbl b on c.skinName = b.skinName where c.userid = ? order by c.saledate desc;";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SaleVO retval = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, text);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				retval = new SaleVO(rs.getString(2), rs.getString(1), rs.getInt(3));
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
