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
	
		//씬에 외부 스타일을 집어넣는다
		//scene.getStylesheets().add(getClass().getResource("app.css").toString());
		
		primaryStage.setScene(scene);  //이 순간에 scene과 primaryStage가 매치됨
		primaryStage.setTitle("로그인 페이지");
		primaryStage.setResizable(true);
		primaryStage.show();
	}

}
