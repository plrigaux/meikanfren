package com.plr;

import ratpack.registry.NotInRegistryException;
import ratpack.render.NoSuchRendererException;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Main2 {

	
	
	public static void main(String[] args) throws NoSuchRendererException, NotInRegistryException, Exception {
	
			
		RatpackServer.start(server -> server 
				.serverConfig(c -> c.baseDir(BaseDir.find()))
			     .handlers(chain -> chain
			       .get(ctx -> ctx.render("Hello World!"))
			       .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
			       .files(f -> f.dir("assets"))
			     )
			   );
			 }
}
