package com.plr

import groovy.util.slurpersupport.NodeChild

import java.nio.charset.Charset

import org.apache.http.HttpEntity
import org.apache.http.HttpHeaders
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.LaxRedirectStrategy
import org.apache.http.util.EntityUtils
import org.ccil.cowan.tagsoup.Parser

class Pizza2 {


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

		NodeChild doc = getNodeChild( word, type)

		return constructOutput(word, type, doc)
	}

	NodeChild getNodeChild(String word, TransType type) {
		NodeChild doc = null;

		if (word) {
			word = cleanWord(word)

			String data = getData(word, type)

			doc = parse data
		}
	}

	String constructOutput(String word, TransType type, NodeChild doc) {

		NodePage np = getSubNode(doc, type)

		np.clean()

		def param = [
			word: word,
			lang: "fr"
		]

		String output = np.generateOutPut(param)

		return output
	}

	NodePage getSubNode(NodeChild doc, TransType type) {
		NodePage np;
		if (doc) {
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
			np = new NodePage(pp: pp, node: subNode, type: type);
		} else {
			np = new NodePage(pp: PageProcessor.NOFOUND, node: null, type: type);
		}

		return np
	}


	String getData(String word, TransType type) {
		URI uri = type.buildUrl(word)
		getData(uri)
	}

	String getData(URI uri) {
		HttpClient httpclient = HttpClientBuilder.create()
				.setRedirectStrategy(new LaxRedirectStrategy()).build();

		HttpGet request = new HttpGet(uri);

		// add request header
		request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Firefox/3.0.4");
		request.addHeader(HttpHeaders.ACCEPT, "*/*")

		//ResponseHandler<String> responseHandler = new BasicResponseHandler();


		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

					public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
						def status = response.getStatusLine().getStatusCode();

						HttpEntity entity = response.getEntity();
						ContentType contentType = null;
						Charset charset = null;

						if (entity) {
							contentType = ContentType.get(entity);

							String mimeType = contentType.getMimeType();

							charset = contentType?.getCharset();
						}

						String mimeType = contentType?.getMimeType()

						println "Got response: ${status}"
						println "Content-Type: ${contentType}"
						println "charset: ${charset}"
						println "mimeType: ${mimeType}"

						if (status >= 200 && status < 300) {
							//HttpEntity entity = response.getEntity();
							return entity != null ? EntityUtils.toString(entity) : null;
						} else {
							throw new ClientProtocolException("Unexpected response status: " + status);
						}
					}

				};
		String responseBody = httpclient.execute(request, responseHandler);
	}

	def cleanWord(String word) {
		word = word.trim()

		word = word.replaceAll(/\s/,'_')
	}
}
