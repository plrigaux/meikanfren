package com.plr;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

public enum TransType {

	ENFR("anglais-francais", "trans_enfr"), FREN("francais-anglais", "trans_fren");

	private final String url;
	private final String dico;
	private final String urlPrefix;

	TransType(String dico, String urlPrefix) {
		this.url = "http://www.larousse.fr/dictionnaires/" + dico + "/";
		this.dico = dico;
		this.urlPrefix = urlPrefix;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public URI buildUrl(String word) throws URISyntaxException {
		URIBuilder builder = new URIBuilder("http://www.larousse.fr");
		builder.setPath("/dictionnaires/rechercher");
		builder.addParameter("q", word);
		builder.addParameter("l", dico);
		builder.addParameter("culture", ""); // to be consistant
		
		return builder.build();

	}
	// http://www.larousse.fr/dictionnaires/rechercher?q=tra%C3%AEna&l=francais-anglais&culture=
}
