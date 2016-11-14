package facture;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import services.entites.Facture;
import services.entites.LivreFact;

public class GenerationFacturePDFImpl implements IGenerationFacture {
	private String chemin;
	private String nomFichier;
	private Date dateAchat = new Date();

	public void genererFacture(Facture facture) {
		String dateString = dateAchat.toString().replace(" ", "");
		dateString = dateString.replace(":", "");
		chemin = "C:\\Users\\Hamza\\workspace\\ServiceYABS\\src\\main\\webapp\\factures\\";
		chemin += facture.getNom() + facture.getPrenom() + dateString + ".pdf";
		nomFichier = facture.getNom() + facture.getPrenom() + dateString + ".pdf";
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(chemin));
			document.open();
			Paragraph date = new Paragraph();
			date.setAlignment(Element.ALIGN_RIGHT);
			Font fontDate = new Font();
			fontDate.setColor(255, 0, 0);
			date.setFont(fontDate);
			date.add(dateAchat.toString());
			document.add(date);
			Paragraph nom = new Paragraph();
			nom.add("Nom : " + facture.getNom());
			document.add(nom);
			Paragraph prenom = new Paragraph();
			prenom.add("Prénom : " + facture.getPrenom());
			document.add(prenom);
			Paragraph mail = new Paragraph();
			mail.add("E-mail : " + facture.getMail());
			document.add(mail);
			Paragraph adresse = new Paragraph();
			adresse.add("Adresse : " + facture.getAdresse());
			document.add(adresse);
			Paragraph separateur = new Paragraph();
			separateur.setAlignment(Element.ALIGN_CENTER);
			separateur.add("***************");
			document.add(separateur);
			for (LivreFact lf : facture.getAchats()) {
				Paragraph titre = new Paragraph();
				titre.add(lf.getTitre());
				document.add(titre);
				Paragraph prix = new Paragraph();
				prix.setAlignment(Element.ALIGN_RIGHT);
				prix.add(lf.getPrix() + " D.T");
				document.add(prix);
			}
			document.add(separateur);
			Paragraph total = new Paragraph();
			Font fontTotal = new Font();
			fontTotal.setStyle(Font.BOLD);
			total.setFont(fontTotal);
			total.add("Total :");
			document.add(total);
			Paragraph prixTotal = new Paragraph();
			prixTotal.setAlignment(Element.ALIGN_RIGHT);
			Font fontPrixTotal = new Font();
			fontPrixTotal.setColor(255, 0, 0);
			fontPrixTotal.setStyle(Font.BOLD);
			prixTotal.setFont(fontPrixTotal);
			prixTotal.add(facture.getTotal() + " D.T");
			document.add(prixTotal);
			document.close();
		} catch (Exception e) {
			System.err.println("Erreur de génération PDF de la facture !");
		}
	}

	public String getChemin() {
		return chemin;
	}

	public String getNomFichier() {
		return nomFichier;
	}
}