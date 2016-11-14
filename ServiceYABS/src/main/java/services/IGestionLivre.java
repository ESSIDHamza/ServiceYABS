package services;

import java.util.List;

import javax.ws.rs.core.Response;

import entites.Livre;
import services.entites.CategorieStat;
import services.entites.ListeCategories;
import services.entites.LivreToken;

public interface IGestionLivre {
	public void creerLivre(LivreToken livreToken);

	public Livre getLivre(int id);

	public Response modifierLivre(int id, LivreToken livreToken);

	public void supprimerLivre(int id);

	public List<Livre> getAllLivres();

	public ListeCategories getCategories();

	public List<Livre> rechercher(String motCle);

	public List<Livre> getLivresByCategorie(String categorie);

	public List<CategorieStat> getStatistiques();
}