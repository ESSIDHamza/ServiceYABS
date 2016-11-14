package services.entites;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Facture {
	private String nom;
	private String prenom;
	private String adresse;
	private String mail;
	private Collection<LivreFact> achats;
	private float total;

	public Facture() {
		super();
		achats = new ArrayList<LivreFact>();
	}

	public Facture(String nom, String prenom, String adresse, String mail, Collection<LivreFact> achats, float total) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.mail = mail;
		this.achats = achats;
		this.total = total;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Collection<LivreFact> getAchats() {
		return achats;
	}

	public void setAchats(Collection<LivreFact> achats) {
		this.achats = achats;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
}