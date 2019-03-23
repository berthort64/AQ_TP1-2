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
		assertTrue(LectureFichier.ValiderFichier("nom correct, bien qu'am�liorable.txt"));
	}
	
	@Test
	public void testNomFichierFormat() {
		assertFalse(LectureFichier.ValiderFichier("mauvaisFormat"));
	}
	
	@Test
	public void testNomFichierExtension() {
		assertFalse(LectureFichier.ValiderFichier("mauvaiseExtension.md"));
	}
	
	//Test de la m�thode principale
	@Test
	public void testFichierCorrect() throws IOException {
		
		//�crire un fichier d'entr�e
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
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierSansFin() throws IOException {
		
		//�crire un fichier d'entr�e
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
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaisClient() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJeannnnnnnnnnn"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\n\n\"Jean Frites 3\" : Le client \"Jean\" n'existe pas\nLa commande n'a pas �t� ajout�e.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaisPlat() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFritesssssssssssss 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites 3\" : Le plat \"Frites\" n'existe pas\nLa commande n'a pas �t� ajout�e.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaisPrix() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites cecinestpasunprix"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Frites cecinestpasunprix\" : Le prix ne respecte pas le format demand�Le plat n'a pas �t� ajout�.\"Jean Frites 3\" : Le plat \"Frites\" n'existe pas\nLa commande n'a pas �t� ajout�e.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierMauvaiseQte() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites cecinestpasunchiffre"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites cecinestpasunchiffre\" : La quantit� n'est pas un entier valide.La commande n'a pas �t� ajout�e.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierQteDecimale() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 2.5"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites 2.5\" : La quantit� n'est pas un entier valide.La commande n'a pas �t� ajout�e.\n\nBienvenue chez Barrette!\nFactures :\n\n";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierClientPlatInverse() throws IOException {
		
		//�crire un fichier d'entr�e
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
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierCommandeAuDebut() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Commandes :"
				+ "\nJean Frites 3"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nClients :"
				+ "\nJean"
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites 3\" : Le client \"Jean\" n'existe pasLa commande n'a pas �t� ajout�e.Bienvenue chez Barrette!Factures :";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierLignesMauvaisFormat() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean �douard"
				+ "\nPlats :"
				+ "\nFrites sal�es, 2.50$"
				+ "\nCommandes :"
				+ "\nJean a command� trois frites sal�es."
				+ "\nFin");
		bw.close();
		
		String attendu = "ERREURS :\"Jean �douard\" : Le client ne respecte pas le format demand�Le client n'a pas �t� ajout�.\"Frites sal�es, 2.50$\" : Le plat ne respecte pas le format demand�Le plat n'a pas �t� ajout�.\"Jean a command� trois frites sal�es.\" : La commande ne respecte pas le format demand�La commande n'a pas �t� ajout�e.Bienvenue chez Barrette!Factures :";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierSansEntetes() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Jean"
				+ "\nFrites 2.50"
				+ "\nJean Frites 3");
		bw.close();
		
		String attendu = "ERREURS :\"Jean\" : Le fichier ne respecte pas le format demand� : Doit commencer par la section 'Clients'.La ligne a �t� ignor�e.\"Frites 2.50\" : Le fichier ne respecte pas le format demand� : Doit commencer par la section 'Clients'.La ligne a �t� ignor�e.\"Jean Frites 3\" : Le fichier ne respecte pas le format demand� : Doit commencer par la section 'Clients'.La ligne a �t� ignor�e.Le fichier ne se termine pas par la ligne \"Fin\".Bienvenue chez Barrette!Factures :";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}
	
	@Test
	public void testFichierContinueApresFin() throws IOException {
		
		//�crire un fichier d'entr�e
		BufferedWriter bw = new BufferedWriter(new FileWriter("inputTest.txt"));
		bw.write("Clients :"
				+ "\nJean"
				+ "\nPlats :"
				+ "\nFrites 2.50"
				+ "\nCommandes :"
				+ "\nJean Frites 3"
				+ "\nFin"
				+ "\nJean Frites 4");
		bw.close();
		
		String attendu = "ERREURS :\"Jean Frites 4\" : Le fichier ne respecte pas le format demand� : Aucune ligne ne doit suivre la ligne 'Fin'.La ligne a �t� ignor�e.Bienvenue chez Barrette!Factures :Jean 8.62$";
		
		//Ex�cuter la m�thode
		String contenu = LectureFichier.executer("inputTest.txt");
		
		//Ne pas s'occuper des retours chariot
		contenu = contenu.replaceAll("\n", "");
		attendu = attendu.replaceAll("\n", "");
		
		//Lire le r�sultat
		assertEquals(attendu, contenu);
	}

}
