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

	// 회원관리
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

	// 스킨관리
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

	// 통계관리
	@FXML
	private BarChart staBarChart;
	@FXML
	private LineChart staLineChart;

	@FXML
	private TableView imageTest;

	ObservableList<UserVO> data1; // 테이블 뷰에 유저정도보여주기 위해서 저장된 데이터.
	ObservableList<SkinVO> data2; // 테이블 뷰에 스킨정보보여주기 위해서 저장된 데이터.

	public static boolean btnOkFlag;
	public static String userId;

	// 스킨 테이블 뷰를 선택했을때 위치값과 객체 값을 저장할수 있는 전역변수 선언.
	private int selectedIndex;
	private ObservableList<SkinVO> selectSkin;

	private UserVO userVO;
	private SkinVO skinVO;
	private UserDAO userDAO;
	private SkinDAO skinDAO;

	// 이미지선택
	private String selectFileName = ""; // 이미지 파일명
	private String localUrl = ""; // 이미지 파일 경로
	private Image localImage;
	private File selectedFile = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 1-1.테이블뷰 항목셋
		userTableViewSetting();

		// 1-2.처음 관리자 로그인을 했을때 테이블에 모든 유저의 목록을 출력한다
		userTotalListSetting();

		// 1-3-1 아이디검색
		btnUserSearch.setOnAction(e -> {
			handlerbtnUserSearchAction(e);
		});

		// 1.유저목록 새로고침
		btnUserRefresh.setOnAction(e -> {
			userTotalListSetting();
		});

		// 1.유저목록 더블클릭 했을때
		userTableView.setOnMousePressed(e -> {
			handlerUserTableViewPressAction(e);
		});

		// 2-1. 테이블뷰에 스킨 목록을 표시한다
		skinTableViewSetting();

		// 2-2. 스킨테이블에 전체 스킨을 출력한다
		skinTotalListSetting();

		// 2-2-1 스킨검색
		btnSkinSearch.setOnAction(e -> {
			handlerbtnSkinSearchAction(e);
		});

		// 2-2-3 신규스킨등록버튼
		btnSkinAdd.setOnAction(e -> {
			handlerbtnSkinAddAction(e);
		});

		// 2-3. 신규스킨 등록버튼 이벤트처리
		btnSkinOk.setOnAction(e -> {
			handlerbtnSkinOkAction(e);
		});

		// 2.4 스킨 새로고침 이벤트처리
		btnSkinRefresh.setOnAction(e -> {
			data2.removeAll(data2);
			skinTotalListSetting();

		});

		// 2. 스킨삭제버튼
		btnSkinDelete.setOnAction(e -> {
			handlerbtnSkinDeleteAction(e);
		});

		// 2.스킨정보 수정
		btnSkinEdit.setOnAction(e -> {
			handlerbtnSkinEditAction(e);
		});

		// 2.5. 테이블 뷰를 눌렀을때 발생되는 이벤트 처리 기능
		skinTableView.setOnMousePressed(e -> {
			handlerSkinTableViewPressAction(e);
		});

		// 2.6 이미지1 등록버튼
		btnSkinImage1.setOnAction(e -> {
			handlerbtnSkinImage1Action(e);
		});

		// 2.7 이미지2 등록버튼
		btnSkinImage2.setOnAction(e -> {
			handlerbtnSkinImage2Action(e);
		});

		// 2.8취소버튼 이벤트처리
		btnSkinCancel.setOnAction(e -> {
			handlerbtnSkinCancelAction(e);
		});

		// 4.1 바차트로 데이터 출력
		barChartSetting();
		// 4.2 파이차트로 데이터 출력
		lineChartSetting();

		// 0. 처음 테이블 셋팅
		initSetting();

		fieldInitSetting(true, true, true, true, true, true, true, true, true, true);
		btnSkinImage3.setDisable(true);
		txtSkinImage1.setDisable(true);
		txtSkinImage2.setDisable(true);
		txtSkinImage3.setDisable(true);

	}// end of initialized

	// 1.유저목록 마우스로 더블클릭 했을때
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

				mainStage.setTitle("유저정보");
				mainStage.setScene(scene); //

				mainStage.setResizable(false);

				mainStage.show();
			} catch (Exception e1) {
				Function.callAlert("화면 전환 오류 : 화면 전환에 오류 발생");
			}
		}
		return userId;

	}

	// 2.스킨 삭제버튼
	private void handlerbtnSkinDeleteAction(ActionEvent e) {
		try {
			// alertDisplay(2, "삭제 경고", "Delete", "정말 삭제하시겠습니까?");
			SkinDAO studentDAO = new SkinDAO();
			studentDAO.getSkinDelete(selectSkin.get(0).getSkinName());
			data2.removeAll(data2);
			skinTotalListSetting();
		} catch (Exception e1) {
			Function.callAlert("삭제오류");
		}
		// 1.버튼초기화(총점,평균x,초기화,등록x,종료,수정x,삭제x)

	}

	// 1.회원 아이디 검색
	private void handlerbtnUserSearchAction(ActionEvent e) {
		try {
			ArrayList<UserVO> list = new ArrayList<UserVO>();
			UserDAO userDAO = new UserDAO();
			list = userDAO.getUserCheck(txtUserSearch.getText());
			if (list == null) {
				throw new Exception("검색오류");
			}
			data1.removeAll(data1);
			for (UserVO svo : list) {
				data1.add(svo);

			}

		} catch (Exception e1) {
			Function.alertDisplay(1, "검색 결과", "이름검색오류", e.toString());
		}

	}

	// 2. 수정버튼 이벤트처리
	private void handlerbtnSkinEditAction(ActionEvent e) {
		fieldInitSetting(true, true, false, true, false, false, true, true, false, false);
		btnOkFlag = false;

	}

	// 이미지2 등록버튼
	private void handlerbtnSkinImage2Action(ActionEvent e) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnSkinOk.getScene().getWindow());
			if (selectedFile != null) { // 이미지 파일 경로
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

	// 스킨등록 버튼 이벤트처리
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
		cbSkinChamp.setItems(FXCollections.observableArrayList("아리", "아칼리", "애쉬", "케이틀린", "카이사", "루시안", "룰루", "럭스",
				"판테온", "키아나", "라칸", "자야", "티모", "야스오", "유미"));
		cbSkinChamp.setValue("아리");

	}

	// 필드셋팅
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

	// 버튼셋팅

	// 1-1유저 리스트뷰 셋팅
	public void userTableViewSetting() {

		data1 = FXCollections.observableArrayList(); // 테이블 설정
		userTableView.setEditable(false);

		TableColumn colId = new TableColumn("아아디");
		colId.setPrefWidth(120);
		colId.setCellValueFactory(new PropertyValueFactory<>("userId"));

		TableColumn colPass = new TableColumn("비밀번호");
		colPass.setPrefWidth(120);
		colPass.setCellValueFactory(new PropertyValueFactory<>("userPassword"));

		TableColumn colName = new TableColumn("이름");
		colName.setPrefWidth(80);
		colName.setCellValueFactory(new PropertyValueFactory<>("userName"));

		TableColumn colEmail = new TableColumn("이메일");
		colEmail.setPrefWidth(160);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

		TableColumn colGender = new TableColumn("성별");
		colGender.setPrefWidth(60);
		colGender.setCellValueFactory(new PropertyValueFactory<>("userGender"));

		TableColumn colPhone = new TableColumn("전화번호");
		colPhone.setPrefWidth(150);
		colPhone.setCellValueFactory(new PropertyValueFactory<>("userPhone"));

		// 테이블에 데이터 추가
		userTableView.setItems(data1);
		userTableView.getColumns().addAll(colId, colPass, colName, colEmail, colGender, colPhone);

	}

	// 로그인 회원 이름 출력
	public void userTotalListSetting() {
		ArrayList<UserVO> list = null;
		userDAO = new UserDAO();

		list = userDAO.getUserTotal();

		if (list == null) {
			Function.alertDisplay(1, "경고", "DB 가져오기 오류", "점검요망");
			return;

		} else {
			for (int i = 0; i < list.size(); i++) {
				userVO = list.get(i);

				data1.add(userVO);
			}
		}

		lbUserName.setText(userDAO.getLoginName(LoginController.userId));

	}

	// 2-1. 테이블뷰에 스킨 목록을 표시한다
	public void skinTableViewSetting() {
		data2 = FXCollections.observableArrayList(); // 테이블 설정
		skinTableView.setEditable(false);

		TableColumn colName = new TableColumn("스킨명");
		colName.setPrefWidth(240);
		colName.setCellValueFactory(new PropertyValueFactory<>("skinName"));

		TableColumn colChamp = new TableColumn("챔피언");
		colChamp.setPrefWidth(70);
		colChamp.setCellValueFactory(new PropertyValueFactory<>("skinChamp"));

		TableColumn colPrice = new TableColumn("가격");
		colPrice.setPrefWidth(70);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("skinPrice"));

		TableColumn colRegister = new TableColumn("출시일");
		colRegister.setPrefWidth(150);
		colRegister.setCellValueFactory(new PropertyValueFactory<>("skinRegister"));

		TableColumn colImage1 = new TableColumn("이미지1");
		colImage1.setPrefWidth(20);
		colImage1.setCellValueFactory(new PropertyValueFactory<>("skinImage1"));

		TableColumn colImage2 = new TableColumn("이미지2");
		colImage2.setPrefWidth(20);
		colImage2.setCellValueFactory(new PropertyValueFactory<>("skinImage2"));

		TableColumn colImage3 = new TableColumn("이미지3");
		colImage3.setPrefWidth(20);
		colImage3.setCellValueFactory(new PropertyValueFactory<>("skinImage3"));

		// 테이블에 데이터 추가
		skinTableView.setItems(data2);
		skinTableView.getColumns().addAll(colName, colChamp, colPrice, colRegister, colImage1, colImage2, colImage3);

	}

	// 2-2. 스킨테이블에 전체 스킨을 출력한다
	public void skinTotalListSetting() {

		ArrayList<SkinVO> list = null;
		skinDAO = new SkinDAO();
		SkinVO skinVO = null;
		list = skinDAO.getSkinTotal();

		if (list == null) {
			Function.alertDisplay(1, "경고", "DB 가져오기 오류", "점검요망");
			return;

		} else {
			for (int i = 0; i < list.size(); i++) {
				skinVO = list.get(i);
				data2.add(skinVO);

			}
		}
	}

	// 2-3. 신규스킨 등록버튼 이벤트처리
	private void handlerbtnSkinOkAction(ActionEvent e) {

		if (btnOkFlag == true) {

			int count1 = SkinDAO.checkSkinName(txtSkinName.getText().toString());

			if (txtSkinName.getText().equals("") || txtSkinPrice.getText().equals("")
					|| dpSkinRegister.getValue().equals("") || txtSkinImage1.getText().equals("")
					|| txtSkinImage2.getText().equals("")) {
				Function.alertDisplay(1, "입력오류", "필드오류", "모든 필드를 입력해주세요");
				return;
			} else if (count1 != 0) {
				Function.callAlert("[스킨명 중복] : 존재하는 스킨명입니다. ");
			} else {

				skinVO = new SkinVO(txtSkinName.getText(), cbSkinChamp.getSelectionModel().getSelectedItem().toString(),
						Integer.parseInt(txtSkinPrice.getText().trim()), dpSkinRegister.getValue().toString(),
						txtSkinImage1.getText(), txtSkinImage2.getText(), txtSkinImage3.getText());

				skinDAO = new SkinDAO(); // 객체를 부른다.

				int count3 = skinDAO.getSkinRegiste(skinVO);
				if (count3 != 0) {
					Function.callAlert("[스킨등록 성공] : 스킨등록 성공" + "");
				}
			}

		} else {

			skinVO = new SkinVO(Integer.parseInt(txtSkinPrice.getText().trim()), txtSkinImage1.getText(),
					txtSkinImage2.getText(), txtSkinName.getText());

			skinDAO = new SkinDAO(); // 객체를 부른다.

			int count3 = skinDAO.getSkinEdit(skinVO);
			if (count3 != 0) {
				Function.callAlert("[스킨수정 성공] : 스킨수정 성공" + "");
			}
		}

		txtSkinName.clear();
		txtSkinPrice.clear();
		dpSkinRegister.getEditor().clear();
		txtSkinImage1.clear();
		txtSkinImage2.clear();

	}

	// 2-2-1 스킨검색
	private void handlerbtnSkinSearchAction(ActionEvent e) {
		try {
			ArrayList<SkinVO> list = new ArrayList<SkinVO>();
			SkinDAO studentDAO = new SkinDAO();
			list = studentDAO.getSkinCheck(txtSkinSearch.getText());
			if (list == null) {
				throw new Exception("검색오류");
			}
			data2.removeAll(data2);
			for (SkinVO svo : list) {
				data2.add(svo);
			}

		} catch (Exception e1) {
			Function.alertDisplay(1, "검색 결과", "이름검색오류", e.toString());
		}

	}

	// 2.6 이미지 등록버튼
	private void handlerbtnSkinImage1Action(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnSkinOk.getScene().getWindow());
			if (selectedFile != null) { // 이미지 파일 경로
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

	// 4.1 바차트로 데이터 출력
	private void barChartSetting() {

		skinDAO = new SkinDAO();

		// 바 차트에 모든 국어점수를 입력한다.
		XYChart.Series skinCount = new XYChart.Series();
		skinCount.setName("VIP 순위");

		ObservableList skinList = FXCollections.observableArrayList();

		for (int i = 0; i < skinDAO.getSkinSelect().size(); i++) {
			skinList.add(new XYChart.Data(skinDAO.getSkinSelect().get(i).getSkinChamp(),
					skinDAO.getSkinSelect().get(i).getCount()));
		}
		

		skinCount.setData(skinList);
		staBarChart.getData().add(skinCount);
	

	}

	// 4.2 라인차트로 데이터 출력
	private void lineChartSetting() {
		skinDAO = new SkinDAO();

		// 바 차트에 모든 국어점수를 입력한다.
		LineChart.Series skinCount = new LineChart.Series();
		skinCount.setName("월별매출");

		ObservableList skinList = FXCollections.observableArrayList();

		for (int i = 0; i < skinDAO.getSkinSelect2().size(); i++) {

			skinList.add(new XYChart.Data(skinDAO.getSkinSelect2().get(i).getSkinChamp()+"월",
					skinDAO.getSkinSelect2().get(i).getCount()));

			
		}

		skinCount.setData(skinList);
		staLineChart.getData().add(skinCount);

	}

	// 2.5. 테이블 뷰를 눌렀을때 발생되는 이벤트 처리 기능
	private void handlerSkinTableViewPressAction(MouseEvent e) {

		if (e.getClickCount() != 2) {

			selectedIndex = skinTableView.getSelectionModel().getSelectedIndex();
			selectSkin = skinTableView.getSelectionModel().getSelectedItems();

			LocalDate date = null;
			String[] array = selectSkin.get(0).getSkinRegister().split("-");
			dpSkinRegister.setValue(
					date.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2])));

			// 눌렀을때 테이블에 있는 값을 가져와서 데이터 필드에 집어넣는다.
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

				mainStage.setTitle("구매페이지");
				mainStage.setScene(scene); //

				mainStage.setResizable(false);

				mainStage.show();
			} catch (Exception e1) {
				Function.callAlert("화면 전환 오류 : 화면 전환에 오류 발생");
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
