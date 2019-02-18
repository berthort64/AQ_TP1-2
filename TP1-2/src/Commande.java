public class Commande {

private String nomClient;
private int quantite;
private String produit;

	public Commande(String nomClient,int quantite,String produit){
	
		this.nomClient=nomClient;
		this.quantite=quantite;
		this.produit=produit;
		
	}

	public String getNomClient(){
		
		return this.nomClient;
		
	}
	
	public int getQuantite(){
		
		return this.quantite;
		
	}
	
	public String getProduit(){
		
		return this.produit;
		
	}

}