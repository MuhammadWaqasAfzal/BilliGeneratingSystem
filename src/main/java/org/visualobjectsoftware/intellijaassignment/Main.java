package org.visualobjectsoftware.intellijaassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {
	
	 public static Stage primaryStage;

	    public static void main(String[] args) {
			launch(args);
		}

		public static void setScene(String fxml,int maxWidth,int maxHeight,int minWidth, int minHeight) {
			try {
				URL resource = Main.class.getResource(fxml);
				if (resource == null) {
					System.err.println("FXML file not found: " + fxml);
					return;
				}
				Parent root = FXMLLoader.load(Main.class.getResource(fxml));


				primaryStage.setTitle("Bill Management System");

				Scene scene = new Scene(root, maxWidth, maxHeight);

				Screen screen = Screen.getPrimary();



				double screenWidth = screen.getBounds().getWidth();
				double screenHeight = screen.getBounds().getHeight();
				primaryStage.setX((screenWidth /4 - 300));
				primaryStage.setY((screenHeight/4  - 200));



				primaryStage.setScene(scene);
				
			    primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

	    this.primaryStage = primaryStage;
	//	setScene("DashboardPage.fxml",600,600,600,600);

		setScene("LoginView.fxml",600,600,600,600);



	}
}
