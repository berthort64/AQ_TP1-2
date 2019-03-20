import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LectureFichier {

	private static ArrayList<Client> clients 		= new ArrayList<Client>();
	private static ArrayList<Commande> commandes 	= new ArrayList<Commande>();
	private static ArrayList<Plat> plats 			= new ArrayList<Plat>();
	
	private static int etat = 0;
	
	public static void main (String[] args) throws IOException {
		
		//Nom des fichiers
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Entrez le chemin du fichier de LECTURE : ");
		String fichierL = reader.nextLine();

		System.out.print("Entrez le chemin du fichier d'ÉCRITURE : ");
		String fichierE = reader.nextLine();
		
		reader.close();
		
		System.out.println();
		
		
		
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
				
			case "Fin :":
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
						System.out.println('"' + ligne + "\" : Le client ne respecte pas le format demandé\nLe client n'a pas été ajouté.\n");
					}
					break;
					
				case 2:
					
					if (contenu.length == 2) {
						Plat plat = new Plat(contenu[0], Double.parseDouble(contenu[1]));
						plats.add(plat);
					} else {
						System.out.println('"' + ligne + "\" : Le plat ne respecte pas le format demandé\nLe plat n'a pas été ajouté.\n");
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
							
							System.out.println('"' + ligne + "\" : Le client \"" + nomClient + "\" n'existe pas\nLa commande n'a pas été ajoutée.\n");
							break;
							
						}
						
						for (Plat p : plats) {
							if (p.getProduit().equals(nomProduit)) {
								platCommande = p;
								break;
							}
						}
						
						if (platCommande == null) {
							
							System.out.println('"' + ligne + "\" : Le plat \"" + nomProduit + "\" n'existe pas\nLa commande n'a pas été ajoutée.\n");
							break;
							
						}
						
						Commande commande = new Commande(clientCommande, platCommande, qte);
						commandes.add(commande);
						
						clientCommande.ajouterCommande(qte * platCommande.getPrix());
					} else {
						System.out.println('"' + ligne + "\" : La commande ne respecte pas le format demandé\nLa commande n'a pas été ajoutée.\n");
					}
					break;
					
				// Cas d'erreur
				case 0:
					System.out.println('"' + ligne + "\" : Le fichier ne respecte pas le format demandé : Doit commencer par la section 'Clients'.\nLa ligne a été ignorée.\n");
					break;
				case -1:
					System.out.println('"' + ligne + "\" : Le fichier ne respecte pas le format demandé : Aucune ligne ne doit suivre la ligne 'Fin'.\nLa ligne a été ignorée.\n");
					break;
				}
			}
		}
		
		br.close();
		
		
		
		//Ecriture dans le fichier des factures
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichierE));

		bw.write("Bienvenue chez Barrette!\n");
		bw.write("Factures :\n");
		
		for (Client client : clients) {
			
			double facture = client.getFacture();
			String nomClient = client.getNom();
			
			bw.write(nomClient + " " + facture + "$\n");
			
		}
		
		bw.close();
		
		
		
		System.out.println("\nFin du programme.");
	}
	
}
