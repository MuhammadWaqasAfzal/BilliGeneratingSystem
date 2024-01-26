package org.visualobjectsoftware.intellijaassignment.utility;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;

public class ConfirmBox {
	static boolean answer;

	public static boolean display(String msg, boolean showTariffDropdown, ObservableList<String> tariffList) {
		Stage stage = new Stage();
		String labelStyle = " -fx-padding: 10px; -fx-font-size: 20px; -fx-font-weight: bold;";

		stage.setTitle("Confirmation Box");
		stage.setMinHeight(350);
		stage.setMinWidth(450);

		Label label = new Label();
		label.setText(msg);
		label.setStyle(labelStyle);



			// Tariff ComboBox
			ComboBox<String> tariffComboBox = new ComboBox<>();
		if(showTariffDropdown) {
			tariffComboBox.setItems(tariffList);
			tariffComboBox.setPromptText("Select alternative tariff:");
		}
			tariffComboBox.setVisible(showTariffDropdown);
		tariffComboBox.setManaged(showTariffDropdown);




		String buttonStyle = " -fx-padding: 10px; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-color: #000075; -fx-text-fill: white;";
		String layoutStyle = "-fx-spacing: 20; -fx-alignment: CENTER;";
		HBox layoutH = new HBox();

		Button buttonYes = new Button("Yes");
		buttonYes.setOnAction(e -> {
			answer = true;
			stage.close();
		});
		buttonYes.setMinWidth(80.0);
		buttonYes.setStyle(buttonStyle);

		Button buttonNo = new Button("No");
		buttonNo.setOnAction(e -> {
			answer = false;
			stage.close();
		});
		buttonNo.setMinWidth(80.0);
		buttonNo.setStyle(buttonStyle);

		layoutH.getChildren().addAll(buttonYes, buttonNo);
		layoutH.setStyle(layoutStyle);

		VBox layout = new VBox();
		layout.getChildren().addAll(label, tariffComboBox, layoutH);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
		stage.showAndWait();
		if(showTariffDropdown){
			Constants.SELECTED_TARIFF = tariffComboBox.getValue();
		}
		return answer;
	}
}
