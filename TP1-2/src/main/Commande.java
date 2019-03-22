package main;

public class Commande {

	private Client client;
	private int quantite;
	private Plat produit;

	public Commande(Client client, Plat produit, int quantite) {

		this.client = client;
		this.quantite = quantite;
		this.produit = produit;

	}

	public Client getClient() {

		return this.client;

	}

	public int getQuantite() {

		return this.quantite;

	}

	public Plat getProduit() {

		return this.produit;

	}

}