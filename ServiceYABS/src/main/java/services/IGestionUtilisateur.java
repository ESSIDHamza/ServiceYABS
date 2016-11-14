package services;

import java.util.List;

import javax.ws.rs.core.Response;

import entites.Utilisateur;
import entites.service.AuthentificationForm;
import entites.service.Token;
import services.entites.UtilisateurToken;

public interface IGestionUtilisateur {
	public void creerUtilisateur(Utilisateur utilisateur);

	public UtilisateurToken authentifierUtilisateur(AuthentificationForm af);

	public Utilisateur getUtilisateurById(int id);

	public Response modifierUtilisateur(int id, UtilisateurToken utilisateurToken);

	public void supprimerUtilisateur(int id);

	public List<Utilisateur> getAllUtilisateurs();

	public void deconnecterUtilisateur(Token token);
}