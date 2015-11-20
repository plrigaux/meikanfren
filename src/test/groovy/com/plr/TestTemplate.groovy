package com.plr;

import groovy.xml.XmlUtil


public class TestTemplate extends spock.lang.Specification {


	def "testtemplate"() {
		given: "a new Adder class is created"
		
		def bind = [word: "Pizza", lang : "fr"]
		
		def out = new GenOutput() {
			
		}.genOutPut(bind)
		
		println out
	}
	
	def "testtemplate2"() {
		given: "a new Adder class is created"
		
		def bind = [word: "Pizza", lang : "fr"]
		
		def pizza = new Pizza2();

		String xmlString = this.getClass().getResource( '/paper.html' ).text
		
		def out = pizza.constructOutput("paper", TransType.ENFR, xmlString)
		
		println out
	}
	
	def "testtemplate3"() {
		given: "a new Adder class is created"
		
		def bind = [word: "Pizza", lang : "fr"]
		
		def pizza = new Pizza2();

		String xmlString = this.getClass().getResource( '/traink.html' ).text
		
		def out = pizza.constructOutput("traink", TransType.ENFR, xmlString)
		
		println out
	}
	
	def "testtemplate4"() {
		given: "a new Adder class is created"
		
		def bind = [word: "Pizza", lang : "fr"]
		
		def pizza = new Pizza2();

		String xmlString = "asdf"
		
		def out = pizza.constructOutput("train", TransType.ENFR, xmlString)
		
		println out
	}
}



