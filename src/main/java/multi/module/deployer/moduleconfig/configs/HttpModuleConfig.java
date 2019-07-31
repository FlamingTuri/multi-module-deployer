package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import multi.module.deployer.moduleconfig.AbstractAsyncResultModuleConfig;
import multi.module.deployer.moduleconfig.info.HttpServiceInfo;

/**
 * Class that checks for a module deployment by waiting for a successful http GET response
 */
public class HttpModuleConfig extends AbstractAsyncResultModuleConfig<HttpResponse<Buffer>, HttpServiceInfo> {

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     */
    public HttpModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        super(unixCmd, windowsCmd, new HttpServiceInfo(port, address, requestURI));
    }

    @Override
    public Future<Void> waitDeployment(Vertx vertx) {
        setVertxInstance(vertx);
        int port = moduleInfo.getPort();
        String address = moduleInfo.getAddress();
        String requestURI = moduleInfo.getRequestURI();
        return waitHttpDeployment(port, address, requestURI);
    }

    /**
     * Waits the module deployment by sending a get request and waiting for a successful response
     *
     * @param port       the port where the microservice is listening to
     * @param host       the microservice host address
     * @param requestURI the requested api
     * @return a future that will be completed after the request succeeds
     */
    private Future<Void> waitHttpDeployment(int port, String host, String requestURI) {
        System.out.println("Waiting for http response at " + format(port, host, requestURI));
        Promise<Void> promise = Promise.promise();
        WebClient client = WebClient.create(vertx);
        waitHttpDeployment(promise, client, port, host, requestURI);
        return promise.future();
    }

    private void waitHttpDeployment(Promise<Void> promise, WebClient client, int port, String host, String requestURI) {
        client.get(port, host, requestURI).send(ar -> {
            if (successCondition.test(ar)) {
                client.close();
                promise.complete();
            } else {
                vertx.setTimer(retryOnFailDelay, r -> waitHttpDeployment(promise, client, port, host, requestURI));
            }
        });
    }
}
