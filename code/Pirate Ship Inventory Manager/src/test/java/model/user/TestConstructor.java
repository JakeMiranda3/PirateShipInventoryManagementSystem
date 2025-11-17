package model.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Role;
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
	public void testValidCrewMateUser() {
		User user1 = new User("Username", "Password");

		assertEquals("Username", user1.getName(), "Checks username of the user");
		assertEquals("Password", user1.getPassword(), "Checks the password of the user");
		assertEquals(1, user1.getRoles().size(), "Checks if the user has the default size of 1 role");
		assertTrue(user1.hasRole(Role.CREWMATE), "Checks if the user has the default crewmate role");

	}

	@Test
	public void testValidQuarterMasterUser() {
		User user1 = new User("Username", "Password");
		user1.addRole(Role.QUARTERMASTER);

		assertEquals("Username", user1.getName(), "Checks username of the user");
		assertEquals("Password", user1.getPassword(), "Checks the password of the user");
		assertEquals(2, user1.getRoles().size(), "Checks if the user has 2 roles");
		assertTrue(user1.hasRole(Role.CREWMATE), "Checks if the user has the crewmate role");
		assertTrue(user1.hasRole(Role.QUARTERMASTER), "Checks if the user has the quartermaster role");
	}

}
