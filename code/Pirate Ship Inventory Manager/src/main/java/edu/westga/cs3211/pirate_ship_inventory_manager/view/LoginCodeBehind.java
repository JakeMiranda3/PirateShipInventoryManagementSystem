package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import java.io.IOException;

import edu.westga.cs3211.pirate_ship_inventory_manager.Main;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The Class CodeBehind.
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class LoginCodeBehind {

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private Button submitButton;

	@FXML
	private Label greetingLabel;

	@FXML
	private Label errorLabel;

	private LoginViewModel viewModel;

	/**
	 * Instantiates a new greeting code behind.
	 * 
	 * @precondition none
	 * @precondition none
	 */
	public LoginCodeBehind() {
		this.viewModel = new LoginViewModel();
	}

	@FXML
	void initialize() {

		this.bindComponentsToViewModel();
	}

	private void bindComponentsToViewModel() {
		this.nameTextField.textProperty().bindBidirectional(this.viewModel.nameProperty());
		this.passwordTextField.textProperty().bindBidirectional(this.viewModel.passwordProperty());

		this.submitButton.disableProperty()
				.bind(this.nameTextField.textProperty().isEmpty().or(this.passwordTextField.textProperty().isEmpty()));

		this.errorLabel.textProperty().bind(this.viewModel.errorMessageProperty());
	}

	@FXML
	void handleSubmit() {
		if (this.viewModel.tryLogin()) {
			try {
				Main.setRoot(Main.PIRATESHIP_INVENTORY_GUI_FXML);
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
		this.viewModel.clearTextFields();
	}

}
