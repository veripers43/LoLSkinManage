package controller;

import java.awt.Checkbox;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import model.SaleVO;
import model.SkinVO;
import model.UserVO;

public class RootController implements Initializable {
	@FXML
	private Button btnAhri;
	@FXML
	private Button btnAkali;
	@FXML
	private Button btnAshe;
	@FXML
	private Button btnCaitlyn;
	@FXML
	private Button btnKaisa;
	@FXML
	private Button btnLucian;
	@FXML
	private Button btnLulu;
	@FXML
	private Button btnLux;
	@FXML
	private Button btnPantheon;
	@FXML
	private Button btnQiyana;
	@FXML
	private Button btnRakan;
	@FXML
	private Button btnXayah;
	@FXML
	private Button btnTeemo;
	@FXML
	private Button btnYasuo;
	@FXML
	private Button btnYuumi;
	@FXML
	private TableView<SkinVO> skinTableView;
	@FXML
	private Button btnMyRoom;
	@FXML
	private Button btnMyRoom2;
	@FXML
	private Button btnMyRoom3;
	@FXML
	private Button btnCharge;
	@FXML
	private Button btnCharge2;
	@FXML
	private Button btnCharge3;
	@FXML
	private Label lbName;
	@FXML
	private Label lbName1;
	@FXML
	private Label lbName2;
	@FXML
	private Label lbName3;
	@FXML
	private Label lbName4;
	@FXML
	private Label lbName5;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private RadioButton rbSkin;
	@FXML
	private Label lbMoney;
	@FXML
	private Label lbMoney2;
	@FXML
	private TableView<SkinVO> tvVip;
	@FXML
	private TableView<SkinVO> tvNewSkin;
	@FXML
	private ImageView ivNotice;
	@FXML
	ComboBox<String> cbChamp;
	private UserDAO userDAO;
	ObservableList<UserVO> data1;
	ObservableList<SkinVO> data2;
	ObservableList<SkinVO> data4;
	ObservableList<SkinVO> data5;
	ObservableList<SaleVO> data3;
	
	
	
	private boolean editDelete = false;
	@FXML
	private ImageView ivTimeList;
	private SkinVO skinVO;
	private SkinDAO skinDAO;
	private SaleDAO saleDAO;
	private SaleVO saleVO;
	private UserVO userVO;
	private String selectFileName = "";
	private ImageView skinImage2;
	@FXML
	private TextArea taPromise;
	@FXML
	private CheckBox cbPromise;
	private String localUrl = " ";
	private Image localImage;
	private File selectedFile = null;
	private File dirSave = new File("C:/skins");
	private File file = null;
	@FXML
	private BarChart skinBarChart;
	@FXML
	private PieChart skinPieChart;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		barChartSetting();
		handlerPieChartAction();

		skinTableViewSetting();

		lbName.setText(UserDAO.getLoginName(LoginController.userId));
		lbName1.setText(UserDAO.getLoginName(LoginController.userId));

		// ���̷� �˾�â ����
		btnMyRoom.setOnAction(event -> {
			handlerbtnMyRoomAction(event);
		});
		// ���� �˾�â ����
		btnCharge.setOnAction(event -> {
			handlerChargeAction(event);
		});
		// ���̺�� ����
		tvNewSkinSetting();
		tvVipRanking();

		// db �������̺� ����

