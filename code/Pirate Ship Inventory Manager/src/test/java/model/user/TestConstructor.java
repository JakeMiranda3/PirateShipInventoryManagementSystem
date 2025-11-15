package model.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;

public class TestConstructor {

	@Test
	public void testWhenNameIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			new User("", "password");
		});
	}

	@Test
	public void testWhenNameIsBlank() {
		assertThrows(IllegalArgumentException.class, () -> {
			new User(" ", "password");
		});
	}

	@Test
	public void testWhenNameIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			new User(null, "password");
		});
	}

	@Test
	public void testWhenPasswordIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			new User("Username", "");
		});
	}

	@Test
	public void testWhenPasswordIsBlank() {
		assertThrows(IllegalArgumentException.class, () -> {
			new User("Username", " ");
		});
	}

	@Test
	public void testWhenPasswordIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			new User("Username", null);
		});
	}

	@Test
	public void testValidUser() {
		User user1 = new User("Username", "Password");
		assertEquals("Username", user1.getName(), "Checks username of the user");
		assertEquals("Password", user1.getPassword(), "Checks the password of the user");

	}

}
