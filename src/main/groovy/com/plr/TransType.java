package com.plr;

import java.net.URISyntaxException;

import groovyx.net.http.URIBuilder;

public enum TransType {

	ENFR("anglais-francais", "en/fr/"), FREN("francais-anglais", "fr/en/");

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

	public String buildUrl(String word) throws URISyntaxException {
		URIBuilder builder = new URIBuilder("http://www.larousse.fr");
		builder.setPath("/dictionnaires/rechercher");
		builder.addQueryParam("q", word);
		builder.addQueryParam("l", dico);
		builder.addQueryParam("culture", ""); // to be consistant
		
		return builder.toString();

	}
	// http://www.larousse.fr/dictionnaires/rechercher?q=tra%C3%AEna&l=francais-anglais&culture=
}