		// ĳ���� �Ƹ� �̹�����ư Ŭ�� �̺�Ʈó��
		btnAhri.setOnAction(event -> {
			handlerBtnChampAction(event);

		});
		// ĳ���� ��Į�� �̹�����ư Ŭ�� �̺�Ʈó��
		btnAkali.setOnAction(event -> {
			handlerBtnAkaliAction(event);
		});
		// ĳ���� �ֽ� �̹�����ư Ŭ�� �̺�Ʈó��
		btnAshe.setOnAction(event -> {
			handlerBtnAsheAction(event);
		});
		// ĳ���� ����Ʋ�� �̹�����ư Ŭ�� �̺�Ʈó��
		btnCaitlyn.setOnAction(event -> {
			handlerBtnCaitlynAction(event);
		});
		// ĳ���� ī�̻� �̹�����ư Ŭ�� �̺�Ʈó��
		btnKaisa.setOnAction(event -> {
			handlerBtnKaisaAction(event);
		});
		// ĳ���� ��þ� �̹�����ư Ŭ�� �̺�Ʈó��
		btnLucian.setOnAction(event -> {
			handlerBtnLucianAction(event);
		});
		// ĳ���� ��� �̹�����ư Ŭ�� �̺�Ʈó��
		btnLulu.setOnAction(event -> {
			handlerBtnLuluAction(event);
		});
		// ĳ���� ���� �̹�����ưŬ�� �̺�Ʈó��
		btnLux.setOnAction(event -> {
			handlerBtnLuxAction(event);
		});
		// ĳ���� ���׿� �̹�����ư Ŭ�� �̺�Ʈó��
		btnPantheon.setOnAction(event -> {
			handlerBtnPantheonAction(event);
		});
		// ĳ���� Ű�Ƴ� �̹�����ư Ŭ�� �̺�Ʈó��
		btnQiyana.setOnAction(event -> {
			handlerBtnQiyanaAction(event);
		});
		// ĳ���� ��ĭ �̹�����ư Ŭ�� �̺�Ʈó��
		btnRakan.setOnAction(event -> {
			handlerBtnRakanAction(event);
		});
		// ĳ���� �ھ� �̹�����ư Ŭ�� �̺�Ʈó��
		btnXayah.setOnAction(event -> {
			handlerBtnXayahAction(event);
		});
		// ĳ���� Ƽ�� �̹�����ư Ŭ�� �̺�Ʈó��
		btnTeemo.setOnAction(event -> {
			handlerBtnTeemoAction(event);
		});
		// ĳ���� �߽��� �̹�����ư Ŭ�� �̺�Ʈó��
		btnYasuo.setOnAction(event -> {
			handlerBtnYasuoAction(event);
		});
		// ĳ���� ���� �̹�����ư Ŭ�� �̺�Ʈó��
		btnYuumi.setOnAction(event -> {
			handlerBtnYuumiAction(event);
		});
		// �ΰ��� �˾�â ����
		skinTableView.setOnMouseClicked(event -> {
			handlerIngameAction(event);
		});
		// �˻����
		btnSearch.setOnAction(event -> {
			handlerbtnSearchAction(event);
		});

	}

	private void handlerPieChartAction() {

		skinDAO = new SkinDAO();
		ArrayList<SkinVO> list2 = skinDAO.pieChartSetting2();

		// ������Ʈ
		try {
			skinPieChart.setData(FXCollections.observableArrayList(
					new PieChart.Data(list2.get(0).getSkinChamp(), list2.get(0).getCount()),
					new PieChart.Data(list2.get(1).getSkinChamp(), list2.get(1).getCount()),
					new PieChart.Data(list2.get(2).getSkinChamp(), list2.get(2).getCount()),
					new PieChart.Data(list2.get(3).getSkinChamp(), list2.get(3).getCount()),
					new PieChart.Data(list2.get(4).getSkinChamp(), list2.get(4).getCount()),
					new PieChart.Data(list2.get(5).getSkinChamp(), list2.get(5).getCount()),
					new PieChart.Data(list2.get(6).getSkinChamp(), list2.get(6).getCount()),
					new PieChart.Data(list2.get(7).getSkinChamp(), list2.get(7).getCount()),
					new PieChart.Data(list2.get(8).getSkinChamp(), list2.get(8).getCount()),
					new PieChart.Data(list2.get(9).getSkinChamp(), list2.get(9).getCount()),
					new PieChart.Data(list2.get(10).getSkinChamp(), list2.get(10).getCount()),
					new PieChart.Data(list2.get(11).getSkinChamp(), list2.get(11).getCount()),
					new PieChart.Data(list2.get(12).getSkinChamp(), list2.get(12).getCount()),
					new PieChart.Data(list2.get(13).getSkinChamp(), list2.get(13).getCount()),
					new PieChart.Data(list2.get(14).getSkinChamp(), list2.get(14).getCount())

			));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void barChartSetting() {

		skinDAO = new SkinDAO();

		XYChart.Series skinCount = new XYChart.Series();
		skinCount.setName("�α� ��Ų TOP 10");

		ObservableList skinList = FXCollections.observableArrayList();
		for (int i = 0; i < skinDAO.getBarChart().size(); i++) {

			try {

				skinList.add(new XYChart.Data(skinDAO.getBarChart().get(i).getSkinChamp(),
						skinDAO.getBarChart().get(i).getCount()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		skinCount.setData(skinList);
		skinBarChart.getData().add(skinCount);

	}

	public void tvVipRanking() {

		data5 = FXCollections.observableArrayList(); // ���̺� ����
		tvVip.setEditable(true);

		TableColumn colUserName = new TableColumn("�̸�");
		colUserName.setPrefWidth(130);
		colUserName.setStyle("-fx-alignmant: CENTER;");
		colUserName.setCellValueFactory(new PropertyValueFactory("skinChamp"));

		TableColumn colSkinSum = new TableColumn("���� �հ�");
		colSkinSum.setPrefWidth(123);
		colSkinSum.setStyle("-fx-alignmant: CENTER;");
		colSkinSum.setCellValueFactory(new PropertyValueFactory("count"));

		tvVip.setItems(data5);
		tvVip.getColumns().addAll(colUserName, colSkinSum);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = null;
		list = skinDAO.skinVipRanking();

		list.get(0).getUserId();

		if (list == null) {
			Function.alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {

			data5.removeAll(data5);
			for (int i = 0; i < list.size(); i++) {
				skinVO = list.get(i);
				data5.add(skinVO);

			}
		}

	}

	public void tvNewSkinSetting() {
		data4 = FXCollections.observableArrayList(); // ���̺� ����
		tvNewSkin.setEditable(true);

		TableColumn colSkinName = new TableColumn("��Ų��");
		colSkinName.setPrefWidth(137);
		colSkinName.setStyle("-fx-alignmant: CENTER;");
		colSkinName.setCellValueFactory(new PropertyValueFactory("skinChamp"));

		TableColumn colSkinPrice = new TableColumn("��Ų ����");
		colSkinPrice.setPrefWidth(80);
		colSkinPrice.setStyle("-fx-alignmant: CENTER;");
		colSkinPrice.setCellValueFactory(new PropertyValueFactory("skinPrice"));
		TableColumn colskinRegister = new TableColumn("�����");
		colskinRegister.setPrefWidth(60);
		colskinRegister.setStyle("-fx-alignmant: CENTER;");
		colskinRegister.setCellValueFactory(new PropertyValueFactory("skinRegister"));

		tvNewSkin.setItems(data4);
		tvNewSkin.getColumns().addAll(colSkinName, colSkinPrice, colskinRegister);

		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = null;
		list = skinDAO.getNewSkin(skinVO);

		if (list == null) {
			Function.alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {

			for (int i = 0; i < list.size(); i++) {
				skinVO = list.get(i);
				data4.add(skinVO);

			}
		}

	}

	// �˻����
	public void handlerbtnSearchAction(ActionEvent event) {
		try {
			ArrayList<SkinVO> list = new ArrayList<SkinVO>();
			SkinDAO skinDAO = new SkinDAO();
			list = skinDAO.getSkinCheck2(txtSearch.getText());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data2.removeAll(data2);
			for (SkinVO svo : list) {
				data2.add(svo);
			}

		} catch (Exception e) {
			Function.alertDisplay(1, "�˻� ���", "�̸��˻�����", e.toString());
		}

	}

	public void handlerIngameAction(MouseEvent event) {
		try {

			if (event.getClickCount() != 2) {

				return;
			}

			Parent ingameRoot = FXMLLoader.load(getClass().getResource("/view/skinimage.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("��Ų �̹��� â");
			ImageView skinImageView2 = (ImageView) ingameRoot.lookup("#skinImage2");
			Button skinBuy = (Button) ingameRoot.lookup("#btnBuy");
			Button skinExit = (Button) ingameRoot.lookup("#btnExit");
			CheckBox skinCbPromise = (CheckBox) ingameRoot.lookup("#cbPromise");

//			File file = new File("C:/Skins/*.jpg");
//			String localUrl = file.toURI().toURL().toString();
//			Image image = new Image(localUrl);
//			localUrl = "/skins/ahri_kda prestige edition.jpg";
//
//			localImage = new Image(localUrl, false);
//			System.out.println("����2");
//			skinImageView2.setImage(localImage);

			// skinImageView2.
//
//			if (selectedFile != null) {
//				selectFileName = selectedFile.getName();
//			}
			skinBuy.setOnMouseClicked(MouseEvent -> {
				try {
					if (skinCbPromise.isSelected() == true) {
						skinVO = skinTableView.getSelectionModel().getSelectedItem();
						
						System.out.println(skinVO.getCount()+skinVO.getSkinChamp()+skinVO.getSkinImage1()+skinVO.getSkinImage2()+
								skinVO.getSkinImage3()+skinVO.getSkinName()+skinVO.getSkinPrice()+skinVO.getSkinRegister()+skinVO.getUserId());
						skinDAO = new SkinDAO();
						int buy = skinDAO.getSkinRegiste2(skinVO);
						if (buy != 0) {
							Function.alertDisplay(5, "���� Ȯ��", "���������� ���� �Ǿ����ϴ�.", "�������ּż� �����մϴ�.");
						} else {

							Function.alertDisplay(1, "�ߺ� ����", "�̹� �����ϰ� �ִ� ��Ų�Դϴ�.", "���� ����");
						}
					} else {
						Function.alertDisplay(1, "��� ���� Ȯ��", "����� �������ּ���!", "check!");
					}
				} catch (Exception e1) {
					Function.alertDisplay(1, "����", "�����ͺ��̽� ��Ͽ���", "��� ����");
				}

			});
			skinExit.setOnAction(e -> {
				stage.close();
			});

			String imageFile = SkinDAO.getSelectSkin(skinTableView.getSelectionModel().getSelectedItem().getSkinName());
			localImage = new Image("/Skins/" + imageFile, false);
			skinImageView2.setImage(localImage);
			Scene scene = new Scene(ingameRoot);
			stage.setScene(scene);
			stage.show();
			// ImageView imageBuyMain = (ImageView) ingameRoot.lookup("#skinImage2");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void skinTableViewSetting() {
		data2 = FXCollections.observableArrayList(); // ���̺� ����
		skinTableView.setEditable(true);

		// TableView<Fish> tableView = new
		// TableView<>(FXCollections.observableList(fish));

		TableColumn<SkinVO, Image> colImage = new TableColumn("�Ϸ���Ʈ");
		colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
		colImage.setPrefWidth(200);
		colImage.setCellFactory(param -> new CustomTableCell<>());

		TableColumn<SkinVO, String> colName = new TableColumn("��Ų��");
		colName.setPrefWidth(280);
		colName.setStyle("-fx-alignment: CENTER;");
		colName.setCellValueFactory(new PropertyValueFactory<>("skinName"));

		TableColumn<SkinVO, String> colPrice = new TableColumn("����(��)");
		colPrice.setPrefWidth(100);
		colPrice.setStyle("-fx-alignment: CENTER;");
		colPrice.setCellValueFactory(new PropertyValueFactory<>("skinPrice"));

		TableColumn<SkinVO, String> colRegister = new TableColumn("�����");
		colRegister.setPrefWidth(160);
		colRegister.setStyle("-fx-alignment: CENTER;");
		colRegister.setCellValueFactory(new PropertyValueFactory<>("skinRegister"));

//		TableColumn<SkinVO, String> colUserId = new TableColumn("������");
//		colUserId.setPrefWidth(1);
//		colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

		// ���̺� ������ �߰�
		skinTableView.getColumns().addAll(colImage, colName, colPrice, colRegister);
		skinTableView.setItems(data2);

	}

	public void handlerbtnMyRoomAction(ActionEvent event) {

		try {
			Parent editRoot = FXMLLoader.load(getClass().getResource("/view/myroom.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMyRoom.getScene().getWindow());
			stageDialog.setTitle("My Room");
			TableView<SaleVO> mrCharge = (TableView) editRoot.lookup("#tvCharge");
			TableView<SaleVO> mrPrice = (TableView) editRoot.lookup("#tvPrice");
			TextField mrJoinId = (TextField) editRoot.lookup("#txtJoinId");
			TextField mrJoinPass1 = (TextField) editRoot.lookup("#txtJoinPass1");
			TextField mrJoinPass2 = (TextField) editRoot.lookup("#txtJoinPass2");
			Button mrJoinCancel = (Button) editRoot.lookup("#btnJoinCancel");
			Button mrJoinOk = (Button) editRoot.lookup("#btnJoinOk");
			TextField mrJoinPhone = (TextField) editRoot.lookup("#txtJoinPhone");
			RadioButton mrradioJoinF = (RadioButton) editRoot.lookup("#radioJoinF");
			RadioButton mrradioJoinM = (RadioButton) editRoot.lookup("#radioJoinM");
			TextField mrJoinEmail = (TextField) editRoot.lookup("#txtJoinEmail");
			TextField mrJoinName = (TextField) editRoot.lookup("#txtJoinName");
			data3 = FXCollections.observableArrayList(); // ���̺� ����
			mrPrice.setEditable(true);
			// TableView<Fish> tableView = new
			// TableView<>(FXCollections.observableList(fish));

			TableColumn colSkin = new TableColumn("������");
			colSkin.setMaxWidth(160);
			colSkin.setStyle("-fx-alignmant: CENTER;");
			colSkin.setCellValueFactory(new PropertyValueFactory("saleDate"));

			TableColumn colDate = new TableColumn("��Ų�̸�");
			colDate.setMaxWidth(160);
			colDate.setStyle("-fx-alignmant: CENTER;");
			colDate.setCellValueFactory(new PropertyValueFactory("skinName"));

			mrPrice.setItems(data3);
			mrPrice.getColumns().addAll(colSkin, colDate);

			ArrayList<SaleVO> list = null;
			saleDAO = new SaleDAO();
			SaleVO saleVO = null;
			list = saleDAO.getBuyList(LoginController.userId.toString());

			if (list == null) {
				Function.alertDisplay(1, "���", "DB �������� ����", "���˿��");
				return;

			} else {

				for (int i = 0; i < list.size(); i++) {
					saleVO = list.get(i);
					data3.add(saleVO);

				}
			}
			// ȸ������ ���� ó��

			ArrayList<UserVO> userEdit = null;
			userDAO = new UserDAO();
			try {
				userEdit = userDAO.searchUserEdit(LoginController.userId);
				if (userEdit == null) {
					Function.alertDisplay(1, "�����ͺ��̽� ����", "�����ͺ��̽� ���޿���", "Ȯ�ο��");
					return;
				} else {
					System.out.println(userEdit.get(0).getUserId());
					mrJoinId.setText(userEdit.get(0).getUserId());
					mrJoinPass1.setText(userEdit.get(0).getUserPassword());
					mrJoinPass2.setText(userEdit.get(0).getUserPassword());
					mrJoinName.setText(userEdit.get(0).getUserName());
					mrJoinEmail.setText(userEdit.get(0).getUserEmail());
					if (userEdit.get(0).getUserGender().equals("����")) {
						mrradioJoinM.setSelected(true);
					} else {
						mrradioJoinF.setSelected(true);
					}
					mrJoinPhone.setText(userEdit.get(0).getUserPhone());
					mrJoinId.setDisable(true);
					mrJoinName.setDisable(true);
					mrradioJoinF.setDisable(true);
					mrradioJoinM.setDisable(true);
					mrJoinEmail.setDisable(true);
				}
				mrJoinOk.setOnMouseClicked(event2 -> {
					data1 = FXCollections.observableArrayList();
					data1.removeAll(data1);
					data1.add(userVO);
					Function.alertDisplay(1, "�����Ϸ�", "���������� ���� �Ǿ����ϴ�.", "����.");
					Stage stage = (Stage) mrJoinOk.getScene().getWindow();
					stage.close();
				});

			} catch (ClassNotFoundException e) {
				Function.alertDisplay(1, "���� ����", "������ ���� Ȯ���ϼ���", "�Է� ����" + e.getMessage());

			}
			mrJoinCancel.setOnAction(event2 -> {
				stageDialog.close();
			});
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();

		} catch (IOException e) {
			Function.alertDisplay(1, "���� ����", "������ ���� Ȯ���ϼ���", "�Է� ����" + e.getMessage());
		}

	}

	public void handlerChargeAction(ActionEvent event) {

		try {
			Parent editRoot = FXMLLoader.load(getClass().getResource("/view/charge.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMyRoom.getScene().getWindow());
			stageDialog.setTitle("�����ϱ�");
			TextField chChargeMoney = (TextField) editRoot.lookup("#ChargeMoney");
			Label chlbMoney = (Label) editRoot.lookup("#lbMoney");
			TextField chtxtTotal = (TextField) editRoot.lookup("#txtTotal");
			Label chlbCaptcha = (Label) editRoot.lookup("#lbCaptcha");
			Button chbtnCaptcha = (Button) editRoot.lookup("#btnCaptcha");
			TextField chtxtCaptcha = (TextField) editRoot.lookup("#txtCaptcha");
			ImageView chimageCaptcha = (ImageView) editRoot.lookup("#imageCaptcha");
			Button chbthOk = (Button) editRoot.lookup("#bthOk");
			Button chbtnCancel = (Button) editRoot.lookup("#btnCancel");

			chbtnCancel.setOnAction(event1 -> {
				stageDialog.close();
			});

			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();

		} catch (IOException e) {
			Function.alertDisplay(1, "���� ����", "������ ���� Ȯ���ϼ���", "�Է� ����" + e.getMessage());
		}

	}

	public void handlerBtnYuumiAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("����");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnYasuoAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("�߽���");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnTeemoAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("Ƽ��");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnXayahAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("�ھ�");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnRakanAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("��ĭ");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnQiyanaAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("Ű�Ƴ�");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnPantheonAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("���׿�");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnLuxAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("����");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnLuluAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("���");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnLucianAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("��þ�");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnKaisaAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("ī�̻�");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnCaitlynAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("����Ʋ��");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnAsheAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("�ֽ�");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnAkaliAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("��Į��");
		list = skinDAO.getSkinSelect(skinVO);
		// TableViewEventPress();
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

	public void handlerBtnChampAction(ActionEvent event) {
		data2.removeAll(data2);
		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = new SkinVO("�Ƹ�");
		list = skinDAO.getSkinSelect(skinVO);
		
		// TableViewEventPress();
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

}
