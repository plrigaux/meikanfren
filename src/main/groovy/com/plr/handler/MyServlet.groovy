package com.plr.handler

import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.Request
import ratpack.http.Response

import com.plr.Pizza2
import com.plr.TransType



abstract class MyServlet implements Handler {

	static final String ENCODING = "UTF-8";
	
	abstract TransType getTransType()

	static Pizza2 piz = new Pizza2()
		
	public void handle(Context context) {
		
		Request request = context.getRequest();

		def content = doContent()
		
		def word = context.getRequest().getQueryParams().get("word")

		content = piz.make_get_request(word, getTransType())
		//println val
		def out = """\
<!DOCTYPE html>
<html lang="fr">
<head>
	<base href="/">
	<meta charset='utf-8' />
	<title>Traduction : ${word}</title>
	<link rel="stylesheet" type="text/css" href="src/style.css">
</head>
<body>
${content}
</body>
</html>
"""
		
		Response response = context.getResponse()
		
		response.contentType("text/html; charset=" + ENCODING).send(out)
	}
	
	def doContent() {

		def writer = new StringWriter()  // html is written here by markup builder
		def markup = new groovy.xml.MarkupBuilder(writer)  // the builder

		markup.html {
			// html is implicitly bound to new MarkupBuilder(out)
			head { title('Groovy Servlet') }
			body { p("Hello, PLr! ${new Date()}") }
		}
		writer.toString()
	}



}