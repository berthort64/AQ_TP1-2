package main;
public class Plat {

	private String produit;
	private double prix;

	public Plat(String produit, double prix) {

		this.produit = produit;
		this.prix = prix;

	}

	public String getProduit() {

		return this.produit;

	}

	public double getPrix() {

		return this.prix;

	}

}