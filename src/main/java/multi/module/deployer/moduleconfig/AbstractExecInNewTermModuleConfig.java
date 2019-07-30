package multi.module.deployer.moduleconfig;

import io.vertx.core.AsyncResult;
import io.vertx.mqtt.messages.MqttConnAckMessage;
import multi.module.deployer.cmdrunner.CmdRunner;

import java.util.function.Predicate;

/**
 * An abstract class that will run the commands in a new detached terminal instance
 */
public abstract class AbstractExecInNewTermModuleConfig<T> extends AbstractModuleConfig {

    protected final Predicate<AsyncResult<T>> successCondition;

    /**
     * Constructor with success condition set to AsyncResult::succeeded
     *
     * @param unixCmd    the commands to run on Unix-like environments
     * @param windowsCmd the commands to run on Windows environments
     * @param port       the port where the microservice is listening to
     * @param address    the microservice host address
     * @param requestURI the requested api
     */
    public AbstractExecInNewTermModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI) {
        this(unixCmd, windowsCmd, port, address, requestURI, AsyncResult::succeeded);
    }

    /**
     * Constructor that allows to setup a custom module deployment condition
     *
     * @param unixCmd          the commands to run on Unix-like environments
     * @param windowsCmd       the commands to run on Windows environments
     * @param port             the port where the microservice is listening to
     * @param address          the microservice host address
     * @param requestURI       the requested api
     * @param successCondition the condition to fulfill to consider the module deployed
     */
    public AbstractExecInNewTermModuleConfig(String unixCmd, String windowsCmd, int port, String address, String requestURI, Predicate<AsyncResult<T>> successCondition) {
        super(unixCmd, windowsCmd, port, address, requestURI);
        this.successCondition = successCondition;
    }

    @Override
    public void deploy(CmdRunner cmdRunner) {
        cmdRunner.execInNewTerm(unixCmd, windowsCmd);
    }
}
