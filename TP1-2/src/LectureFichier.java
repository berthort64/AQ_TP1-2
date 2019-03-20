import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LectureFichier {

	private static ArrayList<Client> clients 		= new ArrayList<Client>();
	private static ArrayList<Commande> commandes 	= new ArrayList<Commande>();
	private static ArrayList<Plat> plats 			= new ArrayList<Plat>();
	
	private static int etat = 0;
	
	private static ArrayList<String> erreurs = new ArrayList<String>();
	
	public static void main (String[] args) throws IOException {
		
		//Nom des fichiers
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Entrez le chemin du fichier de LECTURE : ");
		String fichierL = reader.nextLine();
		
		reader.close();
		
		executer(fichierL);
		
	}
	
	public static void executer(String fichierL) throws IOException {
		
		//Lecture dans le fichier des commandes
		BufferedReader br = new BufferedReader(new FileReader(fichierL));
		
		String ligne;
		
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
						print('"' + ligne + "\" : Le client ne respecte pas le format demandé\nLe client n'a pas été ajouté.\n");
					}
					break;
					
				case 2:
					
					if (contenu.length == 2) {
						
						try {
							Plat plat = new Plat(contenu[0], Double.parseDouble(contenu[1]));
							plats.add(plat);
						} catch(Exception e) {
							print('"' + ligne + "\" : Le prix ne respecte pas le format demandé\nLe plat n'a pas été ajouté.\n");
						}
						
					} else {
						print('"' + ligne + "\" : Le plat ne respecte pas le format demandé\nLe plat n'a pas été ajouté.\n");
					}
					break;
					
				case 3:
					
					if (contenu.length == 3) {
						String nomClient = contenu[0];
						String nomProduit = contenu[1];
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
							
							print('"' + ligne + "\" : Le client \"" + nomClient + "\" n'existe pas\nLa commande n'a pas été ajoutée.\n");
							break;
							
						}
						
						for (Plat p : plats) {
							if (p.getProduit().equals(nomProduit)) {
								platCommande = p;
								break;
							}
						}
						
						if (platCommande == null) {
							
							print('"' + ligne + "\" : Le plat \"" + nomProduit + "\" n'existe pas\nLa commande n'a pas été ajoutée.\n");
							break;
							
						}
						
						Commande commande = new Commande(clientCommande, platCommande, qte);
						commandes.add(commande);
						
						clientCommande.ajouterCommande(qte * platCommande.getPrix());
					} else {
						print('"' + ligne + "\" : La commande ne respecte pas le format demandé\nLa commande n'a pas été ajoutée.\n");
					}
					break;
					
				// Cas d'erreur
				case 0:
					print('"' + ligne + "\" : Le fichier ne respecte pas le format demandé : Doit commencer par la section 'Clients'.\nLa ligne a été ignorée.\n");
					break;
				case -1:
					print('"' + ligne + "\" : Le fichier ne respecte pas le format demandé : Aucune ligne ne doit suivre la ligne 'Fin'.\nLa ligne a été ignorée.\n");
					break;
				}
			}
		}
		
		br.close();
		
		if (etat != -1) {
			print("Le fichier ne se termine pas par la ligne \"Fin\".");
		}
		
		//Ecriture dans le fichier des factures
		BufferedWriter bw = new BufferedWriter(new FileWriter("Facture-du-" /*TODO*/ ));
		
		//Impression des erreurs
		if (erreurs.size() > 0) {
			bw.write("\n\nERREURS :\n");
			for (String erreur : erreurs) {
				bw.write(erreur + "\n");
			}
		}
		
		bw.write("\n\nBienvenue chez Barrette!\n");
		bw.write("Factures :\n");
		
		for (Client client : clients) {
			
			double facture = client.getFacture();
			String nomClient = client.getNom();
			
			bw.write(nomClient + " " + facture + "$\n");
			
		}
		
		
		bw.close();
		
		System.out.println("\nFin du programme.");
	}
	
	private static void print(String text) {
		System.out.println(text);
		erreurs.add(text);
	}
	
}
