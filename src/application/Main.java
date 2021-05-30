package application;

import DDBB.Database;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Database connection = new Database();
		System.out.println("Conexion establecida");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Login.fxml"));
		System.out.println("resource obtenido");
		LoginController logincontroller = new LoginController();
		loader.setController(logincontroller);
		System.out.println("seteamos controlador");
		AnchorPane pane = loader.load();
		Scene scene = new Scene(pane);
		scene.getStylesheets().addAll(getClass().getResource("../css/loginregister.css").toExternalForm());
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
		

	}

	public static void main(String[] args) {
		launch(args);
	}
}
