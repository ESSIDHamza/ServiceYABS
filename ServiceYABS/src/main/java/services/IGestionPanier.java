package services;

import javax.ws.rs.core.Response;

import entites.service.Token;

public interface IGestionPanier {
	public Response ajouterLivre(int idLivre, int idUtilisateur, Token token);

	public Response retirerLivre(int idLivre, int idUtilisateur, Token token);
}