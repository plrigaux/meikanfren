package com.plr

import groovy.util.slurpersupport.NodeChild;

class NodePage  {
	PageProcessor pp;
	NodeChild node;
	TransType type;

	public void clean() {
		pp.getCleaner().clean(node, type)
	}

	public String generateOutPut(Map <String, Object> params) {
		return pp.getCleaner().generateOutPut(node, params);
	}
}