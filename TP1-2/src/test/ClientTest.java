package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.Client;

public class ClientTest {

	private Client client;

	@Before
	public void AvantChaqueTest() {

		client = new Client(null);

	}

	@After
	public void ApresChaqueTest() {

		client = null;
 
	}

	@Test
	public void testGetNom() {

		client.setNom("Patrick");
		assertEquals("Patrick", client.getNom());

	}

	@Test
	public void testGetNomVide() {
		client.setNom(null);

		assertNull(client.getNom());
	}

	@Test
	public void testFactureVide() {

		assertEquals(0.00, client.getFacture(), 0.01);

	}

	@Test
	public void TestAjouterCommande() {

		client.ajouterCommande(40.00);
		client.ajouterCommande(60.00);

		assertEquals(100.00, client.getFacture(), 0.01);

	}

	@Test
	public void TestAjouterCommandeNegatifRestePositif() {

		client.ajouterCommande(30);
		client.ajouterCommande(-20);

		assertEquals(10.00, client.getFacture(), 0.01);

	}

	@Test
	public void TestAjouterCommandeNegatifDevientNegatif() {

		client.ajouterCommande(10);
		client.ajouterCommande(-20);

		assertEquals(-10.00, client.getFacture(), 0.01);

	}

}
