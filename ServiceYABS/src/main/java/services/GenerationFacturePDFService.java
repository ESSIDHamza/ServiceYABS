package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import dao.GestionUtilisateurLocal;
import entites.Livre;
import facture.GenerationFacturePDFImpl;
import facture.IGenerationFacture;
import services.entites.Facture;
import services.entites.LivreFact;

@Path("/facture")
public class GenerationFacturePDFService {
	private IGenerationFacture generationFacture = new GenerationFacturePDFImpl();
	@EJB
	private GestionUtilisateurLocal gestionUtilisateurBean;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response rediriger(@QueryParam("nom") String nom, @QueryParam("prenom") String prenom,
			@QueryParam("adresse") String adresse, @QueryParam("mail") String mail, @QueryParam("total") float total,
			@QueryParam("id") int id) throws IOException {
		Facture facture = new Facture(nom, prenom, adresse, mail, null, total);
		Collection<LivreFact> achats = new ArrayList<LivreFact>();
		for (Livre livre : gestionUtilisateurBean.getUtilisateurById(id).getPanier())
			achats.add(new LivreFact(livre.getTitre(), livre.getPrix()));
		facture.setAchats(achats);
		generationFacture.genererFacture(facture);
		String cheminFacture = ((GenerationFacturePDFImpl) generationFacture).getChemin();
		File file = new File(cheminFacture);
		ResponseBuilder response = Response.ok(file);
		response.header("Content-Disposition",
				"attachment; filename=" + ((GenerationFacturePDFImpl) generationFacture).getNomFichier());
		return response.entity(((GenerationFacturePDFImpl) generationFacture).getNomFichier()).build();
	}
}