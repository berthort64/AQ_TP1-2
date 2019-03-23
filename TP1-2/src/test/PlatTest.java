package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Plat;

public class PlatTest {

	private Plat plat;

	@Before
	public void AvantChaqueTest() {

		plat = new Plat("Hot dog", 5.5);

	}

	@After
	public void ApresChaqueTest() {

		plat = null;

	}
	
	@Test
	public void TestGetPrix() {
		
		assertEquals(5.5,plat.getPrix(),0.01);
		
	}
	
	@Test
	public void TestGetPrixNull() {
		
		plat=new Plat("",0);
		
		assertEquals(0,plat.getPrix(),0.01);
		
	}
	
	@Test
	public void TestGetPrixNegatif() {
		
		plat=new Plat("Hot dog",-5.5);
		
		assertEquals(-5.5,plat.getPrix(),0.01);
		
	}
	
	
	@Test
	public void TestGetProduit() {
		
		assertEquals("Hot dog",plat.getProduit());
		
	}
	
	@Test(expected=NullPointerException.class)
	public void TestGetProduitNull() {
		
		plat=new Plat(null,0);
		
		assertNull(plat.getProduit());
		
	}
	
	
	

}
