package services.entites;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NombreUtilisateursConnectes {
	private int nombreUtilisateursConnectes;

	public NombreUtilisateursConnectes() {
		super();
	}

	public NombreUtilisateursConnectes(int nombreUtilisateursConnectes) {
		super();
		this.nombreUtilisateursConnectes = nombreUtilisateursConnectes;
	}

	public int getNombreUtilisateursConnectes() {
		return nombreUtilisateursConnectes;
	}

	public void setNombreUtilisateursConnectes(int nombreUtilisateursConnectes) {
		this.nombreUtilisateursConnectes = nombreUtilisateursConnectes;
	}
}