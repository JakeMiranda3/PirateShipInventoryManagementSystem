package viewmodel.login;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Role;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.LoginViewModel;

public class TestLoginViewModel {

	@Test
	public void testConstructorInitializesProperties() {
		LoginViewModel viewModel = new LoginViewModel();

		assertEquals("", viewModel.nameProperty().get(), "Checks name property");
		assertEquals("", viewModel.passwordProperty().get(), "Checks password property");
		assertEquals("", viewModel.errorMessageProperty().get(), "Checks error message property");
		assertFalse(viewModel.isSubmitEnabled(), "Checks if Button is enabled or disabled");
	}

	@Test
	public void testIsSubmitEnabledWhenFieldsEmpty() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("");
		viewModel.passwordProperty().set("");

		assertFalse(viewModel.isSubmitEnabled(), "Checks if Button is enabled or disabled");
	}

	@Test
	public void testIsSubmitEnabledWhenOnlyUsernameFilled() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("John");
		viewModel.passwordProperty().set("");

		assertFalse(viewModel.isSubmitEnabled(), "Checks if Button is enabled or disabled");
	}

	@Test
	public void testIsSubmitEnabledWhenOnlyPasswordFilled() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("");
		viewModel.passwordProperty().set("Doe");

		assertFalse(viewModel.isSubmitEnabled(), "Checks if Button is enabled or disabled");
	}

	@Test
	public void testIsSubmitEnabledWhenFieldsFilled() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("John");
		viewModel.passwordProperty().set("Doe");

		assertTrue(viewModel.isSubmitEnabled(), "Checks if Button is enabled or disabled");
	}

	@Test
	public void testTryLoginSucceedsWithValidCrewMateCredentials() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("John");
		viewModel.passwordProperty().set("Doe");

		boolean result = viewModel.tryLogin();

		assertTrue(result, "Checks if user is authenticated when trying to login");
		assertEquals("", viewModel.errorMessageProperty().get(), "Checks error message property");
		assertNotNull(Session.getCurrentUser(), "Checks the status of the user object");
		assertEquals("John", Session.getCurrentUser().getName(), "Checks the name of the user");
		assertTrue(Session.getCurrentUser().getRoles().contains(Role.CREWMATE),
				"Checks if the user has the crewmate role");
		assertEquals(4, Session.getInventory().getCompartments().size(),
				"Ensures current session has the default 4 compartments in inventory");
	}

	@Test
	public void testTryLoginSucceedsWithValidQuartermasterCredentials() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("Jane");
		viewModel.passwordProperty().set("Doe");

		boolean result = viewModel.tryLogin();

		assertTrue(result, "Checks if user is authenticated when trying to login");
		assertEquals("", viewModel.errorMessageProperty().get(), "Checks error message property");
		assertNotNull(Session.getCurrentUser(), "Checks the status of the user object");
		assertEquals("Jane", Session.getCurrentUser().getName(), "Checks the name of the user");
		assertEquals(2, Session.getCurrentUser().getRoles().size(), "Checks the amount of roles the user has");
		assertTrue(Session.getCurrentUser().getRoles().contains(Role.CREWMATE),
				"Checks if the user has the crewmate role");
		assertTrue(Session.getCurrentUser().getRoles().contains(Role.QUARTERMASTER),
				"Checks if the user has Quartermaster role");
		assertEquals(4, Session.getInventory().getCompartments().size(),
				"Ensures current session has the default 4 compartments in inventory");

	}

	@Test
	public void testTryLoginFailsWithInvalidCredentials() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("A");
		viewModel.passwordProperty().set("B");

		boolean result = viewModel.tryLogin();

		assertFalse(result, "Checks if user is an authenticated");
		assertEquals("Invalid username or password", viewModel.errorMessageProperty().get());
		assertNull(Session.getCurrentUser(), "Session user should not be set for invalid login");
	}

	@Test
	public void testClearTextFieldsResetsFields() {
		LoginViewModel viewModel = new LoginViewModel();

		viewModel.nameProperty().set("Jane");
		viewModel.passwordProperty().set("Doe");

		viewModel.clearTextFields();

		assertEquals("", viewModel.nameProperty().get(), "Checks the name property after calling clear");
		assertEquals("", viewModel.passwordProperty().get(), "Checks the password property after calling clear");
	}

}
