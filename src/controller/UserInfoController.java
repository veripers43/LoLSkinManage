package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SaleVO;
import model.SkinVO;
import model.UserVO;

public class UserInfoController implements Initializable {

	ObservableList<SaleVO> data3;

	private SaleDAO saleDAO;
	private SkinDAO skinDAO;
	private SaleVO saleVO;
	private SkinVO skinVO;

	@FXML
	private TableView tvUserBuy;
	@FXML
	private PieChart pcUserBuy;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// �������� ���̺�信 ������ ���Ÿ���� ���
		buyTableviewSetting();

		// ���Ÿ�� ����
		userBuySetting();

		// ������Ʈ ����
		PieChartSetting();

	}

	private void PieChartSetting() {

		ArrayList<SkinVO> list2 = null;
		skinDAO = new SkinDAO();
		SaleVO saleVO = null;

		list2 = SkinDAO.pieChartSetting(AdminController.userId.toString());

		if (list2.size() == 0) {
			ObservableList<Data> list = FXCollections.observableArrayList(new PieChart.Data("����", 1));
			pcUserBuy.setData(list);
		} else if (list2.size() == 1) {
			ObservableList<Data> list = FXCollections
					.observableArrayList(new PieChart.Data(list2.get(0).getSkinChamp(), list2.get(0).getCount()));
			pcUserBuy.setData(list);
		} else if (list2.size() == 2) {
			ObservableList<Data> list = FXCollections.observableArrayList(
					new PieChart.Data(list2.get(0).getSkinChamp(), list2.get(0).getCount()),
					new PieChart.Data(list2.get(1).getSkinChamp(), list2.get(1).getCount()));
			pcUserBuy.setData(list);
		} else if (list2.size() == 3) {
			ObservableList<Data> list = FXCollections.observableArrayList(
					new PieChart.Data(list2.get(0).getSkinChamp(), list2.get(0).getCount()),
					new PieChart.Data(list2.get(1).getSkinChamp(), list2.get(1).getCount()),
					new PieChart.Data(list2.get(2).getSkinChamp(), list2.get(2).getCount()));
			pcUserBuy.setData(list);
		} else if (list2.size() == 4) {
			ObservableList<Data> list = FXCollections.observableArrayList(
					new PieChart.Data(list2.get(0).getSkinChamp(), list2.get(0).getCount()),
					new PieChart.Data(list2.get(1).getSkinChamp(), list2.get(1).getCount()),
					new PieChart.Data(list2.get(2).getSkinChamp(), list2.get(2).getCount()),
					new PieChart.Data(list2.get(3).getSkinChamp(), list2.get(3).getCount()));
			pcUserBuy.setData(list);
		} else if (list2.size() == 5) {
			ObservableList<Data> list = FXCollections.observableArrayList(
					new PieChart.Data(list2.get(0).getSkinChamp(), list2.get(0).getCount()),
					new PieChart.Data(list2.get(1).getSkinChamp(), list2.get(1).getCount()),
					new PieChart.Data(list2.get(2).getSkinChamp(), list2.get(2).getCount()),
					new PieChart.Data(list2.get(3).getSkinChamp(), list2.get(3).getCount()),
					new PieChart.Data(list2.get(4).getSkinChamp(), list2.get(4).getCount()));
			pcUserBuy.setData(list);
		} else {
			Function.alertDisplay(1, "����", "���ŷ� �ʰ�", "���");
		}

	}

	// ������ ������ ��� ���
	private void userBuySetting() {
		ArrayList<SaleVO> list = null;
		saleDAO = new SaleDAO();
		SaleVO saleVO = null;
		list = saleDAO.getBuyList(AdminController.userId.toString());

		if (list == null) {
			Function.alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {

			for (int i = 0; i < list.size(); i++) {
				saleVO = list.get(i);
				data3.add(saleVO);

			}
		}

	}

	// ���� ���̺�� ����
	private void buyTableviewSetting() {

		data3 = FXCollections.observableArrayList(); // ���̺� ����
		tvUserBuy.setEditable(false);
		tvUserBuy.setPlaceholder(new Label("���� ������ �����ϴ�"));

		TableColumn colName = new TableColumn("��Ų��");
		colName.setPrefWidth(280);
		colName.setCellValueFactory(new PropertyValueFactory<>("saleDate"));

		TableColumn colRegister = new TableColumn("���ų�¥");
		colRegister.setPrefWidth(125);
		colRegister.setCellValueFactory(new PropertyValueFactory<>("skinName"));

		TableColumn colPrice = new TableColumn("����(��)");
		colPrice.setPrefWidth(80);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("no"));

		// ���̺� ������ �߰�
		tvUserBuy.setItems(data3);
		tvUserBuy.getColumns().addAll(colName, colRegister, colPrice);

	}

}
