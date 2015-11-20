package com.plr;

import groovy.xml.XmlUtil


public class Pizza2Test2 extends spock.lang.Specification {


	def "parse and clean xml"() {
		given: "a new Adder class is created"
		def pizza = new Pizza2();

		String xmlString = this.getClass().getResource( '/traink.html' ).text

		def doc = pizza.parse xmlString

				NodePage np = pizza.getSubNode(doc, TransType.ENFR)
		
		np.clean(np.node)
		
		
		println XmlUtil.serialize(np.node)


		
	}
}