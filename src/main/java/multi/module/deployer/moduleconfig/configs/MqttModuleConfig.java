package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.messages.MqttConnAckMessage;
import multi.module.deployer.moduleconfig.AbstractAsyncResultModuleConfig;
import multi.module.deployer.moduleconfig.info.ServiceInfo;

/**
 * Class that checks for a module deployment by waiting for a successful mqtt connection
 */
public class MqttModuleConfig extends AbstractAsyncResultModuleConfig<MqttConnAckMessage, ServiceInfo> {

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     */
    public MqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        super(unixCmd, windowsCmd, new ServiceInfo(port, address));
    }

    @Override
    public Future<Void> waitDeployment(Vertx vertx) {
        setVertxInstance(vertx);
        int port = moduleInfo.getPort();
        String address = moduleInfo.getAddress();
        return waitMqttDeployment(port, address);
    }

    /**
     * Waits the module deployment by subscribing via mqtt
     *
     * @param port the port where the microservice is listening to
     * @param host the microservice host address
     * @return a future that will be completed after the mqtt subscription succeeds
     */
    private Future<Void> waitMqttDeployment(int port, String host) {
        System.out.println("Waiting for mqtt successful subscription at " + format(port, host));
        Promise<Void> promise = Promise.promise();
        MqttClient client = MqttClient.create(vertx);
        waitMqttDeployment(promise, client, port, host);
        return promise.future();
    }

    private void waitMqttDeployment(Promise<Void> promise, MqttClient client, int port, String host) {
        client.connect(port, host, ackMessage -> {
            if (successCondition.test(ackMessage)) {
                client.disconnect();
                promise.complete();
            } else {
                vertx.setTimer(retryOnFailDelay, r -> waitMqttDeployment(promise, client, port, host));
            }
        });
    }
}
