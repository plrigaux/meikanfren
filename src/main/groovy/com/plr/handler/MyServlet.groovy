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

	protected String encoding = "UTF-8";
	
	void init(ServletConfig config) {
		println "ExampleServlet initialized"
	}

	abstract TransType getTransType()
	
	public void handle(Context context) {
		
		Request request = context.getRequest();


		def content = doContent()
		
		def word = context.getPathTokens().get("word")
/*
		def uri = request.getUri()

		def word = uri.split('/')[-1]
*/
		
		//def context = request.getContextPath();
		//def path = request.getPathInfo();
		//println "<path href='${path}/ --  ${context}'>"
		
		
		def piz = new Pizza2()

		content = piz.make_get_request(word, getTransType())
		//println val
		def out = """\
<!DOCTYPE html>
<html>
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

		
		//response.setContentType();
		//response.setCharacterEncoding(encoding)
		
		response.contentType("text/html; charset=" + encoding)
		response.send(out)
		//response.getWriter().write( out )
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