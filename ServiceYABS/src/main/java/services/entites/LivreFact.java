package services.entites;

public class LivreFact {
	private String titre;
	private float prix;

	public LivreFact() {
		super();
	}

	public LivreFact(String titre, float prix) {
		super();
		this.titre = titre;
		this.prix = prix;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}
}