package services.entites;

import javax.xml.bind.annotation.XmlRootElement;

import entites.Utilisateur;
import entites.service.Token;

@XmlRootElement
public class UtilisateurToken {
	private Utilisateur utilisateur;
	private Token token;

	public UtilisateurToken() {
		super();
		this.utilisateur = new Utilisateur();
		this.token = new Token();
	}

	public UtilisateurToken(Utilisateur utilisateur, Token token) {
		super();
		this.utilisateur = utilisateur;
		this.token = token;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}