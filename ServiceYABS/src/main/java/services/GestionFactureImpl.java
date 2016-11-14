package services;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.GestionUtilisateurLocal;
import dao.service.GestionTokenLocal;
import entites.Livre;
import entites.Utilisateur;
import entites.service.Token;
import services.entites.Facture;
import services.entites.LivreFact;

@Path("/utilisateurs/{idUtilisateur}/facture")
public class GestionFactureImpl implements IGestionFacture {
	@EJB
	private GestionUtilisateurLocal gestionUtilisateurBean;

	@EJB
	private GestionTokenLocal gestionTokenBean;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response genererFacture(@PathParam("idUtilisateur") int idUtilisateur, @QueryParam("token") String token) {
		Token token2 = new Token(idUtilisateur, token);
		if (gestionTokenBean.estValide(token2)) {
			Utilisateur utilisateur = gestionUtilisateurBean.getUtilisateurById(idUtilisateur);
			Facture facture = new Facture(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAdresse(),
					utilisateur.getMail(), new ArrayList<LivreFact>(), 0);
			float total = 0;
			for (Livre livre : utilisateur.getPanier()) {
				facture.getAchats().add(new LivreFact(livre.getTitre(), livre.getPrix()));
				total += livre.getPrix();
			}
			facture.setTotal(total);
			return Response.ok(facture).build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}
}