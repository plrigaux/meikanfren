package com.plr;

import groovy.xml.XmlUtil


public class Pizza2Test extends spock.lang.Specification {
	def "adder-test"() {
		given: "a new Adder class is created"
		def adder = new Pizza2();

		expect: "Adding two numbers to return the sum"
		adder.add(3, 4) == 7

		adder.add("piz", "za") == "pizza"
		//adder.add("piz", "zaa") == "pizza"
	}

	def "parse html"() {

		given: "a new Adder class is created"
		def pizza = new Pizza2();

		String xmlString = this.getClass().getResource( '/paper.html' ).text

		def doc = pizza.parse xmlString

		println "pasfd"

		def cars = doc.depthFirst().find { it.name() == 'div' && it.@class == 'article_bilingue' }


		println cars.size() + " " + cars.getClass()

		println XmlUtil.serialize(cars)

		expect: "Read file"

		doc != null
	}

	def "clean xml"() {
		given: "a new Adder class is created"
		def pizza = new Pizza2();

		String xmlString = this.getClass().getResource( '/cow.html' ).text

		def doc = pizza.parse xmlString

		//def doc = new XmlSlurper().parseText(xmlString)


		pizza.cleanSubNode(doc, TransType.ENFR)

		println XmlUtil.serialize(doc)

	}
	
	def "parse and clean xml"() {
		given: "a new Adder class is created"
		def pizza = new Pizza2();

		String xmlString = this.getClass().getResource( '/paper.html' ).text

		def doc = pizza.parse xmlString
		
		doc = pizza.getSubNode(doc)


		pizza.cleanSubNode(doc, TransType.ENFR)

		println XmlUtil.serialize(doc)

	}


	def "clean word"() {
		given: "a new Adder class is created"
		def pizza = new Pizza2();

		expect: "Read file"

		'asdf' == pizza.cleanWord ('asdf')
		'asdf' == pizza.cleanWord ('asdf ')
		'asdf' == pizza.cleanWord ('	asdf ')
		'asdf_asdf' == pizza.cleanWord ('asdf asdf')
		'asdf_asdf_asdf' == pizza.cleanWord ('asdf asdf asdf')
	}

	def "test empty"() {
		given:
		def matcher = /\s*/

		expect:
		"" ==~ matcher
		" " ==~ matcher
		"  " ==~ matcher
		" \t" ==~ matcher
		" \t" ==~ matcher
	}
}