package com.example;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

public class PongerVerticle extends AbstractVerticle {

    private static final String contentTypeHeader = "Content-Type";
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
        allowedHeaders.add(contentTypeHeader);
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
        router.get("/api/status")
            .produces(MimeType.TEXT_PLAIN.getValue())
            .handler(this::getStatus);
        router.get("/api/pong")
            .handler(this::getPongHandler);
        router.post("/api/ping")
            .consumes(MimeType.APPLICATION_JSON.getValue())
            .produces(MimeType.APPLICATION_JSON.getValue())
            .handler(this::pingHandler);

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router);

        int verticlePort = 8080;
        server.listen(verticlePort, listenHandler -> {
            if (listenHandler.succeeded()) {
                Utils.log("running on port " + verticlePort);
            } else {
                Utils.log("Error in service setup " + listenHandler.cause());
                vertx.close();
            }
        });
    }

    private void getStatus(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader(contentTypeHeader, MimeType.TEXT_PLAIN.getValue());
        response.end(PongerVerticle.class.getSimpleName() + " status: active");
    }

    private void getPongHandler(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader(contentTypeHeader, MimeType.APPLICATION_JSON.getValue());
        JsonObject jsonPongSentCount = new JsonObject();
        jsonPongSentCount.put("pong", pongSentCount);
        response.end(jsonPongSentCount.encode());
    }
    
    private void pingHandler(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        JsonObject jsonBody = routingContext.getBodyAsJson();
        if (jsonBody.containsKey("ping")) {
            Utils.log("received ping " + jsonBody.getInteger("ping"));
            response.putHeader(contentTypeHeader, MimeType.APPLICATION_JSON.getValue());

            vertx.executeBlocking(promise -> {
                Utils.sleep(2000);
                promise.complete();
            }, result -> {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.put("pong", ++pongSentCount);
                response.end(jsonResponse.encode());
                Utils.log("sent pong " + pongSentCount);
            });
        } else {
            sendWrongBodyDataReceivedMsg(response);
        }
    }

    private void sendWrongBodyDataReceivedMsg(HttpServerResponse response) {
        String reason = "wrong body data received";
        Utils.log(reason);
        response.setStatusCode(400);
        response.putHeader(contentTypeHeader, MimeType.APPLICATION_JSON.getValue());
        response.end(new JsonObject().put("reason", reason).encode());
    }
}
