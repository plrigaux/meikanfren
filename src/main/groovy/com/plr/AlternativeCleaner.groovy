package com.plr

import java.util.Map;

import groovy.util.slurpersupport.NodeChild

class AlternativeCleaner extends GenOutput implements CleanerInt {

	@Override
	public void clean(NodeChild subNode, TransType type) {

		if (subNode == null) {
			return;
		}

		def nodes = subNode.depthFirst().findAll {
			it.name() == 'a'
		}

		subNode.replaceNode {
			ul() {
				nodes.each { anode ->
					li {
						String hr = anode.@href
						hr = type.getUrlPrefix() +  "?word=" + hr.split('/').last()
						a(href: hr, anode.text())
					}
				}
			}
		}
	}

	String getTitle(Map <String, Object> params) {
		'Suggestions proposées'
	}

	String getHeader(Map <String, Object> params) {
		"Suggestions proposées pour " + params['word']
	}
}
