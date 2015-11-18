package com.plr

import ratpack.handling.Handler;
import ratpack.handling.Context;

public class Example implements Handler {
	public void handle(Context context) {
		context.getResponse().send("Hello world!!!!");
	}
}