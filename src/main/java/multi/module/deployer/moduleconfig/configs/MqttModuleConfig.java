package multi.module.deployer.moduleconfig.configs;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.mqtt.messages.MqttConnAckMessage;
import multi.module.deployer.DeployWaiter;
import multi.module.deployer.moduleconfig.AbstractExecInNewTermModuleConfig;

import java.util.function.Predicate;

/**
 * Class that checks for a module deployment by waiting for a successful mqtt connection
 */
public class MqttModuleConfig extends AbstractExecInNewTermModuleConfig<MqttConnAckMessage> {

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     */
    public MqttModuleConfig(String unixCmd, String windowsCmd, int port, String address) {
        super(unixCmd, windowsCmd, port, address, null);
    }

    /**
     * @param unixCmd          the commands to run on Unix-like environments
     * @param windowsCmd       the commands to run on Windows environments
     * @param port             the port where the microservice is listening to
     * @param address          the microservice host address
     * @param successCondition the condition to fulfill to consider the module deployed
     */
    public MqttModuleConfig(String unixCmd, String windowsCmd, int port, String address, Predicate<AsyncResult<MqttConnAckMessage>> successCondition) {
        super(unixCmd, windowsCmd, port, address, null, successCondition);
    }

    @Override
    public Future<Void> waitDeployment(DeployWaiter deployWaiter) {
        return deployWaiter.waitMqttDeployment(port, address, successCondition);
    }
}
