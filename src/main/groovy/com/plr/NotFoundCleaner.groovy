package com.plr

import java.util.Map;

import groovy.util.slurpersupport.NodeChild;

class NotFoundCleaner extends GenOutput implements CleanerInt {

	@Override
	public void clean(NodeChild subNode, TransType type) {
		
	}

	@Override
	String getContent(NodeChild node) {
		return '<span>Une erreur est survenue</span>'
	}

	String getTitle(Map <String, Object> params) {
		'Erreur'
	}

	String getHeader(Map <String, Object> params) {
		getTitle(params)
	}
}
