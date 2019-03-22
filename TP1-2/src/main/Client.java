package main;

public class Client {
	
	private String nom;
	private double facture = 0;
	
	public Client(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
			
			this.nom = nom;
		
	}

	public void ajouterCommande(double prix) {
		this.facture += prix;
	}
	
	public double getFacture() {
		return this.facture;
	}
	
}
