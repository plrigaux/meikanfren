package com.plr;

import groovy.util.slurpersupport.NodeChild


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
	
		
		String word = "paper"
		
		TransType type = TransType.ENFR
		
		NodeChild doc = pizza.getNodeChild(word, type)
		
		def out = pizza.constructOutput(word, type, doc)
		
		
		println out
	}
	
	def "testtemplate3"() {
		given: "a new Adder class is created"
		
		def bind = [word: "Pizza", lang : "fr"]
		
		def pizza = new Pizza2();

		String xmlString = this.getClass().getResource( '/traink.html' ).text
		
		String word = "traink"
		
		TransType type = TransType.ENFR
		
		NodeChild doc = pizza.getNodeChild(word, type)
		
		def out = pizza.constructOutput(word, type, doc)
		
		println out
	}
	
	def "testtemplate4"() {
		given: "a new Adder class is created"
		
		def bind = [word: "Pizza", lang : "fr"]
		
		def pizza = new Pizza2();

		String word = "train"
		
		TransType type = TransType.ENFR
		
		NodeChild doc = pizza.getNodeChild(word, type)
		
		def out = pizza.constructOutput(word, type, doc)
		
		
		println out
	}
}



