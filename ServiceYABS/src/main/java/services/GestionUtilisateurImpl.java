package services;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.GestionUtilisateurLocal;
import dao.service.GestionTokenLocal;
import entites.Utilisateur;
import entites.service.AuthentificationForm;
import entites.service.Token;
import services.entites.NombreUtilisateursConnectes;
import services.entites.UtilisateurToken;

@Path("/utilisateurs")
public class GestionUtilisateurImpl implements IGestionUtilisateur {
	@EJB
	private GestionUtilisateurLocal gestionUtilisateurBean;

	@EJB
	private GestionTokenLocal gestionTokenBean;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void creerUtilisateur(Utilisateur utilisateur) {
		gestionUtilisateurBean.creerUtilisateur(utilisateur);
	}

	@POST
	@Path("/authentification")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UtilisateurToken authentifierUtilisateur(AuthentificationForm af) {
		UtilisateurToken utilisateurToken = null;
		try {
			Utilisateur utilisateur = gestionUtilisateurBean.authentifierUtilisateur(af.getLogin(), af.getMdp());
			Token token = gestionTokenBean.genererToken(af);
			utilisateurToken = new UtilisateurToken(utilisateur, token);
		} catch (NoResultException nre) {
		}
		return utilisateurToken;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Utilisateur getUtilisateurById(@PathParam("id") int id) {
		return gestionUtilisateurBean.getUtilisateurById(id);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierUtilisateur(@PathParam("id") int id, UtilisateurToken utilisateurToken) {
		if (gestionTokenBean.estValide(utilisateurToken.getToken())) {
			utilisateurToken.getUtilisateur().setId(id);
			gestionUtilisateurBean.modifierUtilisateur(utilisateurToken.getUtilisateur());
			return Response.ok(utilisateurToken.getUtilisateur()).build();
		} else
			return Response.status(Status.FORBIDDEN).build();
	}

	@DELETE
	@Path("/{id}")
	public void supprimerUtilisateur(@PathParam("id") int id) {
		gestionUtilisateurBean.supprimerUtilisateur(gestionUtilisateurBean.getUtilisateurById(id));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Utilisateur> getAllUtilisateurs() {
		return gestionUtilisateurBean.getAllUtilisateurs();
	}

	@POST
	@Path("/deconnexion")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deconnecterUtilisateur(Token token) {
		gestionTokenBean.supprimerToken(token);
	}

	@GET
	@Path("/nombreUtilisateursConnectes")
	@Produces(MediaType.APPLICATION_JSON)
	public NombreUtilisateursConnectes getNombreUtilisateursConnectes() {
		return new NombreUtilisateursConnectes(gestionTokenBean.getNombreUtilisateursConnectes());
	}
}