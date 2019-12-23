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

		// �α��ι�ư �̺�Ʈó��
		btnLoginOk.setOnAction(e -> {
			handlerbtnLoginOkAction(e);
		});
		// ȸ�����Թ�ư �̺�Ʈó��
		btnLoginJoin.setOnAction(e -> {
			handlerbtnLoginJoinAction(e);
		});

		// ���̵�/�й�ȣã�� �̺�Ʈó��
		btnLoginFindInfo.setOnAction(e -> {
			handlerbtnLoginFindInfoAction(e);
		});

		// �׽�Ʈ��
		txtLoginId.setText("cm1");
		txtLoginPass.setText("123456");

	}

	// �α��ι�ư �̺�Ʈó��
	public void handlerbtnLoginOkAction(ActionEvent event) {
		// 1.���̵�� �н����尡 �Է¾ȵǾ����� ���â�� �ش�
		if (txtLoginId.getText().equals("") || txtLoginPass.getText().equals("")) {
			alertDisplay(5, "�α��ν���", "���̵� �н����� ���Է�", "�ٽ� �Է��ϼ���");
		}

		String uId = txtLoginId.getText();
		String tPW = txtLoginPass.getText();
		userId = txtLoginId.getText();

		int existID = UserDAO.checkUserId(uId);
		int existPW = 0;

		if (existID == 0) {
			Function.callAlert("[LOGIN ����]: �������� �ʴ� ���̵� �Դϴ�.");
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
				

				mainStage.setTitle("�����");
				mainStage.setScene(scene); // �� ������ scene�� primaryStage�� ��ġ��

				mainStage.setResizable(false);

				((Stage) btnLoginOk.getScene().getWindow()).close();
				mainStage.show();

			} catch (Exception e) {
				Function.callAlert("ȭ�� ��ȯ ���� : ȭ�� ��ȯ�� ���� �߻�");
			}
		} else {

			Parent mainView = null;
			Stage mainStage = null;

			try {

				mainView = FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();

				mainStage.setTitle("������������");
				mainStage.setScene(scene); // �� ������ scene�� primaryStage�� ��ġ��

				mainStage.setResizable(false);

				((Stage) btnLoginOk.getScene().getWindow()).close();
				mainStage.show();

			} catch (Exception e) {
				Function.callAlert("ȭ�� ��ȯ ���� : ȭ�� ��ȯ�� ���� �߻�");
			}

		}

	}

	// ȸ������ �̺�Ʈó��
	public void handlerbtnLoginJoinAction(ActionEvent event) {

		Parent mainView = null;
		Stage mainStage = null;

		try {

			mainView = FXMLLoader.load(getClass().getResource("/view/join.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("ȸ������");
			mainStage.setScene(scene); // �� ������ scene�� primaryStage�� ��ġ��

			mainStage.setResizable(false);

			// ������ ���������� �ݰ� ���ο� â�� ����

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

			// �׽�Ʈ�� ���� ����
			txtJoinId.setText("hello");
			txtJoinPass1.setText("123456");
			txtJoinPass2.setText("123456");
			txtJoinName.setText("������");
			radioJoinM.setSelected(true);
			txtJoinEmail.setText("admon@naver.com");
			txtJoinPhone.setText("010-1234-5678");

			btnJoinOk.setOnAction((e) -> {
				int count1 = UserDAO.checkUserId(txtJoinId.getText().toString());
				int count2 = userDAO.checkUserEmail(txtJoinEmail.getText().toString());

				if (txtJoinId.getText().equals("") || txtJoinPass1.getText().equals("")
						|| txtJoinPass2.getText().equals("") || txtJoinName.getText().equals("")
						|| txtJoinEmail.getText().equals("") || txtJoinPhone.getText().equals("")) {
					Function.callAlert("[�Է¿��� ] : ��� �ʵ带 ü���ּ��� ");
				} else if (count1 != 0) {
					Function.callAlert("[�ƾƵ� �ߺ�] : �����ϴ� ���̵��Դϴ�. ");
				} else if (!txtJoinPass1.getText().equals(txtJoinPass2.getText())) {
					Function.callAlert("[��й�ȣ ����] : ��й�ȣ�� ���� �ʽ��ϴ� ");
				} else if (txtJoinEmail.getText().contains("@") == false) {
					Function.callAlert("[�̸��� ���Ŀ���] : �̸��� ������ �����ּ���.");
				} else if (count2 != 0) {
					Function.callAlert("[�̸��� �ߺ�] : �����ϴ� �̸����Դϴ�");

				} else {
					String gender = "";
					if (radioJoinM.isSelected()) {
						gender = radioJoinM.getText();
					} else if (radioJoinF.isSelected()) {
						gender = radioJoinF.getText();
					}
					userVO = new UserVO(txtJoinId.getText(), txtJoinPass1.getText(), txtJoinName.getText(),
							txtJoinEmail.getText(), gender, txtJoinPhone.getText());

					userDAO = new UserDAO(); // ��ü�� �θ���.

					int count3 = userDAO.getUserRegiste(userVO);
					if (count3 != 0) {
						Function.callAlert("[���� ����] : ȸ���� �ǽŰ��� ȯ���մϴ�");
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

	// ��й�ȣã�� �̺�Ʈó��
	public void handlerbtnLoginFindInfoAction(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;

		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/find_pass.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("ȸ������ ã��");
			mainStage.setScene(scene); // �� ������ scene�� primaryStage�� ��ġ��

			mainStage.setResizable(false);
			// ������ ���������� �ݰ� ���ο� â�� ����

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
					Function.alertDisplay(5, "ȸ������ ã��", "ȸ������ ��й�ȣ�� ", pass + "�Դϴ�");
				} else {
					Function.alertDisplay(5, "ȸ������ã��", "��й�ȣ ã�����", "������ �ٽ� �Է����ּ���");
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

	// ���â ó���ϴ� �Լ�
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
