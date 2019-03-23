package main;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class LectureFichier {

	private  ArrayList<Client> clients 		= new ArrayList<Client>();
	private  ArrayList<Commande> commandes 	= new ArrayList<Commande>();
	private  ArrayList<Plat> plats 			= new ArrayList<Plat>();
	
	private  int etat = 0;
	private  static String MessageErreur="Erreur dans la lecture du fichier\nFin du programme.";
	private  ArrayList<String> erreurs = new ArrayList<String>();
	
	
	public static void main (String[] args) throws IOException {
		
		LectureFichier lectureFichier=new LectureFichier();
		
		//Nom des fichiers
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Entrez le chemin du fichier de LECTURE : ");
		String fichierL = reader.nextLine();
		
		
		
		reader.close();
		
		boolean Validation=lectureFichier.ValiderFichier(fichierL);
		
		if(Validation){
			
			lectureFichier.executer(fichierL);
			
		}else{
			
			System.out.println(MessageErreur);
			
		}
		
		System.out.println("\nFin du programme.");
		
	}
	
	public String executer(String fichierL) throws IOException {
		
		//Tout r�initialiser
		clients 		= new ArrayList<Client>();
		commandes 		= new ArrayList<Commande>();
		plats 			= new ArrayList<Plat>();
		
		etat 			= 0;
		MessageErreur	="Erreur dans la lecture du fichier\nFin du programme.";
		erreurs 		= new ArrayList<String>();
		
		//Lecture dans le fichier des commandes
		BufferedReader br = new BufferedReader(new FileReader(fichierL));
		
		String ligne;
		
		print("ERREURS :\n");
		
		while ((ligne = br.readLine()) != null) {			
			
			switch(ligne) {
			case "Clients :":
				etat = 1;
				break;
				
			case "Plats :":
				etat = 2;
				break;
				
			case "Commandes :":
				etat = 3;
				break;
				
			case "Fin":
				etat = -1;
				break;
				
			default:
				
				String[] contenu = ligne.split(" ");
				
				switch(etat) {
				case 1:
					
					if (contenu.length == 1) {
						Client client = new Client(contenu[0]);
						clients.add(client);
					} else {
						print('"' + ligne + "\" : Le client ne respecte pas le format demand�\nLe client n'a pas �t� ajout�.\n");
					}
					break;
					
				case 2:
					
					if (contenu.length == 2) {
						
						try {
							Plat plat = new Plat(contenu[0], Double.parseDouble(contenu[1]));
							plats.add(plat);
						} catch(Exception e) {
							
							print('"' + ligne + "\" : Le prix ne respecte pas le format demand�\nLe plat n'a pas �t� ajout�.\n");
						}
						
					} else {
						print('"' + ligne + "\" : Le plat ne respecte pas le format demand�\nLe plat n'a pas �t� ajout�.\n");
					}
					break;
					
				case 3:
					
					if (contenu.length == 3) {
						String nomClient = contenu[0];
						String nomProduit = contenu[1];
						try {
							int qte = Integer.parseInt(contenu[2]);
							
							Client clientCommande = null;
							Plat platCommande = null;
							
							for (Client c : clients) {
								if (c.getNom().equals(nomClient)) {
									clientCommande = c;
									break;
								}
							}
							
							if (clientCommande == null) {
								
								print('"' + ligne + "\" : Le client \"" + nomClient + "\" n'existe pas\nLa commande n'a pas �t� ajout�e.\n");
								break;
								
							}
							
							for (Plat p : plats) {
								if (p.getProduit().equals(nomProduit)) {
									platCommande = p;
									break;
								}
							}
							
							if (platCommande == null) {
								
								print('"' + ligne + "\" : Le plat \"" + nomProduit + "\" n'existe pas\nLa commande n'a pas �t� ajout�e.\n");
								break;
								
							}
							
							Commande commande = new Commande(clientCommande, platCommande, qte);
							commandes.add(commande);
							
							clientCommande.ajouterCommande(qte * platCommande.getPrix());
						} catch(Exception e) {
							print('"' + ligne + "\" : La quantit� n'est pas un entier valide.\nLa commande n'a pas �t� ajout�e.\n");
						}
					} else {
						print('"' + ligne + "\" : La commande ne respecte pas le format demand�\nLa commande n'a pas �t� ajout�e.\n");
					}
					break;
					
				// Cas d'erreur
				case 0:
					print('"' + ligne + "\" : Le fichier ne respecte pas le format demand� : Doit commencer par la section 'Clients'.\nLa ligne a �t� ignor�e.\n");
					break;
				case -1:
					print('"' + ligne + "\" : Le fichier ne respecte pas le format demand� : Aucune ligne ne doit suivre la ligne 'Fin'.\nLa ligne a �t� ignor�e.\n");
					break;
				}
			}
		}
		
		br.close();
		
		if (etat != -1) {
			print("Le fichier ne se termine pas par la ligne \"Fin\".");
		}
		
		print("\n\nBienvenue chez Barrette!\nFactures :\n");
		
		for (Client client : clients) {
			
			double facture = CalculTaxe(client.getFacture());
			String nomClient = client.getNom();
			
			if(facture!=0){
				print(nomClient + " " + facture + "$\n");
				
			}
			
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
	    Date date = new Date();
		
		//Ecriture dans le fichier des factures
		BufferedWriter bw = new BufferedWriter(new FileWriter("Facture-du-"+dateFormat.format(date)+".txt"));
		
		//Impression des erreurs
		for (String erreur : erreurs) {
			bw.write(erreur + "\n");
		}
		
		bw.close();
		
		return String.join("\n", erreurs);
	}
	
	public void print(String text) {
		System.out.println(text);
		erreurs.add(text);
	}
	
	public boolean ValiderFichier(String nomFichier){
		
		boolean test=false;
		
		if(!nomFichier.contains("*")&&!nomFichier.contains("\\")&&!nomFichier.contains("/")&&!nomFichier.contains(":")
				&&!nomFichier.contains("*")&&!nomFichier.contains("?")&&!nomFichier.contains("\"")&&!nomFichier.contains("<")&&!nomFichier.contains(">")){
			
			String extension=nomFichier.substring(nomFichier.length()-4,nomFichier.length());
			
			if(extension.equalsIgnoreCase(".txt")){
				
				test=true;
				
			}
			
		}
		
		return test;
	}
	
	public double CalculTaxe(double prix){
		
		double tps=prix*(0.05);
		double tvq=prix*(0.1);
		prix+=(tps+tvq);
		
		//Arrondi vers le bas et laisse seulement 2 decimal
		prix = Math.floor(prix*100)/100;
		
		
		return prix;
	}
	
}
