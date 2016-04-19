package com.plr;

import groovy.util.slurpersupport.NodeChild


public class TestConnections extends spock.lang.Specification {


	def "testtemplate"() {
		given: "a new Adder class is created"

		def pizza = new Pizza2();

		URI uri = TransType.FREN.buildUrl("trouva")
		println uri
		//String word, TransType type

		String result = pizza.getData(uri)

		//println result;
	}
}



