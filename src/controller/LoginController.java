package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserVO;

public class LoginController implements Initializable {

	@FXML
	private TextField txtLoginId;
	@FXML
	private PasswordField txtLoginPass;
	@FXML
	private Button btnLoginOk;
	@FXML
	private Button btnLoginJoin;
	@FXML
	private Button btnLoginFindInfo;

	private UserVO userVO;
	private UserDAO userDAO;
	static public String userId;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 로그인버튼 이벤트처리
		btnLoginOk.setOnAction(e -> {
			handlerbtnLoginOkAction(e);
		});
		// 회원가입버튼 이벤트처리
		btnLoginJoin.setOnAction(e -> {
			handlerbtnLoginJoinAction(e);
		});

		// 아이디/밀번호찾기 이벤트처리
		btnLoginFindInfo.setOnAction(e -> {
			handlerbtnLoginFindInfoAction(e);
		});

		// 테스트용
		txtLoginId.setText("cm1");
		txtLoginPass.setText("123456");

	}

	// 로그인버튼 이벤트처리
	public void handlerbtnLoginOkAction(ActionEvent event) {
		// 1.아이디와 패스워드가 입력안되었을때 경고창을 준다
		if (txtLoginId.getText().equals("") || txtLoginPass.getText().equals("")) {
			alertDisplay(5, "로그인실패", "아이디 패스워드 미입력", "다시 입력하세요");
		}

		String uId = txtLoginId.getText();
		String tPW = txtLoginPass.getText();
		userId = txtLoginId.getText();

		int existID = UserDAO.checkUserId(uId);
		int existPW = 0;

		if (existID == 0) {
			Function.callAlert("[LOGIN 실패]: 존재하지 않는 아이디 입니다.");
			return;
		} else {
			existPW = UserDAO.checkPW(uId, tPW);
		}

		if (existPW == 0) {
			return;
		} else {
			existPW = UserDAO.checkPW(uId, tPW);
		}
		if (!userId.equals("admin")) {
			
			Parent mainView = null;
			Stage mainStage = null;

			try {
				
		
				try {
				mainView = FXMLLoader.load(getClass().getResource("/view/root.fxml"));
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				Scene scene = new Scene(mainView);
				mainStage = new Stage();
				

				mainStage.setTitle("사용자");
				mainStage.setScene(scene); // 이 순간에 scene과 primaryStage가 매치됨

				mainStage.setResizable(false);

				((Stage) btnLoginOk.getScene().getWindow()).close();
				mainStage.show();

			} catch (Exception e) {
				Function.callAlert("화면 전환 오류 : 화면 전환에 오류 발생");
			}
		} else {

			Parent mainView = null;
			Stage mainStage = null;

			try {

				mainView = FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();

				mainStage.setTitle("관리자페이지");
				mainStage.setScene(scene); // 이 순간에 scene과 primaryStage가 매치됨

				mainStage.setResizable(false);

				((Stage) btnLoginOk.getScene().getWindow()).close();
				mainStage.show();

			} catch (Exception e) {
				Function.callAlert("화면 전환 오류 : 화면 전환에 오류 발생");
			}

		}

	}

	// 회원가입 이벤트처리
	public void handlerbtnLoginJoinAction(ActionEvent event) {

		Parent mainView = null;
		Stage mainStage = null;

		try {

			mainView = FXMLLoader.load(getClass().getResource("/view/join.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("회원가입");
			mainStage.setScene(scene); // 이 순간에 scene과 primaryStage가 매치됨

			mainStage.setResizable(false);

			// 현재의 스테이지를 닫고 새로운 창을 연다

			TextField txtJoinId = (TextField) mainView.lookup("#txtJoinId");
			PasswordField txtJoinPass1 = (PasswordField) mainView.lookup("#txtJoinPass1");
			PasswordField txtJoinPass2 = (PasswordField) mainView.lookup("#txtJoinPass2");
			TextField txtJoinName = (TextField) mainView.lookup("#txtJoinName");
			TextField txtJoinEmail = (TextField) mainView.lookup("#txtJoinEmail");
			RadioButton radioJoinM = (RadioButton) mainView.lookup("#radioJoinM");
			RadioButton radioJoinF = (RadioButton) mainView.lookup("#radioJoinF");
			TextField txtJoinPhone = (TextField) mainView.lookup("#txtJoinPhone");
			Button btnJoinOk = (Button) mainView.lookup("#btnJoinOk");
			Button btnJoinCancle = (Button) mainView.lookup("#btnJoinCancle");

			// 테스트를 위한 셋팅
			txtJoinId.setText("hello");
			txtJoinPass1.setText("123456");
			txtJoinPass2.setText("123456");
			txtJoinName.setText("관리자");
			radioJoinM.setSelected(true);
			txtJoinEmail.setText("admon@naver.com");
			txtJoinPhone.setText("010-1234-5678");

			btnJoinOk.setOnAction((e) -> {
				int count1 = UserDAO.checkUserId(txtJoinId.getText().toString());
				int count2 = userDAO.checkUserEmail(txtJoinEmail.getText().toString());

				if (txtJoinId.getText().equals("") || txtJoinPass1.getText().equals("")
						|| txtJoinPass2.getText().equals("") || txtJoinName.getText().equals("")
						|| txtJoinEmail.getText().equals("") || txtJoinPhone.getText().equals("")) {
					Function.callAlert("[입력오류 ] : 모든 필드를 체워주세요 ");
				} else if (count1 != 0) {
					Function.callAlert("[아아디 중복] : 존재하는 아이디입니다. ");
				} else if (!txtJoinPass1.getText().equals(txtJoinPass2.getText())) {
					Function.callAlert("[비밀번호 오류] : 비밀번호가 같지 않습니다 ");
				} else if (txtJoinEmail.getText().contains("@") == false) {
					Function.callAlert("[이메일 형식오류] : 이메일 형식을 지켜주세요.");
				} else if (count2 != 0) {
					Function.callAlert("[이메일 중복] : 존재하는 이메일입니다");

				} else {
					String gender = "";
					if (radioJoinM.isSelected()) {
						gender = radioJoinM.getText();
					} else if (radioJoinF.isSelected()) {
						gender = radioJoinF.getText();
					}
					userVO = new UserVO(txtJoinId.getText(), txtJoinPass1.getText(), txtJoinName.getText(),
							txtJoinEmail.getText(), gender, txtJoinPhone.getText());

					userDAO = new UserDAO(); // 객체를 부른다.

					int count3 = userDAO.getUserRegiste(userVO);
					if (count3 != 0) {
						Function.callAlert("[가입 성공] : 회원이 되신것을 환영합니다");
					}

					((Stage) btnJoinOk.getScene().getWindow()).close();

				}

			});

			btnJoinCancle.setOnAction((e) -> {
				Platform.exit();
			});

			mainStage.show();

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

	// 비밀번호찾기 이벤트처리
	public void handlerbtnLoginFindInfoAction(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;

		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/find_pass.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("회원정보 찾기");
			mainStage.setScene(scene); // 이 순간에 scene과 primaryStage가 매치됨

			mainStage.setResizable(false);
			// 현재의 스테이지를 닫고 새로운 창을 연다

			TextField txtFindName = (TextField) mainView.lookup("#txtFindName");
			TextField txtFindEmail = (TextField) mainView.lookup("#txtFindEmail");
			Button btnFindOk = (Button) mainView.lookup("#btnFindOk");
			Button btnFindCancel = (Button) mainView.lookup("#btnFindCancel");

			mainStage.show();

			btnFindOk.setOnAction((e) -> {

				String id = txtFindName.getText();
				String email = txtFindEmail.getText();
				String pass = UserDAO.findPass(id, email);
				if (pass != null) {
					Function.alertDisplay(5, "회원정보 찾기", "회원님의 비밀번호는 ", pass + "입니다");
				} else {
					Function.alertDisplay(5, "회원정보찾기", "비밀번호 찾기실패", "정보를 다시 입력해주세요");
				}

			});

			btnFindCancel.setOnAction((e) -> {
				((Stage) btnFindCancel.getScene().getWindow()).close();

			});

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 경고창 처리하는 함수
	private void alertDisplay(int type, String title, String headerText, String ContentText) {
		Alert alert = null;
		switch (type) {
		case 1:
			alert = new Alert(AlertType.WARNING);
			break;
		case 2:
			alert = new Alert(AlertType.CONFIRMATION);
			break;
		case 3:
			alert = new Alert(AlertType.ERROR);
			break;
		case 4:
			alert = new Alert(AlertType.NONE);
			break;
		case 5:
			alert = new Alert(AlertType.INFORMATION);
			break;

		}
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(headerText + "\n" + ContentText);
		alert.setResizable(false);
		alert.show();

	}

}
