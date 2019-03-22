package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import main.LectureFichier;

import org.junit.Test;

public class LectureFichierTest {

	//Test des taxes
	@Test
	public void testTaxes() {
		assertEquals(11.5, LectureFichier.CalculTaxe(10), 0);
	}
	
	@Test
	public void testTaxesZero() {
		assertEquals(0, LectureFichier.CalculTaxe(0), 0);
	}
	
	@Test
	public void testTaxesNegatif() {
		assertEquals(-11.5, LectureFichier.CalculTaxe(-10), 0);
	}
	

	//Test des noms de fichier
	@Test
	public void testNomFichier() {
		assertTrue(LectureFichier.ValiderFichier("document.txt"));
	}
	
	@Test
	public void testNomFichierDT() {
		assertFalse(LectureFichier.ValiderFichier("dossier/nom.txt"));
	}
	
	@Test
	public void testNomFichierSymbole() {
		assertFalse(LectureFichier.ValiderFichier("mauvais?nom.txt"));
	}
	
	@Test
	public void testNomFichierNomCorrect() {
		assertTrue(LectureFichier.ValiderFichier("nom correct, bien qu'améliorable.txt"));
	}
	
	@Test
	public void testNomFichierFormat() {
		assertFalse(LectureFichier.ValiderFichier("mauvaisFormat"));
	}
	
	@Test
	public void testNomFichierExtension() {
		assertFalse(LectureFichier.ValiderFichier("mauvaiseExtension.md"));
	}
	
	//Test de la méthode principale
	@Test
	public void testFichierCorrect() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\n\n\n\nBienvenue chez Barrette!\nFactures :\n\nJean 8.62$\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierSansFin() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\n");
		bw.close();
		
		String attendu = "ERREURS :\n\nLe fichier ne se termine pas par la ligne \"Fin\".\n\n\nBienvenue chez Barrette!\nFactures :\n\nJean 8.62$\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaisClient() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJeannnnnnnnnnn"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\n\n\"Jean Frites 3\" : Le client \"Jean\" n'existe pas\nLa commande n'a pas été ajoutée.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaisPlat() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFritesssssssssssss 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites 3\" : Le plat \"Frites\" n'existe pas\nLa commande n'a pas été ajoutée.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaisPrix() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites cecinestpasunprix"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Frites cecinestpasunprix\" : Le prix ne respecte pas le format demandéLe plat n'a pas été ajouté.\"Jean Frites 3\" : Le plat \"Frites\" n'existe pas\nLa commande n'a pas été ajoutée.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaiseQte() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites cecinestpasunchiffre"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites cecinestpasunchiffre\" : La quantité n'est pas un entier valide.La commande n'a pas été ajoutée.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierQteDecimale() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 2.5"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites 2.5\" : La quantité n'est pas un entier valide.La commande n'a pas été ajoutée.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierClientPlatInverse() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Plats :"
				+ "\nFrites 2.50"
				+ "\nClients :"
				+ "\nJean"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\n\n\n\nBienvenue chez Barrette!\nFactures :\n\nJean 8.62$\n";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierCommandeAuDebut() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Commandes :"
				+ "\nJean Frites 3"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nClients :"
				+ "\nJean"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites 3\" : Le client \"Jean\" n'existe pasLa commande n'a pas été ajoutée.Bienvenue chez Barrette!Factures :";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierLignesMauvaisFormat() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean Édouard"
				+ "\nPlats :"
				+ "\nFrites salées, 2.50$"
				+ "\nCommandes :"
				+ "\nJean a commandé trois frites salées."
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Édouard\" : Le client ne respecte pas le format demandéLe client n'a pas été ajouté.\"Frites salées, 2.50$\" : Le plat ne respecte pas le format demandéLe plat n'a pas été ajouté.\"Jean a commandé trois frites salées.\" : La commande ne respecte pas le format demandéLa commande n'a pas été ajoutée.Bienvenue chez Barrette!Factures :";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierSansEntetes() throws IOException {
		
		//Écrire un fichier d'entrée
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Jean"
				+ "\nFrites 2.50"
				+ "\nJean Frites 3");
		bw.close();
		
		String attendu = "ERREURS :\"Jean\" : Le fichier ne respecte pas le format demandé : Doit commencer par la section 'Clients'.La ligne a été ignorée.\"Frites 2.50\" : Le fichier ne respecte pas le format demandé : Doit commencer par la section 'Clients'.La ligne a été ignorée.\"Jean Frites 3\" : Le fichier ne respecte pas le format demandé : Doit commencer par la section 'Clients'.La ligne a été ignorée.Le fichier ne se termine pas par la ligne \"Fin\".Bienvenue chez Barrette!Factures :";
		
		//Exécuter la méthode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le résultat
		assertEquals(attendu, contenu);
	}

}
