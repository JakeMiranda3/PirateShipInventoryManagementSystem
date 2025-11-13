package edu.westga.cs3211.pirate_ship_inventory_manager;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

/**
 * Main Application class.
 * 
 * @author CS 3211
 * 
 * @version Fall 2025
 */

public class Main extends Application {

	public static final String PIRATESHIP_INVENTORY_GUI_FXML = "view/MainPirateShipInventoryWindow.fxml";
	public static final String MAIN_PIRATESHIP_INVENTORY_WINDOW_TITLE = "Pirate Ship Inventory Management";

	private static final String WINDOW_TITLE = "Greetings";
	private static final String GUI_FXML = "view/hello.fxml";

	private static Stage primaryStage;

	@Override
	public void start(Stage stage) {
		try {
			primaryStage = stage;
			Pane root = this.loadGui(GUI_FXML);
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setTitle(WINDOW_TITLE);
			primaryStage.show();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private Pane loadGui(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlPath));
		return (Pane) loader.load();
	}

	/**
	 * Sets the root.
	 *
	 * @param fxmlPath the new root
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void setRoot(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(fxmlPath));
		Pane pane = (Pane) loader.load();
		primaryStage.getScene().setRoot(pane);

		if (fxmlPath.equals(PIRATESHIP_INVENTORY_GUI_FXML)) {
			primaryStage.setTitle(MAIN_PIRATESHIP_INVENTORY_WINDOW_TITLE);
		} else {
			primaryStage.setTitle(WINDOW_TITLE);
		}
	}

	/**
	 * Entry point for the application
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
