package com.plr;

import java.util.Map;

import groovy.text.SimpleTemplateEngine
import groovy.util.slurpersupport.NodeChild;
import groovy.xml.XmlUtil;

abstract public class GenOutput {


	def genOutPut(Map<String, Object> params) {

		def f = this.getClass().getResource( '/transout.html' )

		params = params.withDefault { x -> '${' + x + '}' }

		def engine = new SimpleTemplateEngine()

		def template = engine.createTemplate(f as URL).make(params)

		template
	}

	String getContent(NodeChild node) {
		return XmlUtil.serialize(node).substring('<?xml version="1.0" encoding="UTF-8"?>'.size())
	}

	public String generateOutPut(NodeChild node, Map <String, Object> params) {
		params["content"] = getContent(node)

		params["title"] = getTitle(params);

		params["header"] = getHeader(params);

		return genOutPut(params);
	}

	String getTitle(Map <String, Object> params) {}

	String getHeader(Map <String, Object> params) {}
}
