package multi.module.deployer;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.client.WebClient;
import io.vertx.mqtt.MqttClient;


public class DeployWaiter {

    private final Vertx vertx;
    private long retryOnFailDelay = 2000;

    public DeployWaiter() {
        this(Vertx.vertx());
    }

    public DeployWaiter(Vertx vertx) {
        this.vertx = vertx;
    }

    public long getRetryOnFailDelay() {
        return retryOnFailDelay;
    }

    public void setRetryOnFailDelay(long retryOnFailDelay) {
        this.retryOnFailDelay = retryOnFailDelay;
    }

    public Future<Void> waitHttpDeployment(int port, String host, String requestURI) {
        System.out.println("Waiting setup via http");
        Promise<Void> promise = Promise.promise();
        WebClient client = WebClient.create(vertx);
        waitHttpDeployment(promise, client, port, host, requestURI);
        return promise.future();
    }

    private void waitHttpDeployment(Promise<Void> promise, WebClient client, int port, String host, String requestURI) {
        client.get(port, host, requestURI).send(ar -> {
            if (ar.succeeded()) {
                promise.complete();
                client.close();
            } else {
                vertx.setTimer(retryOnFailDelay, r -> waitHttpDeployment(promise, client, port, host, requestURI));
            }
        });
    }

    public Future<Void> waitWebsocketDeployment(int port, String host, String requestURI) {
        System.out.println("Waiting setup via websocket");
        Promise<Void> promise = Promise.promise();
        HttpClient client = vertx.createHttpClient();
        waitWebsocketDeployment(promise, client, port, host, requestURI);
        return promise.future();
    }

    private void waitWebsocketDeployment(Promise<Void> promise, HttpClient client, int port, String host, String requestURI) {
        client.webSocket(port, host, requestURI, res -> {
            if (res.succeeded()) {
                promise.complete();
                client.close();
            } else {
                vertx.setTimer(retryOnFailDelay, r -> waitWebsocketDeployment(promise, client, port, host, requestURI));
            }
        });
    }

    public Future<Void> waitMqttDeployment(int port, String host) {
        Promise<Void> promise = Promise.promise();
        MqttClient client = MqttClient.create(vertx);
        waitMqttDeployment(promise, client, port, host);
        return promise.future();
    }

    private void waitMqttDeployment(Promise<Void> promise, MqttClient client, int port, String host) {
        System.out.println("Waiting setup via mqtt");
        client.connect(port, host, s -> {
            if (s.succeeded()) {
                promise.complete();
                client.disconnect();
            } else {
                System.out.println("Something went wrong ");
                vertx.setTimer(retryOnFailDelay, r -> waitMqttDeployment(promise, client, port, host));
            }
        });
    }

    public void deployCompleted() {
        vertx.close();
    }
}
