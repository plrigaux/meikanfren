package gtest

import groovy.servlet.GroovyServlet

import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.plr.Pizza2
import com.plr.TransType;



abstract class MyServlet extends GroovyServlet {

	protected String encoding = "UTF-8";
	
	void init(ServletConfig config) {
		println "ExampleServlet initialized"
	}

	abstract TransType getTransType()
	
	void service(HttpServletRequest request, HttpServletResponse response) {
		def greeting = "Hello"
		def name = request.getParameter( "name" ) ?: "World"
		def content = "${greeting}, ${name}!"


		content = doContent()

		def uri = request.getRequestURI()

		def word = uri.split('/')[-1]

		
		def context = request.getContextPath();
		def path = request.getPathInfo();
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
	<link rel="stylesheet" type="text/css" href="${context}/src/style.css">
</head>
<body>
${content}
</body>
</html>
"""
		
		
		
		
		
		
		
		

		response.setContentType("text/html; charset=" + encoding);
		response.setCharacterEncoding(encoding)
		
		response.getWriter().write( out )
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