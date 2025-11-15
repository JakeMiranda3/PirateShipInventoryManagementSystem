package model.authenticator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.model.Authenticator;

class TestVerifyUserCredentials {

	@Test
	public void testAllInvalidCredentials() {
		Authenticator authenticator = new Authenticator();
		boolean result = authenticator.verifyUserCredentials("Username", "Password");
		assertEquals(false, result, "Checks if the user is in the credential set");
	}
	
	@Test
	public void testInvalidUserName() {
		Authenticator authenticator = new Authenticator();
		boolean result = authenticator.verifyUserCredentials("", "Doe");
		assertEquals(false, result, "Checks if the user is in the credential set");
	}
	
	@Test
	public void testInvalidUserPassword() {
		Authenticator authenticator = new Authenticator();
		boolean result = authenticator.verifyUserCredentials("John", "");
		assertEquals(false, result, "Checks if the user is in the credential set");
	}
	
	
	@Test
	public void testValidCredentials() {
		Authenticator authenticator = new Authenticator();
		boolean result = authenticator.verifyUserCredentials("John", "Doe");
		assertEquals(true, result, "Checks if the user is in the credential set");
	}

}
