package services.entites;

import javax.xml.bind.annotation.XmlRootElement;

import entites.Livre;
import entites.service.Token;

@XmlRootElement
public class LivreToken {
	private Livre livre;
	private Token token;

	public LivreToken() {
		super();
		this.livre = new Livre();
		this.token = new Token();
	}

	public LivreToken(Livre livre, Token token) {
		super();
		this.livre = livre;
		this.token = token;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}