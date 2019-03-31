package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.*;

public class LectureFichierTest {

	@Test
	public void testTaxesZero() {
		assertEquals(0, LectureFichier.CalculTaxe(0), 0);
	}
	
	@Test
	public void testTaxes() {
		assertEquals(11.5, LectureFichier.CalculTaxe(10), 0);
	}

}
