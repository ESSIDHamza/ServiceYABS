package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import dao.GestionLivreLocal;
import dao.GestionUtilisateurLocal;
import dao.service.GestionTokenLocal;
import entites.Livre;
import entites.Utilisateur;
import services.entites.CategorieStat;
import services.entites.ListeCategories;
import services.entites.LivreSupp;
import services.entites.LivreToken;

@Path("/livres")
public class GestionLivreImpl implements IGestionLivre {
	@EJB
	private GestionLivreLocal gestionLivreBean;

	@EJB
	private GestionUtilisateurLocal gestionUtilisateurBean;

	@EJB
	private GestionTokenLocal gestionTokenBean;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void creerLivre(LivreToken livreToken) {
		if (gestionTokenBean.estValide(livreToken.getToken()))
			gestionLivreBean.creerLivre(livreToken.getLivre());
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Livre getLivre(@PathParam("id") int id) {
		return gestionLivreBean.getLivre(id);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierLivre(@PathParam("id") int id, LivreToken livreToken) {
		if (gestionTokenBean.estValide(livreToken.getToken())) {
			livreToken.getLivre().setId(id);
			gestionLivreBean.modifierLivre(livreToken.getLivre());
			return Response.ok(livreToken.getLivre()).build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}

	@GET
	@Path("/{id}/supprimable")
	@Produces(MediaType.APPLICATION_JSON)
	public LivreSupp estSupprimable(@PathParam("id") int id) {
		boolean estSupprimable = true;
		for (Utilisateur utilisateur : gestionUtilisateurBean.getAllUtilisateurs())
			for (Livre livre : utilisateur.getPanier()) {
				if (livre.getId() == id) {
					estSupprimable = false;
					break;
				}
				if (!estSupprimable)
					break;
			}
		LivreSupp livreSupp = new LivreSupp(estSupprimable);
		return livreSupp;
	}

	@DELETE
	@Path("/{id}")
	public void supprimerLivre(@PathParam("id") int id) {
		String chemin = gestionLivreBean.getLivre(id).getImage();
		gestionLivreBean.supprimerLivre(gestionLivreBean.getLivre(id));
		try {
			Runtime.getRuntime().exec("cmd del " + chemin);
		} catch (IOException e) {
			System.err.println("L'image du livre # " + id + " n'a pas été supprimée !");
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Livre> getAllLivres() {
		return gestionLivreBean.getAllLivres();
	}

	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public ListeCategories getCategories() {
		ListeCategories lc = new ListeCategories(gestionLivreBean.getCategories());
		return lc;
	}

	@GET
	@Path("/recherche/{motCle}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Livre> rechercher(@PathParam("motCle") String motCle) {
		return gestionLivreBean.rechercher(motCle);
	}

	@GET
	@Path("/categorie/{categorie}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Livre> getLivresByCategorie(@PathParam("categorie") String categorie) {
		return gestionLivreBean.getLivresByCategorie(categorie);
	}

	@POST
	@Path("/{id}/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void ajouterImage(@FormDataParam("image") InputStream inputStream,
			@FormDataParam("image") FormDataContentDisposition formDataContentDisposition, @PathParam("id") int id)
			throws IOException {
		String extension = formDataContentDisposition.getFileName()
				.substring(formDataContentDisposition.getFileName().lastIndexOf(".") + 1);
		String chemin = "C:\\Users\\Hamza\\workspace\\ServiceYABS\\src\\main\\webapp\\covers\\" + id + "." + extension;
		FileOutputStream fileOutputStream = new FileOutputStream(new File(chemin));
		byte bytes[] = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(bytes)) != -1)
			fileOutputStream.write(bytes, 0, read);
		fileOutputStream.flush();
		fileOutputStream.close();
		Livre livre = gestionLivreBean.getLivre(id);
		livre.setImage(chemin);
		gestionLivreBean.modifierLivre(livre);
	}

	@GET
	@Path("/categories/statistiques")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategorieStat> getStatistiques() {
		List<CategorieStat> resultat = new ArrayList<CategorieStat>();
		for (String categorie : getCategories().getCategories()) {
			int n = 0;
			for (Utilisateur utilisateur : gestionUtilisateurBean.getAllUtilisateurs())
				for (Livre livre : utilisateur.getPanier())
					if (livre.getCategorie().equals(categorie))
						n++;
			resultat.add(new CategorieStat(categorie, n));
		}
		return resultat;
	}
}