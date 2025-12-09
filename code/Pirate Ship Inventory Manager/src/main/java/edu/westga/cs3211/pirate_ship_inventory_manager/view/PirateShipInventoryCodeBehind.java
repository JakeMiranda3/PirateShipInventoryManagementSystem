package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import java.io.IOException;

import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.PirateShipInventoryViewModel;
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

	@FXML
	private Button viewStockChanges;
	
    @FXML
    private Button viewInventory;
    
    @FXML
    private Button viewPantry;
	private PirateShipInventoryViewModel viewModel;

	/**
	 * Instantiates a new pirate ship inventory code behind.
	 */
	public PirateShipInventoryCodeBehind() {
		this.viewModel = new PirateShipInventoryViewModel();
	}

	@FXML
	private void initialize() {
		this.viewStockChanges.visibleProperty().bind(this.viewModel.isQuarterMasterProperty());
		this.viewInventory.visibleProperty().bind(this.viewModel.isQuarterMasterProperty());
		this.viewPantry.visibleProperty().bind(this.viewModel.isChefProperty());
	}
	
	@FXML
	private void handleViewPantry() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPantryPage.fxml"));
			Parent addStockpage = loader.load();
			Scene scene = new Scene(addStockpage);

			Stage stage = (Stage) this.rootPane.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException error) {
			error.printStackTrace();
		}
	}
	
	@FXML
	private void handleViewStockChanges() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStockChangesPage.fxml"));
			Parent addStockpage = loader.load();
			Scene scene = new Scene(addStockpage);

			Stage stage = (Stage) this.rootPane.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException error) {
			error.printStackTrace();
		}

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
	
    @FXML
    private void handleViewInventory() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewInventoryPage.fxml"));
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
