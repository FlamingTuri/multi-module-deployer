package com.example;

import io.vertx.core.Vertx;

public class Launcher {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        PongerVerticle pongerVerticle = new PongerVerticle();
        vertx.deployVerticle(pongerVerticle);
    }

}
