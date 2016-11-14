package services;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.GestionLivreLocal;
import dao.GestionUtilisateurLocal;
import dao.service.GestionTokenLocal;
import entites.Livre;
import entites.Utilisateur;
import entites.service.Token;

@Path("/utilisateurs/{idUtilisateur}/panier")
public class GestionPanierImpl implements IGestionPanier {
	@EJB
	private GestionUtilisateurLocal gestionUtilisateurBean;
	@EJB
	private GestionLivreLocal gestionLivreBean;

	@EJB
	private GestionTokenLocal gestionTokenBean;

	@PUT
	@Path("/ajout/livre/{idLivre}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ajouterLivre(@PathParam("idLivre") int idLivre, @PathParam("idUtilisateur") int idUtilisateur,
			Token token) {
		if (gestionTokenBean.estValide(token)) {
			Utilisateur utilisateur = gestionUtilisateurBean.getUtilisateurById(idUtilisateur);
			Livre livre = gestionLivreBean.getLivre(idLivre);
			utilisateur.getPanier().add(livre);
			gestionUtilisateurBean.modifierUtilisateur(utilisateur);
			livre.setNbrCopies(livre.getNbrCopies() - 1);
			gestionLivreBean.modifierLivre(livre);
			return Response.ok(utilisateur).build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}

	@PUT
	@Path("/retrait/livre/{idLivre}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retirerLivre(@PathParam("idLivre") int idLivre, @PathParam("idUtilisateur") int idUtilisateur,
			Token token) {
		if (gestionTokenBean.estValide(token)) {
			Utilisateur utilisateur = gestionUtilisateurBean.getUtilisateurById(idUtilisateur);
			Livre livre = gestionLivreBean.getLivre(idLivre);
			utilisateur.getPanier().remove(livre);
			gestionUtilisateurBean.modifierUtilisateur(utilisateur);
			livre.setNbrCopies(livre.getNbrCopies() + 1);
			gestionLivreBean.modifierLivre(livre);
			return Response.ok(utilisateur).build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}
}