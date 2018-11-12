package PopupBoxes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox
{
	private static boolean choice;

	public static boolean display(String title, String message)
	{
		Stage confirmBoxStage = new Stage();
		confirmBoxStage.initModality(Modality.APPLICATION_MODAL);
		confirmBoxStage.setTitle(title);

		VBox vBox = new VBox();
		vBox.setStyle(
				//"-fx-background-color: BLACK;" +
				"-fx-alignment: CENTER; " +
				"-fx-pref-height: 125.0;" +
				"-fx-pref-width: 450.0;"
		);

		Label label = new Label(message);
		label.setStyle(
				//"-fx-text-fill: #00eeff;" +
				"-fx-font-family: System Italic;" +
				"-fx-font-size: 25.0;" +
				"-fx-font-wrap-text: true;" +
				"-fx-padding: 12.5;"
		);

		HBox hBox = new HBox();
		hBox.setStyle(
				"-fx-alignment: CENTER; " +
				"-fx-pref-height: 100.0;" +
				"-fx-pref-width: 200.0;" +
				"-fx-spacing: 50;"
		);

		Button yesButton = new Button("Yes");
		yesButton.setStyle(
				"-fx-background-color: #00eeff;" +
				"-fx-font-size: 20.0;" +
				"-fx-pref-width: 100.0;"
		);
		yesButton.setOnAction(e ->
		{
			choice = true;
			confirmBoxStage.close();
		});

		Button noButton = new Button("No");
		noButton.setStyle(
				"-fx-background-color: #00eeff;" +
				"-fx-font-size: 20.0;" +
				"-fx-pref-width: 100.0;"
		);
		noButton.setOnAction(e ->
		{
			choice = false;
			confirmBoxStage.close();
		});

		hBox.getChildren().addAll(yesButton, noButton);
		vBox.getChildren().addAll(label, hBox);

		Scene scene = new Scene(vBox);

		confirmBoxStage.setScene(scene);
		confirmBoxStage.showAndWait();

		return choice;
	}

}
