package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import model.SkinVO;

import model.UserVO;

public class AdminController implements Initializable {

	// ȸ������
	@FXML
	private TableView<UserVO> userTableView;

	@FXML
	private TextField txtUserSearch;
	@FXML
	private Button btnUserSearch;
	@FXML
	private Label lbUserName;
	@FXML
	private Button btnUserRefresh;

	// ��Ų����
	@FXML
	private TableView<SkinVO> skinTableView;
	@FXML
	private TextField txtSkinSearch;
	@FXML
	private Button btnSkinRefresh;
	@FXML
	private Button btnSkinAdd;

	@FXML
	private TextField txtSkinName;
	@FXML
	private ComboBox cbSkinChamp;
	@FXML
	private TextField txtSkinPrice;
	@FXML
	private DatePicker dpSkinRegister;
	@FXML
	private TextField txtSkinImage1;
	@FXML
	private TextField txtSkinImage2;
	@FXML
	private TextField txtSkinImage3;
	@FXML
	private Button btnSkinSearch;
	@FXML
	private Button btnSkinDelete;
	@FXML
	private Button btnSkinEdit;
	@FXML
	private Button btnSkinOk;
	@FXML
	private Button btnSkinCancel;
	@FXML
	private Button btnSkinImage1;
	@FXML
	private Button btnSkinImage2;
	@FXML
	private Button btnSkinImage3;
	@FXML
	private Button btnSkinBuy;

	// ������
	@FXML
	private BarChart staBarChart;
	@FXML
	private LineChart staLineChart;

	@FXML
	private TableView imageTest;

	ObservableList<UserVO> data1; // ���̺� �信 �������������ֱ� ���ؼ� ����� ������.
	ObservableList<SkinVO> data2; // ���̺� �信 ��Ų���������ֱ� ���ؼ� ����� ������.

	public static boolean btnOkFlag;
	public static String userId;

	// ��Ų ���̺� �並 ���������� ��ġ���� ��ü ���� �����Ҽ� �ִ� �������� ����.
	private int selectedIndex;
	private ObservableList<SkinVO> selectSkin;

	private UserVO userVO;
	private SkinVO skinVO;
	private UserDAO userDAO;
	private SkinDAO skinDAO;

	// �̹�������
	private String selectFileName = ""; // �̹��� ���ϸ�
	private String localUrl = ""; // �̹��� ���� ���
	private Image localImage;
	private File selectedFile = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 1-1.���̺�� �׸��
		userTableViewSetting();

		// 1-2.ó�� ������ �α����� ������ ���̺� ��� ������ ����� ����Ѵ�
		userTotalListSetting();

		// 1-3-1 ���̵�˻�
		btnUserSearch.setOnAction(e -> {
			handlerbtnUserSearchAction(e);
		});

		// 1.������� ���ΰ�ħ
		btnUserRefresh.setOnAction(e -> {
			userTotalListSetting();
		});

		// 1.������� ����Ŭ�� ������
		userTableView.setOnMousePressed(e -> {
			handlerUserTableViewPressAction(e);
		});

		// 2-1. ���̺�信 ��Ų ����� ǥ���Ѵ�
		skinTableViewSetting();

		// 2-2. ��Ų���̺� ��ü ��Ų�� ����Ѵ�
		skinTotalListSetting();

		// 2-2-1 ��Ų�˻�
		btnSkinSearch.setOnAction(e -> {
			handlerbtnSkinSearchAction(e);
		});

		// 2-2-3 �űԽ�Ų��Ϲ�ư
		btnSkinAdd.setOnAction(e -> {
			handlerbtnSkinAddAction(e);
		});

		// 2-3. �űԽ�Ų ��Ϲ�ư �̺�Ʈó��
		btnSkinOk.setOnAction(e -> {
			handlerbtnSkinOkAction(e);
		});

		// 2.4 ��Ų ���ΰ�ħ �̺�Ʈó��
		btnSkinRefresh.setOnAction(e -> {
			data2.removeAll(data2);
			skinTotalListSetting();

		});

		// 2. ��Ų������ư
		btnSkinDelete.setOnAction(e -> {
			handlerbtnSkinDeleteAction(e);
		});

