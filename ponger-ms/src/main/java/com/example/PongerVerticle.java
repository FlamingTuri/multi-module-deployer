package com.example;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

public class PongerVerticle extends AbstractVerticle {

    private int pongSentCount = 0;

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        // CORS handling
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.PUT);

        CorsHandler corsHandler = CorsHandler.create("*");
        corsHandler.allowedHeaders(allowedHeaders);
        corsHandler.allowedMethods(allowedMethods);
        router.route().handler(corsHandler);

        // REST API
        router.get("/api/status").handler(this::getStatus);
        router.post("/api/ping").handler(this::pingHandler);

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router);

        int verticlePort = 8080;
        server.listen(verticlePort, listenHandler -> {
            if (listenHandler.succeeded()) {
                System.out.println(PongerVerticle.class.getSimpleName() + " running on port " + verticlePort);
            } else {
                System.out.println("Error in service setup");
                System.out.println("" + listenHandler.cause());
                vertx.close();
            }
        });
    }

    private void getStatus(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-Type", "application/text");
        response.end(PongerVerticle.class.getSimpleName() + " status: active");
    }

    private void pingHandler(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        JsonObject jsonBody;
        try {
            String body = routingContext.getBodyAsString();
            jsonBody = new JsonObject(body);
        } catch (NullPointerException | DecodeException e) {
            sendWrongBodyDataReceivedMsg(response);
            return;
        }

        if (jsonBody.containsKey("ping")) {
            System.out.println("received ping " + jsonBody.getInteger("ping"));
            response.putHeader("Content-Type", "application/json");

            vertx.executeBlocking(promise -> {
                sleep(2000);
                promise.complete();
            }, result -> {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.put("pong", ++pongSentCount);
                response.end(jsonResponse.encode());
                System.out.println("sent pong " + pongSentCount);
            });
        } else {
            sendWrongBodyDataReceivedMsg(response);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendWrongBodyDataReceivedMsg(HttpServerResponse response) {
        String reason = "wrong body data received";
        System.out.println(reason);
        response.setStatusCode(400);
        response.putHeader("Content-Type", "application/text");
        response.end(reason);
    }
}
