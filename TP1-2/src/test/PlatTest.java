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
	
	@Test(expected=NullPointerException.class)
	public void TestGetPrixNull() {
		
		plat=null;
		
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
		
		plat=null;
		
		assertEquals("Hot dog",plat.getProduit());
		
	}
	
	
	

}
