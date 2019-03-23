package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Client;
import main.Commande;
import main.Plat;

public class CommandeTest {

	private Commande commande;
	private Client client;
	private Plat plat;

	@Before
	public void AvantChaqueTest() {

		client = new Client("Patrick");
		plat = new Plat("Frites", 2.5);
		commande = new Commande(client, plat, 2);

	}

	@After
	public void ApresChaqueTest() {

		client = null;
		plat = null;
		commande = null;

	}
	
	@Test
	public void TestCommandeClient(){
		
		Client client=commande.getClient();
		client.ajouterCommande(50);
		String humain=client.getNom()+Double.toString((client.getFacture()));
		
		assertEquals("Patrick50.0",humain);
		
	}
	
	@Test
	public void TestCommandeProduit(){
		
		Plat test=commande.getProduit();
		test.getProduit();
		
		assertEquals("Frites",test.getProduit());
		
	}
	
	@Test
	public void TestCommandeQuantite(){
		
		Plat test=commande.getProduit();
		
		assertEquals(2.5,test.getPrix(),0.01);
		
	}

	@Test
	public void TestCommandeClientVide() {

		commande = new Commande(null, plat, 2);
		assertEquals(null, commande.getClient());

	}

	@Test
	public void TestCommandeProduitVide() {

		commande = new Commande(client, null, 2);
		assertEquals(null, commande.getProduit());
	}

	@Test
	public void TestCommandeQuantite0() {

		commande = new Commande(client, plat, 0);

		assertEquals(0, commande.getQuantite(), 0);

	}

}
