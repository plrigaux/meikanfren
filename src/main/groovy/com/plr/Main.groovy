package com.plr;

import ratpack.file.FileHandlerSpec;
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.registry.NotInRegistryException
import ratpack.render.NoSuchRendererException
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer
import ratpack.server.RatpackServerSpec
import ratpack.server.ServerConfigBuilder;

import com.plr.handler.ENFRTranslate
import com.plr.handler.FRENTranslate

public class Main {

	public static void main(String[] args) throws NoSuchRendererException, NotInRegistryException, Exception {

		def mydir = BaseDir.find()

		println mydir

		RatpackServer.start { RatpackServerSpec server ->
			server.serverConfig{  ServerConfigBuilder c ->
				c.baseDir(mydir)
			}.handlers{ Chain chain ->
				chain.get("") {  Context ctx -> ctx.redirect "/index.html" }
				.get ("trans_enfr", new ENFRTranslate())
				.get ("trans_fren", new FRENTranslate())
				.get ("example", new Example())
				.files { FileHandlerSpec f -> f.dir "public" }
			}
		}
	}
}
