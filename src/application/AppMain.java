package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	
	public void start(Stage primaryStage) throws Exception {
		
		
		Parent hBox = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
	
		Scene scene= new Scene(hBox);
	
		//���� �ܺ� ��Ÿ���� ����ִ´�
		//scene.getStylesheets().add(getClass().getResource("app.css").toString());
		
		primaryStage.setScene(scene);  //�� ������ scene�� primaryStage�� ��ġ��
		primaryStage.setTitle("�α��� ������");
		primaryStage.setResizable(true);
		primaryStage.show();
	}

}
