package model.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;

public class TestToString {

	@Test
	void testToStringFormat() {
		User user = new User("John", "Doe");
		assertEquals("John", user.toString(), "Checks format of toString");
	}

}
