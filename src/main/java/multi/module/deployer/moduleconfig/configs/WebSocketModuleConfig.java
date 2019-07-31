package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import multi.module.deployer.moduleconfig.AbstractAsyncResultModuleConfig;
import multi.module.deployer.moduleconfig.info.HttpServiceInfo;

/**
 * Class that checks for a module deployment by waiting for a successful websocket connection
 */
public class WebSocketModuleConfig extends AbstractAsyncResultModuleConfig<WebSocket, HttpServiceInfo> {

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     */
    public WebSocketModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, new HttpServiceInfo(port, address, requestURI));
    }

    @Override
    public Future<Void> waitDeployment(Vertx vertx) {
        setVertxInstance(vertx);
        int port = moduleInfo.getPort();
        String address = moduleInfo.getAddress();
        String requestURI = moduleInfo.getRequestURI();
        return waitWebsocketDeployment(port, address, requestURI);
    }

    /**
     * Waits the module deployment by connecting to its web socket
     *
     * @param port       the port where the microservice is listening to
     * @param host       the microservice host address
     * @param requestURI the requested api
     * @return a future that will be completed after the connection succeeds
     */
    private Future<Void> waitWebsocketDeployment(int port, String host, String requestURI) {
        System.out.println("Waiting for websocket connection at " + format(port, host, requestURI));
        Promise<Void> promise = Promise.promise();
        HttpClient client = vertx.createHttpClient();
        waitWebsocketDeployment(promise, client, port, host, requestURI);
        return promise.future();
    }

    private void waitWebsocketDeployment(Promise<Void> promise, HttpClient client, int port, String host, String requestURI) {
        client.webSocket(port, host, requestURI, res -> {
            if (successCondition.test(res)) {
                client.close();
                promise.complete();
            } else {
                vertx.setTimer(retryOnFailDelay, r -> waitWebsocketDeployment(promise, client, port, host, requestURI));
            }
        });
    }
}
