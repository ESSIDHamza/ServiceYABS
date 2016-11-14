package services;

import javax.ws.rs.core.Response;

public interface IGestionFacture {
	public Response genererFacture(int idUtilisateur, String token);
}