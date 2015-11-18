package com.plr;

public enum TransType {

	ENFR("anglais-francais"), FREN("francais-anglais");

	private String url;

	TransType(String dico) {
		url = "http://www.larousse.fr/dictionnaires/" + dico + "/";
	}

	public String getUrl() {
		return url;
	}
}
