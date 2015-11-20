package com.plr

import groovy.util.slurpersupport.NodeChild;
import groovy.xml.XmlUtil
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import org.ccil.cowan.tagsoup.Parser

class Pizza2 {
	static void main(String[] args) {

		def piz = new Pizza2()
		piz.constructOutput("paper clip")
		println "Pizza2"
	}

	final XmlSlurper PARSER

	Pizza2() {
		def saxParser = new Parser()
		saxParser.setFeature('http://xml.org/sax/features/namespaces',false)
		PARSER = new XmlSlurper(saxParser )
	}

	def parse(String text) {
		def document = PARSER.parseText(text)
		document
	}

	def add(first, second) {
		return first + second
	}

	String make_get_request(String word, TransType type) {

		word = cleanWord(word)

		String data = getData(word, type)

		return constructOutput(word, type, data)
	}

	String constructOutput(String word, TransType type, String data) {

		def doc = parse data

		NodePage np = getSubNode(doc, type)

		np.clean(np.node)

		def param = [
			word: word,
			lang: "fr"
		]

		String output = np.generateOutPut(param)

		return output
	}

	NodePage getSubNode(NodeChild doc, TransType type) {

		NodeChild subNode = doc.depthFirst().find {
			it.name() == 'div' && it.@class == 'article_bilingue'
		}

		PageProcessor pp = null;
		if (subNode) {
			pp = PageProcessor.TRANSLATION
		} else {
			subNode = doc.depthFirst().find {
				it.name() == 'section' && it.@class == 'corrector'
			}
			pp = subNode ? PageProcessor.ALTERNATIVES : PageProcessor.NOFOUND
		}

		NodePage np = new NodePage(pp: pp, node: subNode, type: type);

		return np
	}




	String getData(String word, TransType type) {
		def http = new HTTPBuilder()

		def url = type.buildUrl( word)

		String ret = null

		http.request( url, Method.GET, ContentType.TEXT ) { req ->

			//uri.path = '/groovy/examples/'
			headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
			//headers.Accept = 'application/json'

			response.success = { resp, reader ->
				assert resp.statusLine.statusCode == 200
				println "Got response: ${resp.statusLine}"
				println "Content-Type: ${resp.headers.'Content-Type'}"

				ret = reader.text
			}

			response.'404' = { println 'Not found' }
		}

		return ret
	}

	def cleanWord(String word) {
		word = word.trim()

		word = word.replaceAll(/\s/,'_')
	}
}
