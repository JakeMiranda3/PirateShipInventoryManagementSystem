package model.storage.compartment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;

public class TestConstructor {

	@Test
	public void testWhenNameIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Compartment(null, 60, SpecialQuality.NONE);
		});
	}

	@Test
	public void testWhenNameIsBlank() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Compartment(" ", 60, SpecialQuality.NONE);
		});
	}

	@Test
	public void testWhenWhenNameIsEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Compartment("", 60, SpecialQuality.NONE);
		});
	}

	@Test
	public void testWhenMaxcapacityIsLessThanZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Compartment("Regular Compartment", -1, SpecialQuality.NONE);
		});
	}

	@Test
	public void testWhenMaxcapacityIsZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Compartment("Regular Compartment", 0, SpecialQuality.NONE);
		});
	}

	@Test
	public void testWhenSpecialQualityIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Compartment("Regular Compartment", 6, null);
		});
	}

	@Test
	public void testValidCompartment() {
		Compartment compartment = new Compartment("Regular Compartment", 50, SpecialQuality.NONE);

		assertEquals("Regular Compartment", compartment.getCompartmentName(), "Checks name of the compartment");
		assertEquals(50, compartment.getMaxCapacity(), "Checks the max capacity of the compartment");
		assertEquals(SpecialQuality.NONE, compartment.getAllowedQuality(), "Checks the allowed stock quality");
	}

}
