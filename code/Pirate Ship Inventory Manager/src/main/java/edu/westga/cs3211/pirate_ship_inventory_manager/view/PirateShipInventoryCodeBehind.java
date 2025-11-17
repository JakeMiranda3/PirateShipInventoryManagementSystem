package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Pirate Ship Inventory Code Behind
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class PirateShipInventoryCodeBehind {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private Button addStock;

	/**
	 * Instantiates a new pirate ship inventory code behind.
	 */
	public PirateShipInventoryCodeBehind() {

	}

	@FXML
	private void handleAddStock() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStockPage.fxml"));
			Parent addStockpage = loader.load();
			Scene scene = new Scene(addStockpage);

			Stage stage = (Stage) this.rootPane.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException error) {
			error.printStackTrace();
		}
	}

}
