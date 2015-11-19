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
		piz.make_get_request("paper clip")
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


		def doc = parse data

		doc = getSubNode(doc, type)

		String val = doc == null ? "NULL" : XmlUtil.serialize(doc).substring('<?xml version="1.0" encoding="UTF-8"?>'.size())

		return val
	}

	NodeChild getSubNode(NodeChild doc, TransType type) {

		def subNode = doc.depthFirst().find {
			it.name() == 'div' && it.@class == 'article_bilingue'
		}


		if (subNode) {
			cleanSubNode subNode, type
		} else {
			subNode = doc.depthFirst().find {
				it.name() == 'section' && it.@class == 'corrector'
			}

			cleanSubNodeCorrector (subNode, type)
		}

		return subNode
	}


	void cleanSubNodeCorrector(NodeChild subNode, TransType type) {

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
						hr = type.getUrlPrefix() + hr.split('/').last()
						a(href: hr, anode.text())
					}
				}
			}
		}
	}


	def final keysToRemove = ["id", "href", "shape"]

	void cleanSubNode(NodeChild subNode, TransType type) {

		if (subNode == null) {
			return;
		}

		subNode.'**'.find {
			it.name() == 'h1' && it.@class == 'TitrePage'
		}.each {
			// remove <h1 class="TitrePage">Traduction de ****</h1>

			if (it.@class == 'TitrePage') {
				it.replaceNode {}
			} else {
				it.replaceNode {
					h2(it.attributes(), it.text() )
				}
			}
		}

		subNode.'**'.findAll {
			it.name() == 'br'
		}.each {
			it.attributes().clear()
		}

		subNode.'**'.findAll {
			it.name() == 'a'
		}.each {

			String txt = it.text().trim().toLowerCase()

			//println "txt '" + txt + "'"

			switch ( txt ) {
				case "conjugaison":
				case "":
				case "&nbsp;":
				case " ":
					it.replaceNode {

					}
					break
				default:

					def newMap = it.attributes().findAll({!keysToRemove.contains(it.key)})
					it.replaceNode {
						span(newMap, it.text() )
					}
			}
		}

		def anodes = subNode.'**'.findAll {
			it.attributes().containsKey("lang")
		}.each {
			Map<String, String> attrs = it.attributes()

			//println attrs.keySet()
			String lang = attrs["lang"]
			attrs.remove("{http://www.w3.org/XML/1998/namespace}lang")

			//transfor the FRA to fr and ANG to en
			attrs["lang"] = lang.toLowerCase().startsWith("fr") ? "fr" : "en"

		}
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
