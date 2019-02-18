import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LectureFichier {

	private static ArrayList<Client> clients 		= new ArrayList<Client>();
	private static ArrayList<Commande> commandes 	= new ArrayList<Commande>();
	private static ArrayList<Plat> plats 			= new ArrayList<Plat>();
	
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
			
			//On ignore les lignes servant au repérage
			if (ligne != "Clients :" && ligne != "Plats :" && ligne != "Commandes :" && ligne != "Fin") {
				
				String[] contenu = ligne.split(" ");
				
				switch (contenu.length) {
				case 1:
					
					Client client = new Client(contenu[0]);
					clients.add(client);
					
					System.out.println(client.getNom());
					
					break;
				case 2:
					
					Plat plat = new Plat(contenu[0], Double.parseDouble(contenu[1]));
					plats.add(plat);
					
					break;
				case 3:
					
					String nomClient = contenu[0];
					String nomProduit = contenu[1];
					int qte = Integer.parseInt(contenu[2]);
					
					Client clientCommande = null;
					Plat platCommande = null;
					
					for (Client c : clients) {
						if (c.getNom() == nomClient) {
							clientCommande = c;
							break;
						}
					}
					
					if (clientCommande == null) {
						
						System.out.println('"' + ligne + "\" : Le client \"" + nomClient + "\" n'existe pas\nLa commande n'a pas été ajoutée.\n");
						break;
						
					}
					
					for (Plat p : plats) {
						if (p.getProduit() == nomProduit) {
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
					
					break;
				default:
					System.out.println("Le fichier ne respecte pas le format demandé");
				}
				
			}
			
		}
		
		br.close();
		
		
		//Ecriture dans le fichier des factures
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichierE));

		bw.write("Bienvenue chez Barrette!\n");
		bw.write("Factures :\n");
		
		for (Commande commande : commandes) {
			
			double prix = commande.getQuantite() * commande.getProduit().getPrix();
			String nomClient = commande.getClient().getNom();
			
			bw.write(nomClient + " " + prix + "$\n");
			
		}
		
		bw.close();
		
		System.out.println("\nFin du programme.");
	}
	
}
