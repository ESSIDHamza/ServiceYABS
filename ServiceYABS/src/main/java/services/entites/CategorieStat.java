package services.entites;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategorieStat {
	private String categorie;
	private int n;

	public CategorieStat() {
		super();
	}

	public CategorieStat(String categorie, int n) {
		super();
		this.categorie = categorie;
		this.n = n;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
}