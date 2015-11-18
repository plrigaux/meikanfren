package com.plr

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

	XmlSlurper PARSER

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

		doc = getSubNode(doc)

		String val = doc == null ? "NULL" : XmlUtil.serialize(doc).substring('<?xml version="1.0" encoding="UTF-8"?>'.size())

		return val
	}

	def getSubNode(doc) {
		def subNode = doc.depthFirst().find {
			it.name() == 'div' && it.@class == 'article_bilingue'
		}
		cleanSubNode subNode
		return subNode
	}

	def cleanSubNode(subNode) {

		if (subNode == null) {
			return;
		}

		def brs = subNode.'**'.findAll {
			it.name() == 'br'
		}
		brs.each {
			it.attributes().clear()
		}

		def anodes = subNode.'**'.findAll {
			it.name() == 'a'
		}

		anodes.each {

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
					def keysToRemove = ["id", "href", "shape"]
					def newMap = it.attributes().findAll({!keysToRemove.contains(it.key)})
					it.replaceNode {
						span(newMap, it.text() )
					}
			}
		}
	}

	String getData(String word, TransType type) {
		def http = new HTTPBuilder()

		def url = type.getUrl() + word

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