		// 2.��Ų���� ����
		btnSkinEdit.setOnAction(e -> {
			handlerbtnSkinEditAction(e);
		});

		// 2.5. ���̺� �並 �������� �߻��Ǵ� �̺�Ʈ ó�� ���
		skinTableView.setOnMousePressed(e -> {
			handlerSkinTableViewPressAction(e);
		});

		// 2.6 �̹���1 ��Ϲ�ư
		btnSkinImage1.setOnAction(e -> {
			handlerbtnSkinImage1Action(e);
		});

		// 2.7 �̹���2 ��Ϲ�ư
		btnSkinImage2.setOnAction(e -> {
			handlerbtnSkinImage2Action(e);
		});

		// 2.8��ҹ�ư �̺�Ʈó��
		btnSkinCancel.setOnAction(e -> {
			handlerbtnSkinCancelAction(e);
		});

		// 4.1 ����Ʈ�� ������ ���
		barChartSetting();
		// 4.2 ������Ʈ�� ������ ���
		lineChartSetting();

		// 0. ó�� ���̺� ����
		initSetting();

		fieldInitSetting(true, true, true, true, true, true, true, true, true, true);
		btnSkinImage3.setDisable(true);
		txtSkinImage1.setDisable(true);
		txtSkinImage2.setDisable(true);
		txtSkinImage3.setDisable(true);

	}// end of initialized

	// 1.������� ���콺�� ����Ŭ�� ������
	public String handlerUserTableViewPressAction(MouseEvent e) {
		if (e.getClickCount() != 2) {
			return null;
		} else {
			Parent mainView = null;
			Stage mainStage = null;
			userId = userTableView.getSelectionModel().getSelectedItem().getUserId();

			try {

				mainView = FXMLLoader.load(getClass().getResource("/view/userInfo.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();

				mainStage.setTitle("��������");
				mainStage.setScene(scene); //

				mainStage.setResizable(false);

				mainStage.show();
			} catch (Exception e1) {
				Function.callAlert("ȭ�� ��ȯ ���� : ȭ�� ��ȯ�� ���� �߻�");
			}
		}
		return userId;

	}

	// 2.��Ų ������ư
	private void handlerbtnSkinDeleteAction(ActionEvent e) {
		try {
			// alertDisplay(2, "���� ���", "Delete", "���� �����Ͻðڽ��ϱ�?");
			SkinDAO studentDAO = new SkinDAO();
			studentDAO.getSkinDelete(selectSkin.get(0).getSkinName());
			data2.removeAll(data2);
			skinTotalListSetting();
		} catch (Exception e1) {
			Function.callAlert("��������");
		}
		// 1.��ư�ʱ�ȭ(����,���x,�ʱ�ȭ,���x,����,����x,����x)

	}

	// 1.ȸ�� ���̵� �˻�
	private void handlerbtnUserSearchAction(ActionEvent e) {
		try {
			ArrayList<UserVO> list = new ArrayList<UserVO>();
			UserDAO userDAO = new UserDAO();
			list = userDAO.getUserCheck(txtUserSearch.getText());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data1.removeAll(data1);
			for (UserVO svo : list) {
				data1.add(svo);

			}

		} catch (Exception e1) {
			Function.alertDisplay(1, "�˻� ���", "�̸��˻�����", e.toString());
		}

	}

	// 2. ������ư �̺�Ʈó��
	private void handlerbtnSkinEditAction(ActionEvent e) {
		fieldInitSetting(true, true, false, true, false, false, true, true, false, false);
		btnOkFlag = false;

	}

	// �̹���2 ��Ϲ�ư
	private void handlerbtnSkinImage2Action(ActionEvent e) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnSkinOk.getScene().getWindow());
			if (selectedFile != null) { // �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] array1 = localUrl.split("/");
		txtSkinImage2.setText(array1[6]);

		btnSkinOk.setDisable(false);

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}

	}

	private void handlerbtnSkinCancelAction(ActionEvent e) {
		fieldInitSetting(true, true, true, true, true, true, true, true, true, true);
		btnSkinImage3.setDisable(true);
		txtSkinImage1.setDisable(true);
		txtSkinImage2.setDisable(true);
		txtSkinImage3.setDisable(true);

	}

	// ��Ų��� ��ư �̺�Ʈó��
	private void handlerbtnSkinAddAction(ActionEvent e) {
		fieldInitSetting(false, false, false, false, false, false, true, true, false, false);
		btnOkFlag = true;
		txtSkinName.clear();
		txtSkinPrice.clear();
		dpSkinRegister.getEditor().clear();
		txtSkinImage1.clear();
		txtSkinImage2.clear();

	}

	private void initSetting() {
		cbSkinChamp.setItems(FXCollections.observableArrayList("�Ƹ�", "��Į��", "�ֽ�", "����Ʋ��", "ī�̻�", "��þ�", "���", "����",
				"���׿�", "Ű�Ƴ�", "��ĭ", "�ھ�", "Ƽ��", "�߽���", "����"));
		cbSkinChamp.setValue("�Ƹ�");

	}

	// �ʵ����
	private void fieldInitSetting(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g,
			boolean h, boolean i, boolean j) {
		txtSkinName.setDisable(a);
		cbSkinChamp.setDisable(b);
		txtSkinPrice.setDisable(c);
		dpSkinRegister.setDisable(d);
		btnSkinImage1.setDisable(e);
		btnSkinImage2.setDisable(f);
		btnSkinDelete.setDisable(g);
		btnSkinEdit.setDisable(h);
		btnSkinOk.setDisable(i);
		btnSkinCancel.setDisable(j);

	}

	// ��ư����

	// 1-1���� ����Ʈ�� ����
	public void userTableViewSetting() {

		data1 = FXCollections.observableArrayList(); // ���̺� ����
		userTableView.setEditable(false);

		TableColumn colId = new TableColumn("�ƾƵ�");
		colId.setPrefWidth(120);
		colId.setCellValueFactory(new PropertyValueFactory<>("userId"));

		TableColumn colPass = new TableColumn("��й�ȣ");
		colPass.setPrefWidth(120);
		colPass.setCellValueFactory(new PropertyValueFactory<>("userPassword"));

		TableColumn colName = new TableColumn("�̸�");
		colName.setPrefWidth(80);
		colName.setCellValueFactory(new PropertyValueFactory<>("userName"));

		TableColumn colEmail = new TableColumn("�̸���");
		colEmail.setPrefWidth(160);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

		TableColumn colGender = new TableColumn("����");
		colGender.setPrefWidth(60);
		colGender.setCellValueFactory(new PropertyValueFactory<>("userGender"));

		TableColumn colPhone = new TableColumn("��ȭ��ȣ");
		colPhone.setPrefWidth(150);
		colPhone.setCellValueFactory(new PropertyValueFactory<>("userPhone"));

		// ���̺� ������ �߰�
		userTableView.setItems(data1);
		userTableView.getColumns().addAll(colId, colPass, colName, colEmail, colGender, colPhone);

	}

	// �α��� ȸ�� �̸� ���
	public void userTotalListSetting() {
		ArrayList<UserVO> list = null;
		userDAO = new UserDAO();

		list = userDAO.getUserTotal();

		if (list == null) {
			Function.alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {
			for (int i = 0; i < list.size(); i++) {
				userVO = list.get(i);

				data1.add(userVO);
			}
		}

		lbUserName.setText(userDAO.getLoginName(LoginController.userId));

	}

	// 2-1. ���̺�信 ��Ų ����� ǥ���Ѵ�
	public void skinTableViewSetting() {
		data2 = FXCollections.observableArrayList(); // ���̺� ����
		skinTableView.setEditable(false);

		TableColumn colName = new TableColumn("��Ų��");
		colName.setPrefWidth(240);
		colName.setCellValueFactory(new PropertyValueFactory<>("skinName"));

		TableColumn colChamp = new TableColumn("è�Ǿ�");
		colChamp.setPrefWidth(70);
		colChamp.setCellValueFactory(new PropertyValueFactory<>("skinChamp"));

		TableColumn colPrice = new TableColumn("����");
		colPrice.setPrefWidth(70);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("skinPrice"));

		TableColumn colRegister = new TableColumn("�����");
		colRegister.setPrefWidth(150);
		colRegister.setCellValueFactory(new PropertyValueFactory<>("skinRegister"));

		TableColumn colImage1 = new TableColumn("�̹���1");
		colImage1.setPrefWidth(20);
		colImage1.setCellValueFactory(new PropertyValueFactory<>("skinImage1"));

		TableColumn colImage2 = new TableColumn("�̹���2");
		colImage2.setPrefWidth(20);
		colImage2.setCellValueFactory(new PropertyValueFactory<>("skinImage2"));

		TableColumn colImage3 = new TableColumn("�̹���3");
		colImage3.setPrefWidth(20);
		colImage3.setCellValueFactory(new PropertyValueFactory<>("skinImage3"));

		// ���̺� ������ �߰�
		skinTableView.setItems(data2);
		skinTableView.getColumns().addAll(colName, colChamp, colPrice, colRegister, colImage1, colImage2, colImage3);

	}

	// 2-2. ��Ų���̺� ��ü ��Ų�� ����Ѵ�
	public void skinTotalListSetting() {

		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = null;
		list = skinDAO.getSkinTotal();

		if (list == null) {
			Function.alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {
			for (int i = 0; i < list.size(); i++) {
				skinVO = list.get(i);
				data2.add(skinVO);

			}
		}
	}

	// 2-3. �űԽ�Ų ��Ϲ�ư �̺�Ʈó��
	private void handlerbtnSkinOkAction(ActionEvent e) {

		if (btnOkFlag == true) {

			int count1 = SkinDAO.checkSkinName(txtSkinName.getText().toString());

			if (txtSkinName.getText().equals("") || txtSkinPrice.getText().equals("")
					|| dpSkinRegister.getValue().equals("") || txtSkinImage1.getText().equals("")
					|| txtSkinImage2.getText().equals("")) {
				Function.alertDisplay(1, "�Է¿���", "�ʵ����", "��� �ʵ带 �Է����ּ���");
				return;
			} else if (count1 != 0) {
				Function.callAlert("[��Ų�� �ߺ�] : �����ϴ� ��Ų���Դϴ�. ");
			} else {

				skinVO = new SkinVO(txtSkinName.getText(), cbSkinChamp.getSelectionModel().getSelectedItem().toString(),
						Integer.parseInt(txtSkinPrice.getText().trim()), dpSkinRegister.getValue().toString(),
						txtSkinImage1.getText(), txtSkinImage2.getText(), txtSkinImage3.getText());

				skinDAO = new SkinDAO(); // ��ü�� �θ���.

				int count3 = skinDAO.getSkinRegiste(skinVO);
				if (count3 != 0) {
					Function.callAlert("[��Ų��� ����] : ��Ų��� ����" + "");
				}
			}

		} else {

			skinVO = new SkinVO(Integer.parseInt(txtSkinPrice.getText().trim()), txtSkinImage1.getText(),
					txtSkinImage2.getText(), txtSkinName.getText());

			skinDAO = new SkinDAO(); // ��ü�� �θ���.

			int count3 = skinDAO.getSkinEdit(skinVO);
			if (count3 != 0) {
				Function.callAlert("[��Ų���� ����] : ��Ų���� ����" + "");
			}
		}

		txtSkinName.clear();
		txtSkinPrice.clear();
		dpSkinRegister.getEditor().clear();
		txtSkinImage1.clear();
		txtSkinImage2.clear();

	}

	// 2-2-1 ��Ų�˻�
	private void handlerbtnSkinSearchAction(ActionEvent e) {
		try {
			ArrayList<SkinVO> list = new ArrayList<SkinVO>();
			SkinDAO studentDAO = new SkinDAO();
			list = studentDAO.getSkinCheck(txtSkinSearch.getText());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data2.removeAll(data2);
			for (SkinVO svo : list) {
				data2.add(svo);
			}

		} catch (Exception e1) {
			Function.alertDisplay(1, "�˻� ���", "�̸��˻�����", e.toString());
		}

	}

	// 2.6 �̹��� ��Ϲ�ư
	private void handlerbtnSkinImage1Action(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnSkinOk.getScene().getWindow());
			if (selectedFile != null) { // �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] array1 = localUrl.split("/");
		txtSkinImage1.setText(array1[6]);

		btnSkinOk.setDisable(false);

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}

	}

	// 4.1 ����Ʈ�� ������ ���
	private void barChartSetting() {

		skinDAO = new SkinDAO();

		// �� ��Ʈ�� ��� ���������� �Է��Ѵ�.
		XYChart.Series skinCount = new XYChart.Series();
		skinCount.setName("VIP ����");

		ObservableList skinList = FXCollections.observableArrayList();

		for (int i = 0; i < skinDAO.getSkinSelect().size(); i++) {
			skinList.add(new XYChart.Data(skinDAO.getSkinSelect().get(i).getSkinChamp(),
					skinDAO.getSkinSelect().get(i).getCount()));
		}
		

		skinCount.setData(skinList);
		staBarChart.getData().add(skinCount);
	

	}

	// 4.2 ������Ʈ�� ������ ���
	private void lineChartSetting() {
		skinDAO = new SkinDAO();

		// �� ��Ʈ�� ��� ���������� �Է��Ѵ�.
		LineChart.Series skinCount = new LineChart.Series();
		skinCount.setName("��������");

		ObservableList skinList = FXCollections.observableArrayList();

		for (int i = 0; i < skinDAO.getSkinSelect2().size(); i++) {

			skinList.add(new XYChart.Data(skinDAO.getSkinSelect2().get(i).getSkinChamp()+"��",
					skinDAO.getSkinSelect2().get(i).getCount()));

			
		}

		skinCount.setData(skinList);
		staLineChart.getData().add(skinCount);

	}

	// 2.5. ���̺� �並 �������� �߻��Ǵ� �̺�Ʈ ó�� ���
	private void handlerSkinTableViewPressAction(MouseEvent e) {

		if (e.getClickCount() != 2) {

			selectedIndex = skinTableView.getSelectionModel().getSelectedIndex();
			selectSkin = skinTableView.getSelectionModel().getSelectedItems();

			LocalDate date = null;
			String[] array = selectSkin.get(0).getSkinRegister().split("-");
			dpSkinRegister.setValue(
					date.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2])));

			// �������� ���̺� �ִ� ���� �����ͼ� ������ �ʵ忡 ����ִ´�.
			txtSkinName.setText(selectSkin.get(0).getSkinName());
			cbSkinChamp.setValue(selectSkin.get(0).getSkinChamp());
			// dpSkinRegister.setValue(selectSkin.get(0).getSkinRegister());
			txtSkinPrice.setText(String.valueOf(selectSkin.get(0).getSkinPrice()));
			txtSkinImage1.setText(selectSkin.get(0).getSkinImage1());
			txtSkinImage2.setText(selectSkin.get(0).getSkinImage2());
			txtSkinImage3.setText(selectSkin.get(0).getSkinImage3());

			fieldInitSetting(true, true, true, true, true, true, false, false, true, true);

		} else {

			Parent mainView = null;
			Stage mainStage = null;

			try {

				mainView = FXMLLoader.load(getClass().getResource("/view/purchase.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();

				mainStage.setTitle("����������");
				mainStage.setScene(scene); //

				mainStage.setResizable(false);

				mainStage.show();
			} catch (Exception e1) {
				Function.callAlert("ȭ�� ��ȯ ���� : ȭ�� ��ȯ�� ���� �߻�");
			}

			ImageView imageBuyMain = (ImageView) mainView.lookup("#imageBuyMain");
			ImageView imageBuyIngame = (ImageView) mainView.lookup("#imageBuyIngame");
			Button btnBuyOk = (Button) mainView.lookup("#btnBuyOk");

			String imageFile1 = SkinDAO
					.getSelectSkin1(skinTableView.getSelectionModel().getSelectedItem().getSkinName());
			String imageFile2 = SkinDAO
					.getSelectSkin2(skinTableView.getSelectionModel().getSelectedItem().getSkinName());

			localImage = new Image("/images/" + imageFile1, false);
			imageBuyMain.setImage(localImage);
			localImage = new Image("/skins/" + imageFile2, false);
			imageBuyIngame.setImage(localImage);

			btnBuyOk.setOnAction(e1 -> {
				((Stage) btnBuyOk.getScene().getWindow()).close();
			});

		}

	}

}
