package multi.module.deployer;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.client.WebClient;
import io.vertx.mqtt.MqttClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * Class to wait for the deployment of a module
 */
public class DeployWaiter {

    private final Vertx vertx;
    private long retryOnFailDelay = 2000;

    public DeployWaiter() {
        this(Vertx.vertx());
    }

    public DeployWaiter(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * Gets the time to wait before retrying to check for module deployment
     *
     * @return
     */
    public long getRetryOnFailDelay() {
        return retryOnFailDelay;
    }

    /**
     * Sets the time to wait before retrying to check for module deployment
     *
     * @param retryOnFailDelay the time to wait
     */
    public void setRetryOnFailDelay(long retryOnFailDelay) {
        this.retryOnFailDelay = retryOnFailDelay;
    }

    /**
     * Waits the module deployment by sending a get request and waiting for a successful response
     *
     * @param port       the port where the microservice is listening to
     * @param host       the microservice host address
     * @param requestURI the requested api
     * @return a future that will be completed after the request succeeds
     */
    public Future<Void> waitHttpDeployment(int port, String host, String requestURI) {
        System.out.println("Waiting for http response at " + format(port, host, requestURI));
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

    /**
     * Waits the module deployment by connecting to it's web socket
     *
     * @param port       the port where the microservice is listening to
     * @param host       the microservice host address
     * @param requestURI the requested api
     * @return a future that will be completed after the connection succeeds
     */
    public Future<Void> waitWebsocketDeployment(int port, String host, String requestURI) {
        System.out.println("Waiting for websocket connection at " + format(port, host, requestURI));
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

    /**
     * Waits the module deployment by subscribing via mqtt
     *
     * @param port the port where the microservice is listening to
     * @param host the microservice host address
     * @return a future that will be completed after the mqtt subscription succeeds
     */
    public Future<Void> waitMqttDeployment(int port, String host) {
        System.out.println("Waiting for mqtt successful subscription at " + format(port, host));
        Promise<Void> promise = Promise.promise();
        MqttClient client = MqttClient.create(vertx);
        waitMqttDeployment(promise, client, port, host);
        return promise.future();
    }

    private void waitMqttDeployment(Promise<Void> promise, MqttClient client, int port, String host) {
        client.connect(port, host, s -> {
            if (s.succeeded()) {
                promise.complete();
                client.disconnect();
            } else {
                vertx.setTimer(retryOnFailDelay, r -> waitMqttDeployment(promise, client, port, host));
            }
        });
    }

    /**
     * Waits a process termination
     *
     * @param process the process to wait for
     * @return a future that will be completed after the process terminates
     */
    public Future<Void> waitProcessTermination(Process process) {
        System.out.println("Waiting process termination");
        Promise<Void> promise = Promise.promise();
        vertx.executeBlocking(blockingCodePromise -> {
            try {
                readOutput(process);
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blockingCodePromise.complete();
        }, resultHandler -> promise.complete());
        return promise.future();
    }

    /**
     * Gets process input and error stream
     *
     * @param process the process
     */
    private void readOutput(Process process) {
        readStream(process.getInputStream(), System.out::println);
        readStream(process.getErrorStream(), System.err::println);
    }

    private void readStream(InputStream in, Consumer<String> print) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                print.accept(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String format(int port, String host) {
        return host + ":" + port;
    }

    private String format(int port, String host, String requestURI) {
        return format(port, host) + requestURI;
    }

    /**
     * Closes the vertx instance. To call only when there are no more pending futures
     */
    public void deployCompleted() {
        vertx.close();
    }
}
