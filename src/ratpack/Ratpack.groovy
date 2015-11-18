import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

import com.plr.Example
import com.plr.handler.ENFRTranslate
import com.plr.handler.FRENTranslate

ratpack {
    handlers {
        /*get {
            render "Hello World!"
        }
        get(":name") {
            render "Hello $pathTokens.name!"
        }
		*/
		
		get ("en/fr/:word", new ENFRTranslate())
		
		get ("fr/en/:word", new FRENTranslate())
		
		get ("example", new Example())
		
		files { dir "public" }
    }
}
